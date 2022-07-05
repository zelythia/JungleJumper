package net.zelythia.GameObjects;


import net.zelythia.Collision.CollisionType;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RectangularShape;
import java.io.File;
import java.io.IOException;


//Model
public abstract class GameObject{

    private RectangularShape shape;
    private Image sprite;
    private CollisionType collisionType;

    public GameObject(RectangularShape shape, String sprite){
        this.shape = shape;
        try {
            this.sprite = ImageIO.read(new File(sprite));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        collisionType = CollisionType.NONE;
    }
    

    public void setShape(RectangularShape shape)
    {
        this.shape = shape;
    }
    public RectangularShape getShape() {
        return shape;
    }

    public void setSprite(String sprite){
        try {
            this.sprite = ImageIO.read(new File(sprite));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Image getSprite(){
        return sprite;
    }

    public void setPos(double x, double y){
        this.shape.setFrame(x, y, this.shape.getWidth(), this.shape.getHeight());
    }

    public double getX(){
        return shape.getX();
    }

    public double getY(){
        return shape.getY();
    }

    public void setCollisionType(CollisionType type){
        collisionType = type;
    }

    public CollisionType getCollisionType(){
        return collisionType;
    }
}
