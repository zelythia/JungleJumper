package net.zelythia.jump.GameObjects;

import net.zelythia.jump.Collision.CollisionType;
import net.zelythia.jump.Events.UpdateListener;
import net.zelythia.jump.GameEngine;

import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;

public class Banana extends GameObject implements Collectible, UpdateListener {

    public Banana(double x, double y) {
        super(new Rectangle2D.Double(x, y, 80, 80), "scr/main/resources/banana.png");
        super.setCollisionType(CollisionType.INTERACTION);
        GameEngine.updateListeners.add(this);
    }

    @Override
    public void onCollect(GameEngine engine) {
        engine.removeGameObject(this);
        engine.addScoreMultiplier(100);
        engine.levelFinished();
    }


    double initialPosY = this.getY();
    double goalPos = initialPosY - 15;
    int updateCounter = 0;

    @Override
    public void update(float deltaTime) {
        if(updateCounter == 5){
            if(this.getY() > goalPos){
                setPos(this.getX(), this.getY()-1);
            }
            else{
                setPos(this.getX(), this.getY()+1);
            }

            if(this.getY() == goalPos){
                goalPos = (goalPos == initialPosY - 15)? initialPosY + 15: initialPosY - 15;
            }
            updateCounter = 0;
        }
        updateCounter++;
    }

    @Override
    public void setPos(double x, double y) {
        RectangularShape shape = (RectangularShape) super.getShape();
        shape.setFrame(x, y, shape.getWidth(), shape.getHeight());
    }
}
