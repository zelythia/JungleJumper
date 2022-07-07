package net.zelythia.jump;



import net.zelythia.jump.Collision.CollisionType;
import net.zelythia.jump.Events.CollisionListener;
import net.zelythia.jump.Events.UpdateListener;
import net.zelythia.jump.GameObjects.*;
import net.zelythia.jump.Utils.List.List;
import net.zelythia.jump.Utils.Utils;
import net.zelythia.jump.Utils.Vector2D;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;


//Game controller = Game engine = Control
public class GameEngine implements KeyListener {

    private final Renderer renderer;

    public List<Solid> gameBoundaries;
    private boolean playerOnGround;

    private long startTime;
    private float scoreMultiplier;

    public Player player;
    public List<GameObject> gameObjects;


    //Listeners
    public static List<CollisionListener> collisionListeners;
    public static List<UpdateListener> updateListeners;


    static {
        collisionListeners = new List<>();
        updateListeners = new List<>();
    }

    public GameEngine(Renderer renderer){
        this.renderer = renderer;
        this.gameObjects = new List<>();
        this.gameBoundaries = new List<>();

        initializeGameObjects();
    }

    public void initializeGameObjects()
    {
        player = new Player(new Rectangle2D.Double(200, 600, 50, 50), "scr/main/resources/player.png", 1, 20);


        //Game boundaries
        gameBoundaries.add(new Solid(-6, 0, 1, 800, "scr/main/resources/wall.png"));
        gameBoundaries.add(new Solid(485, 0, 1, 800, "scr/main/resources/wall.png"));


        //Solids
        gameObjects.add(new Solid(0, 700,480, 20, "scr/main/resources/wall.png"));
        gameObjects.add(new Solid(300, 500, 200,200, "scr/main/resources/wall.png"));

        //Collectibles
        gameObjects.add(new Coin(20, 670));
    }


    public void update(float deltaTime){

        /*
        for(int i = 0; i < collisionListeners.size; i++){
            updateCollisionListeners(collisionListeners.get(i));
        }*/

        for(int i = 0; i < updateListeners.size; i++){
            updateListeners.get(i).update(deltaTime);
        }


        //=====Player movement======================================

        //Gravity
        if(player.vel.y < 10){
            player.vel.y += 1;
        }
        //Limiting the players speed
        player.vel.clamp(-player.maxSpeed, player.maxSpeed);

        calculatePlayerPhysics();
        checkPlayerOutOfScreen();

        if(playerOnGround){
            renderer.setCameraPosition(0, (int) Utils.lerp(renderer.getCameraPosition().y, player.getY() - 500, .05));
        }

        //========================================================

        checkPlayerCollision();

        //System.out.println(player.getY());
    }

    public void render(Graphics graphics){

        renderer.clearGameObjects();

        //Rendering the gameObjects
        for(int i = 0; i < gameObjects.size; i++){
            renderer.addGameObject(gameObjects.get(i));
        }
        renderer.addGameObject(player);

        renderer.render(graphics);
    }



    public void checkPlayerOutOfScreen(){
        if(player.getX() <  -player.getShape().getWidth()/2){
            player.setPos(480 - player.getShape().getWidth()/2, player.getY());
        }
        else if(player.getX() > 480 + player.getShape().getWidth()/2){
            player.setPos(-player.getShape().getWidth()/2, player.getY());
        }
    }

    public void calculatePlayerPhysics(){

        //True if the player would collide when moving in a direction
        boolean collides = false;
        playerOnGround = false;

        List<RectangularShape> possibleColliders = new List<>();


        for(int i = 0; i < gameObjects.size; i++){
            if(gameObjects.get(i).getCollisionType() == CollisionType.COLLIDE){
                possibleColliders.add(gameObjects.get(i).getShape());

                if(gameObjects.get(i).getX() + gameObjects.get(i).getShape().getWidth() >= 480){
                    possibleColliders.add(new Rectangle2D.Double(-1, gameObjects.get(i).getY(), 1, gameObjects.get(i).getShape().getHeight()));
                }

                if(gameObjects.get(i).getX()+ gameObjects.get(i).getShape().getWidth() <= 0){
                    possibleColliders.add(new Rectangle2D.Double(481, gameObjects.get(i).getY(), 1, gameObjects.get(i).getShape().getHeight()));
                }
            }
        }

        for(int i = 0; i < possibleColliders.size; i++){
            Rectangle2D bounds = possibleColliders.get(i).getBounds2D();

            RectangularShape gameObjectShape = player.getShape();

            //Extending the bounds of the Rectangle on the side to calculate collision when gamObjects are touching and not when overlapping
            if(new Rectangle2D.Double(gameObjectShape.getX(), gameObjectShape.getY(), gameObjectShape.getWidth(), gameObjectShape.getHeight()+player.vel.y).intersects(bounds)){
                //Side = BOTTOM
                if(player.vel.y > 0){
                    player.setPos(player.getX() + player.vel.x, bounds.getY()-player.getShape().getHeight());
                    player.vel.y = 0;
                    collides = true;
                }

                playerOnGround = true;
            }
            if(new Rectangle2D.Double(gameObjectShape.getX(), gameObjectShape.getY()+player.vel.y, gameObjectShape.getWidth(), gameObjectShape.getHeight()).intersects(bounds)){
                //Side = TOP
                if(player.vel.y < 0){
                    player.setPos(player.getX() + player.vel.x, bounds.getY()+bounds.getHeight());
                    //player.vel.y = 0;
                    collides = true;
                }
            }

            if(new Rectangle2D.Double(gameObjectShape.getX(), gameObjectShape.getY(), gameObjectShape.getWidth()+player.vel.x, gameObjectShape.getHeight()).intersects(bounds)){
                //Side = RIGHT
                if(player.vel.x > 0) {
                    player.setPos(bounds.getX()-player.getShape().getWidth(), player.getY() + player.vel.y);
                    //player.vel.x = 0;
                    collides = true;
                }
            }
            if(new Rectangle2D.Double(gameObjectShape.getX()+player.vel.x, gameObjectShape.getY(), gameObjectShape.getWidth(), gameObjectShape.getHeight()).intersects(bounds)){
                //SIDE = LEFT
                if(player.vel.x < 0) {
                    player.setPos(bounds.getX()+bounds.getWidth(), player.getY() +  player.vel.y);
                    //player.vel.x = 0;
                    collides = true;
                }
            }
        }

        if(collides){
            //Simulating friction:
            player.vel.x = Utils.lerp(player.vel.x, 0, .25f);
            player.vel.y = Utils.lerp(player.vel.y, 0, .1f);
        }
        else{
            player.setPos(player.getX() + player.vel.x, player.getY() + player.vel.y);
        }

    }

    public void checkPlayerCollision(){

        List<GameObject> possibleCollisions = new List<>();
        for(int i = 0; i < gameObjects.size; i++){
            possibleCollisions.add(gameObjects.get(i));
        }

        for(int i = 0; i < possibleCollisions.size; i++){
            if(player.getShape().intersects(possibleCollisions.get(i).getShape().getBounds2D())){
                if(possibleCollisions.get(i).getCollisionType() == CollisionType.INTERACTION){

                }

                if(possibleCollisions.get(i) instanceof Collectible collectible) {
                    collectible.onCollect(this);
                }
            }
        }
    }


    public void addScoreMultiplier(float multiplier){
        this.scoreMultiplier += multiplier;
    }

    public void levelFinished(){
        long endTime = System.currentTimeMillis();

        System.out.println("Time: " + (endTime - startTime) / 1000 + "seconds");
        System.out.println("Score: " + (100000*scoreMultiplier)/(endTime - startTime));
    }


    public Shape getShapeAtPoint(double x, double y){
        for(int i = 0; i < gameObjects.size; i++){
            if(gameObjects.get(i).getShape().contains(x, y)) return gameObjects.get(i).getShape();
        }
        return null;
    }

    public void removeGameObject(GameObject object){
        gameObjects.remove(object);
    }


//=======EVENTS=========================================================================================================

    Vector2D storedVel = new Vector2D(0,0);

    @Override
    public void keyTyped(KeyEvent e) {
        if(startTime==0) startTime = System.currentTimeMillis();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(playerOnGround){
            if(e.getKeyCode() == KeyEvent.VK_W){
                storedVel.y -= 10;
            }

            if(e.getKeyCode() == KeyEvent.VK_A){
                storedVel.x -= 5;
            }

            if(e.getKeyCode() == KeyEvent.VK_D){
                storedVel.x += 5;
            }

            player.setSprite("scr/main/resources/player_charging.png");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(playerOnGround){
            storedVel.clamp(-50, 50);
            player.vel.add(storedVel);
            storedVel = new Vector2D(0,0);
        }
        player.setSprite("scr/main/resources/player.png");
    }
}
