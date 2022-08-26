package net.zelythia.jump.GameObjects;

import net.zelythia.jump.Collision.CollisionType;
import net.zelythia.jump.Events.UpdateListener;
import net.zelythia.jump.GameEngine;

import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;

public class Coin extends GameObject implements Collectible, UpdateListener {

    public Coin(double x, double y) {
        super(new Rectangle2D.Double(x, y, 30, 30), "scr/main/resources/coin.png");
        super.setCollisionType(CollisionType.INTERACTION);
        GameEngine.updateListeners.add(this);
    }

    @Override
    public void onCollect(GameEngine engine) {
        engine.removeGameObject(this);
        engine.addScoreMultiplier(10);
    }


    double initialPosY = this.getY();
    double goalPos = initialPosY - 5;
    int updateCounter = 0;

    @Override
    public void update(float deltaTime) {
        if(updateCounter == 7){
            if(this.getY() > goalPos){
                setPos(this.getX(), this.getY()-1);
            }
            else{
                setPos(this.getX(), this.getY()+1);
            }

            if(this.getY() == goalPos){
                goalPos = (goalPos == initialPosY - 5)? initialPosY + 5: initialPosY - 5;
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
