package net.zelythia.jump;


import net.zelythia.jump.Collision.CollisionType;
import net.zelythia.jump.Events.UpdateListener;
import net.zelythia.jump.GameObjects.*;
import net.zelythia.jump.Utils.List.List;
import net.zelythia.jump.Utils.Utils;
import net.zelythia.jump.Utils.Vector2D;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;


//Game controller = Game engine = Control
public class GameEngine implements KeyListener {

    private final GameRenderer renderer;

    private boolean playerOnGround;
    private Interactible interactiveObject;

    private long startTime;
    private float scoreMultiplier;

    public Player player;
    public List<GameObject> gameObjects;
    public List<GameObject> temporalRenderedObjects;


    //Listeners
    public static List<UpdateListener> updateListeners;


    static {
        updateListeners = new List<>();
    }

    public GameEngine(GameRenderer renderer){
        this.renderer = renderer;
        this.gameObjects = new List<>();
        temporalRenderedObjects = new List<>();

        initializeGameObjects();
    }

    public void initializeGameObjects()
    {
        player = new Player(new Rectangle2D.Double(220, 645, 50, 50), "scr/main/resources/monkey.png", 20);

        //y geht nach unten; 0,0 linke obere ecke
        //480x800
        //Solids
        gameObjects.add(new Solid(0, 700,480, 259, "scr/main/resources/grass_floor.png"));      //Boden

        gameObjects.add(new Solid(320, 500, 160,200, "scr/main/resources/wall.png"));   //Startblock rechts
        gameObjects.add(new Solid(0, 500, 160, 200, "scr/main/resources/wall.png"));    //Startblock links
        //Block 1
        gameObjects.add(new Solid(130 , 300, 140, 70, "scr/main/resources/wall.png"));
        //Block 2
        gameObjects.add(new Solid(0, 100, 140, 100, "scr/main/resources/wall.png"));
        gameObjects.add(Slope.Down(140, 100, 26, 100));
        //Block 3
        gameObjects.add(new Solid(400, 100, 160, 100, "scr/main/resources/wall.png"));
        gameObjects.add(Slope.Up(350, 100, 50, 100));
        //Block 4
        gameObjects.add(Slope.DownInv(175, -30, 75, 200));
        gameObjects.add(Slope.UpInv(250, -50, 100, 220));
        gameObjects.add(new Solid(0, -50, 250, 50, "scr/main/resources/wall.png"));
        //Block 5
        gameObjects.add(new Solid(220, -250, 100, 75, "scr/main/resources/wall.png"));
        gameObjects.add(new Solid(320, -295, 100, 120, "scr/main/resources/wall.png"));
        //Block 6
        gameObjects.add(new Solid(60, -350, 120, 50, "scr/main/resources/wall.png"));
        //Block 7
        gameObjects.add(new Solid(320, -425, 120, 40, "scr/main/resources/wall.png"));
        //Block 8
        gameObjects.add(new Solid(0, -475, 100, 50, "scr/main/resources/wall.png"));
        //Block 9
        gameObjects.add(new Solid(200, -525, 75, 50, "scr/main/resources/wall.png"));
        //Block 10
        gameObjects.add(new Solid(75, -650, 75, 50, "scr/main/resources/wall.png"));
        //Block 11
        gameObjects.add(new Solid(375, -650, 75, 50, "scr/main/resources/wall.png"));
        //Block 12
        gameObjects.add(new Solid(220, -825, 150, 75, "scr/main/resources/wall.png"));
        //Block 13
        gameObjects.add(new Solid(60, -900, 100, 75, "scr/main/resources/wall.png"));
        //Block 14
        gameObjects.add(new Solid(0, -1025, 60, 75, "scr/main/resources/wall.png"));
        gameObjects.add(Slope.Down(60, -1025, 50, 75));
        //Block 15
        gameObjects.add(new Solid(400, -1025, 80, 75, "scr/main/resources/wall.png"));
        //Block 16
        gameObjects.add(new Solid(190, -1100, 120, 75, "scr/main/resources/wall.png"));
        gameObjects.add(Slope.DownInv(160, -1100, 30, 75));
        gameObjects.add(Slope.Down(310, -1100, 30, 75));
        //Block 17
        gameObjects.add(new Solid(0, -1225, 50, 75, "scr/main/resources/wall.png"));
        gameObjects.add(Slope.UpInv(50, -1225, 75, 75));
        //Block 18
        gameObjects.add(new Solid(200, -1250, 100, 50, "scr/main/resources/wall.png"));
        //Block 19
        gameObjects.add(new Solid(400, -1300, 80, 50, "scr/main/resources/wall.png"));
        //Block 20
        gameObjects.add(new Solid(75, -1375, 100, 50, "scr/main/resources/wall.png"));;
        //Block 21
        gameObjects.add(new Solid(275, -1450, 100, 75, "scr/main/resources/wall.png"));
        gameObjects.add(Slope.DownInv(200, -1450, 75, 75));
        //Block 22
        gameObjects.add(Slope.Up(75, -1550, 50, 100));
        gameObjects.add(Slope.UpInv(125, -1550, 50, 100));
        //Block 23
        gameObjects.add(new Solid(400, -1600, 80, 75, "scr/main/resources/wall.png"));
        gameObjects.add(Slope.DownInv(350, -1600, 50, 75));
        //Block 24
        gameObjects.add(new Solid(0, -1700, 75, 75, "scr/main/resources/wall.png"));
        gameObjects.add(Slope.Down(75, -1700, 50, 75));
        //Block 25
        gameObjects.add(new Solid(160, -1800, 160, 50, "scr/main/resources/wall.png"));


        //Collectibles
        gameObjects.add(new Coin(30, 60));

        //BANANA
        gameObjects.add(new Banana(200, -1900));

        //Interactible
        gameObjects.add(new Vine(new Rectangle2D.Double(220,370,10,150), "scr/main/resources/vine.png"));
        gameObjects.add(new Vine(new Rectangle2D.Double(295, -750, 10, 150), "scr/main/resources/vine.png"));

    }


    public void update(float deltaTime){
        temporalRenderedObjects.clear();


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
        renderer.setTimer(startTime);

    }

    public void render(Graphics graphics){

        renderer.clearGameObjects();

        //Rendering the gameObjects
        for (int i = 0; i < temporalRenderedObjects.size; i++) {
            renderer.add(temporalRenderedObjects.get(i));
        }

        for(int i = 0; i < gameObjects.size; i++){
            renderer.add(gameObjects.get(i));
        }
        renderer.add(player);

        renderer.render(graphics);
    }


    public void checkPlayerOutOfScreen(){

        if(player.getX() <  -player.getShape().getWidth()/2){
            player.setPos(480 - player.getShape().getWidth()/2, player.getY());
        }
        else if(player.getX() > 480 - player.getShape().getWidth()/2){
            player.setPos(-player.getShape().getWidth()/2, player.getY());
        }

        if(player.getX() < 0){
            temporalRenderedObjects.add(new GameObject(new Rectangle2D.Double(480 + player.getX(), player.getY(), player.getShape().getWidth(),player.getShape().getHeight()), player.getSprite()));
        }
    }

    public GameObject getPlayerPhysicsColliderOnMove(double x, double y, double w, double h){
        List<GameObject> possibleColliders = new List<>();

        for(int i = 0; i < gameObjects.size; i++){
            GameObject gameObject = gameObjects.get(i);
            if(gameObject.getCollisionType() == CollisionType.COLLIDE){
                possibleColliders.add(gameObject);

                if(gameObject.getX() + gameObject.getBounds().getWidth() >= 480){
                    possibleColliders.add(new Solid(-gameObject.getBounds().getWidth(), gameObject.getY(), gameObject.getBounds().getWidth(), gameObject.getBounds().getHeight(), ""));
                    if(renderer instanceof GameRenderer.DebugRenderer r){
                        r.addDebugObject(new Solid(-gameObject.getBounds().getWidth(), gameObject.getY(), gameObject.getBounds().getWidth(), gameObject.getBounds().getHeight(), ""));
                    }
                }

                if(gameObject.getX() <= 0){
                    possibleColliders.add(new Solid(481, gameObject.getY(), gameObject.getBounds().getWidth(), gameObject.getBounds().getHeight(), ""));
                    if(renderer instanceof GameRenderer.DebugRenderer r){
                        r.addDebugObject(new Solid(481, gameObject.getY(), gameObject.getBounds().getWidth(), gameObject.getBounds().getHeight(), ""));
                    }
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

        interactiveObject = null;

        List<GameObject> possibleCollisions = new List<>();
        for(int i = 0; i < gameObjects.size; i++){
            possibleCollisions.add(gameObjects.get(i));
        }

        for(int i = 0; i < possibleCollisions.size; i++){
            if(player.getShape().intersects(possibleCollisions.get(i).getShape().getBounds2D())){
                if(possibleCollisions.get(i) instanceof Interactible interactible){
                    interactiveObject = interactible;
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
        System.out.println("Score: " + (100000*scoreMultiplier)/(endTime - startTime) + "s");

        if(!JumpKing.username.equals("")){
            DB.addPlayerScore(JumpKing.username, scoreMultiplier, (100000*scoreMultiplier)/(endTime - startTime));
        }

        renderer.displayFinishScreen(scoreMultiplier, (100000*scoreMultiplier)/(endTime - startTime));

    }

//=======EVENTS=========================================================================================================

    Vector2D storedVel = new Vector2D(0,0);

    @Override
    public void keyTyped(KeyEvent e) {
        if(startTime==0) startTime = System.currentTimeMillis();
    }

    boolean playerFlipped = false;

    @Override
    public void keyPressed(KeyEvent e) {

        if(e.getKeyCode() == KeyEvent.VK_Q){
            if(interactiveObject != null){
                interactiveObject.onInteraction(player);
            }
        }

        if(playerOnGround){
            if(e.getKeyCode() == KeyEvent.VK_W){
                storedVel.y -= 10;

                if(playerFlipped) player.setSprite("scr/main/resources/monkey_crouching_flipped.png");
                else player.setSprite("scr/main/resources/monkey_crouching.png");

            }

            if(e.getKeyCode() == KeyEvent.VK_A){
                storedVel.x -= 5;
            }

            if(e.getKeyCode() == KeyEvent.VK_D){
                storedVel.x += 5;
            }

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(playerOnGround){
            storedVel.clamp(-50, 50);
            player.vel.add(storedVel);
            storedVel = new Vector2D(0,0);
        }

        playerFlipped = player.vel.x > 0;
        if(player.vel.x > 0){
            player.setSprite("scr/main/resources/monkey_flipped.png");
        }
        else{
            player.setSprite("scr/main/resources/monkey.png");
        }

    }
}
