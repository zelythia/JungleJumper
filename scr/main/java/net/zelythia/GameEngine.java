package net.zelythia;

import net.zelythia.GameObjects.GameObject;
import net.zelythia.GameObjects.Player;
import net.zelythia.GameObjects.Solid;
import net.zelythia.List.List;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;


//Game controller = Game engine = Control
public class GameEngine implements KeyListener {

    private Renderer renderer;

    public Player player;
    public List<Solid> solids;


    //Listeners
    public static List<CollisionListener> collisionListeners;
    public static List<UpdateListener> updateListeners;


    static {
        collisionListeners = new List<>();
        updateListeners = new List<>();
    }

    public GameEngine(Renderer renderer){
        this.renderer = renderer;
        this.solids = new List<>();

        initializeGameObjects();
    }

    public void initializeGameObjects()
    {
        player = new Player(new Rectangle(200, 400, 50, 50), "scr/main/resources/player.png", 1, 5);
        collisionListeners.add(player);

        solids.add(new Solid(0, 700,480, 20, "scr/main/resources/wall.png"));


        //Rendering the gameObjects
        renderer.addGameObject(player);
        renderer.addGameObject(solids.get(0));
    }


    public void update(float deltaTime){

        for(int i = 0; i < collisionListeners.size; i++){
            updateCollisionListeners(collisionListeners.get(i));
        }

        for(int i = 0; i < updateListeners.size; i++){
            updateListeners.get(i).update(deltaTime);
        }

        player.vel.y += 1;
        player.vel.clamp(-player.maxSpeed, player.maxSpeed);
        movePlayer();

        //System.out.println(player.vel);
    }

    public float lerp(float a, float b, float f){
        return a + f * (b - a);
    }

    public void movePlayer(){

        //True if the player would collide when moving in a direction
        boolean collides = false;

        for(int i = 0; i < solids.size; i++){
            if(solids.get(i).getCollisionType() == CollisionType.COLLIDE){
                Rectangle2D bounds = solids.get(i).getShape().getBounds2D();

                RectangularShape gameObjectShape = player.getShape();

                //Extending the bounds of the Rectangle on the side to calculate collision when gamObjects are touching and not when overlapping
                if(new Rectangle2D.Double(gameObjectShape.getX(), gameObjectShape.getY(), gameObjectShape.getWidth(), gameObjectShape.getHeight()+1).intersects(bounds)){
                    //Side = BOTTOM
                    if(player.vel.y > 0){
                        //player.setPos(player.getX() + player.vel.x, player.getY());
                        player.vel.y = 0;
                        collides = true;
                    }
                }
                if(new Rectangle2D.Double(gameObjectShape.getX(), gameObjectShape.getY(), gameObjectShape.getWidth()+1, gameObjectShape.getHeight()).intersects(bounds)){
                    //Side = RIGHT
                    if(player.vel.x > 0) {
                        //player.setPos(player.getX(), player.getY() + player.vel.y);
                        player.vel.x = 0;
                        collides = true;
                    }
                }
                if(new Rectangle2D.Double(gameObjectShape.getX(), gameObjectShape.getY()-1, gameObjectShape.getWidth(), gameObjectShape.getHeight()+1).intersects(bounds)){
                    //Side = TOP
                    if(player.vel.y < 0){
                        //player.setPos(player.getX() + player.vel.x, player.getY());
                        player.vel.y = 0;
                        collides = true;
                    }
                }
                if(new Rectangle2D.Double(gameObjectShape.getX()-1, gameObjectShape.getY(), gameObjectShape.getWidth()+1, gameObjectShape.getHeight()).intersects(bounds)){
                    //SIDE = LEFT
                    if(player.vel.x < 0) {
                        //player.setPos(player.getX(), player.getY() + player.vel.y);
                        player.vel.x = 0;
                        collides = true;
                    }
                }
            }
        }

        player.setPos(player.getX() + player.vel.x, player.getY() + player.vel.y);

        if(!collides){
            //player.setPos(player.getX() + player.vel.x, player.getY() + player.vel.y);
        }
        else{
            player.vel.x = lerp((float) player.vel.x, 0, 1f);
            player.vel.y = lerp((float) player.vel.y, 0, 1f);
        }

    }

    public void updateCollisionListeners(CollisionListener listener){
        //TODO make new list and add all future game Objects
        for(int i = 0; i < solids.size; i++){
            Rectangle2D bounds = solids.get(i).getShape().getBounds2D();

            if(listener instanceof GameObject gameObject){
                RectangularShape gameObjectShape = gameObject.getShape();

                //Extending the bounds of the Rectangle on the side to calculate collision when gamObjects are touching and not when overlapping
                if(gameObjectShape.intersects(bounds)){
                    listener.onCollision(new CollisionData(solids.get(i), Side.BOTTOM));
                }
                if(gameObjectShape.intersects(bounds)){
                    listener.onCollision(new CollisionData(solids.get(i), Side.RIGHT));
                }
                if(gameObjectShape.intersects(bounds)){
                    listener.onCollision(new CollisionData(solids.get(i), Side.TOP));
                }
                if(gameObjectShape.intersects(bounds)){
                    listener.onCollision(new CollisionData(solids.get(i), Side.LEFT));
                }
            }
        }
    }

    public void render(Graphics graphics){
        renderer.render(graphics);
    }



//=======EVENTS=========================================================================================================

    Vector2D storedVel = new Vector2D(0,0);

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("key");

        if(e.getKeyCode() == KeyEvent.VK_W){
            storedVel.y -= 50;
        }

        if(e.getKeyCode() == KeyEvent.VK_A){
            storedVel.x -= 5;
        }

        if(e.getKeyCode() == KeyEvent.VK_D){
            storedVel.x += 5;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        player.vel.add(storedVel);
        storedVel = new Vector2D(0,0);
    }
}
