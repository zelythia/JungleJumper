package net.zelythia.jump.GameObjects;


import net.zelythia.jump.Collision.CollisionData;
import net.zelythia.jump.Collision.CollisionType;
import net.zelythia.jump.Events.CollisionListener;
import net.zelythia.jump.Events.UpdateListener;
import net.zelythia.jump.GameEngine;
import net.zelythia.jump.Utils.Vector2D;

import java.awt.geom.RectangularShape;

public class Player extends GameObject implements CollisionListener, UpdateListener {

    public Vector2D vel;
    private double speed;

    public double maxSpeed;

    public Player(RectangularShape shape, String spritePath) {
        this(shape, spritePath, 1, 5);
    }

    public Player(RectangularShape shape, String spritePath, double speed, double maxSpeed) {
        super(shape, spritePath);
        vel = new Vector2D(0,0);
        this.speed = speed;
        this.maxSpeed = maxSpeed;

        GameEngine.collisionListeners.add(this);
        GameEngine.updateListeners.add(this);
    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void onCollision(CollisionData collisionData) {
        System.out.println("player collided");

        if(collisionData.collider().getCollisionType() == CollisionType.INTERACTION){
            System.out.println("can interact");
        }
    }


}
