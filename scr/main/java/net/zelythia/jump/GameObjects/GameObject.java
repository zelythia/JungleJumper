package net.zelythia.jump.GameObjects;


import net.zelythia.jump.Collision.CollisionType;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.io.File;
import java.io.IOException;


//Model
public class GameObject{

    protected Shape shape;
    private Image sprite;
    private CollisionType collisionType;

    public GameObject(Shape shape, Image sprite){
        this.shape = shape;
        this.sprite = sprite;
        collisionType = CollisionType.NONE;
    }

    public GameObject(Shape shape, String sprite){
        this.shape = shape;
        if(!sprite.equals("")){
            try {
                this.sprite = ImageIO.read(new File(sprite));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        collisionType = CollisionType.NONE;
    }

    public void setShape(RectangularShape shape)
    {
        this.shape = shape;
    }
    public Shape getShape() {
        return shape;
    }

    public Rectangle2D getBounds(){
        return shape.getBounds2D();
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

    }

    public double getX(){
        return shape.getBounds2D().getX();
    }

    public double getY(){
        return shape.getBounds().getY();
    }

    public void setCollisionType(CollisionType type){
        collisionType = type;
    }

    public CollisionType getCollisionType(){
        return collisionType;
    }
}
