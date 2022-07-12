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
import java.awt.geom.*;


//Game controller = Game engine = Control
public class GameEngine implements KeyListener {

    private final GameRenderer renderer;

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

    public GameEngine(GameRenderer renderer){
        this.renderer = renderer;
        this.gameObjects = new List<>();
        this.gameBoundaries = new List<>();

        initializeGameObjects();
    }

    public void initializeGameObjects()
    {
        player = new Player(new Rectangle2D.Double(201, 200, 50, 50), "scr/main/resources/player.png", 20);


        //Game boundaries
        gameBoundaries.add(new Solid(-6, 0, 1, 800, "scr/main/resources/wall.png"));
        gameBoundaries.add(new Solid(485, 0, 1, 800, "scr/main/resources/wall.png"));


        //Solids
        gameObjects.add(new Solid(0, 700,480, 20, "scr/main/resources/wall.png"));
       // gameObjects.add(new Solid(300, 500, 200,200, "scr/main/resources/wall.png"));


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
        if(player.vel.y < 10 && !playerOnGround){
            player.vel.y += 1;
        }
        //Limiting the players speed
        player.vel.clamp(-player.maxSpeed, player.maxSpeed);

        movePlayer();
        checkPlayerOutOfScreen();

        if(playerOnGround){
            addPlayerFriction();
            renderer.setCameraPosition(0, (int) Utils.lerp(renderer.getCameraPosition().y, player.getY() - 500, .05));
        }


        //========================================================

        checkPlayerCollision();

        //========================================================

        renderer.setScoreMulti(scoreMultiplier);
        renderer.setTimer((float) ((System.currentTimeMillis() - startTime) / 1000));
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
        else if(player.getX() > 480 - player.getShape().getWidth()/2){
            player.setPos(-player.getShape().getWidth()/2, player.getY());
        }
    }

    public GameObject getPlayerPhysicsColliderOnMove(double x, double y, double w, double h){
        List<GameObject> possibleColliders = new List<>();

        for(int i = 0; i < gameObjects.size; i++){
            if(gameObjects.get(i).getCollisionType() == CollisionType.COLLIDE){
                possibleColliders.add(gameObjects.get(i));

                if(gameObjects.get(i).getX() + gameObjects.get(i).getBounds().getWidth() >= 480){
                    possibleColliders.add(new Solid(-1, gameObjects.get(i).getY(), 1, gameObjects.get(i).getBounds().getHeight(), ""));
                }

                if(gameObjects.get(i).getX()+ gameObjects.get(i).getBounds().getWidth() <= 0){
                    possibleColliders.add(new Solid(481, gameObjects.get(i).getY(), 1, gameObjects.get(i).getBounds().getHeight(), ""));
                }
            }
        }

        for(int i = 0; i < possibleColliders.size; i++){
            if(possibleColliders.get(i).getShape().intersects(x, y, w, h)) return possibleColliders.get(i);
        }

        return null;
    }

    public void movePlayer(){

        double slopeInteraction = 0;
        playerOnGround = false;

        //DOWN
        for(int i = 0; i < player.vel.y; i++){
            GameObject collider = getPlayerPhysicsColliderOnMove(player.getX(), player.getY()+1, player.getShape().getWidth(), player.getShape().getHeight());
            if(collider == null){
                player.setPos(player.getX(), player.getY()+1);
            }
            else {
                addPlayerFriction();
                if(collider instanceof Slope slope && slope.getSlopeCollisionInteraction() != 0){
                    player.setPos(player.getX()+slope.getSlopeCollisionInteraction(), player.getY());
                    slopeInteraction = slope.getSlopeCollisionInteraction();
                }
                else{
                    player.vel.y = 0;
                    playerOnGround = true;
                    break;
                }
            }
        }
        //UP
        for(int i = 0; i > player.vel.y; i--){
            playerOnGround = false;
            if(getPlayerPhysicsColliderOnMove(player.getX(), player.getY()-1, player.getShape().getWidth(), player.getShape().getHeight()) == null){
                player.setPos(player.getX(), player.getY()-1);
            }
            else{
                player.vel.y = 0;
                break;
            }
        }

        //RIGHT
        for(int i = 0; i < player.vel.x; i++){
            if(getPlayerPhysicsColliderOnMove(player.getX()+1, player.getY(), player.getShape().getWidth(), player.getShape().getHeight()) == null){
                player.setPos(player.getX()+1, player.getY());
            }
            else{
                addPlayerFriction();
                break;
            }
        }

        //LEFT
        for(int i = 0; i > player.vel.x; i--){
            if(getPlayerPhysicsColliderOnMove(player.getX()-1, player.getY(), player.getShape().getWidth(), player.getShape().getHeight()) == null){
                player.setPos(player.getX()-1, player.getY());
            }
            else{
                addPlayerFriction();
                break;
            }
        }

        if (slopeInteraction != 0){
            //Player collided with slope
            player.vel.x = 0;
        }
        else{
            playerOnGround = getPlayerPhysicsColliderOnMove(player.getX(), player.getY()+1, player.getShape().getWidth(), player.getShape().getHeight()) != null;
        }
    }



    public void addPlayerFriction(){
        player.vel.x = Math.round(Utils.lerp(player.vel.x, 0, .6f) * 1000d) / 1000d;
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

    public Shape getShapeAtPoint(double x, double y){
        for(int i = 0; i < gameObjects.size; i++){
            if(gameObjects.get(i).getShape().contains(x, y)) return gameObjects.get(i).getShape();
        }
        return null;
    }

    public void removeGameObject(GameObject object){
        gameObjects.remove(object);
    }



    public void addScoreMultiplier(float multiplier){
        this.scoreMultiplier += multiplier;
    }

    public void levelFinished(){
        long endTime = System.currentTimeMillis();

        System.out.println("Time: " + (endTime - startTime) / 1000 + " seconds");
        System.out.println("Score: " + (100000*scoreMultiplier)/(endTime - startTime));
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