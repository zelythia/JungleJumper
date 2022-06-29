package net.zelythia.GameObjects;

import net.zelythia.Tickable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Vector;


//Model
public class GameObject{

    public Shape shape;
    public Image sprite;
    public float x;
    public float y;


    public GameObject(Shape shape, String sprite, float x, float y){
        this.shape = shape;
        this.x = x;
        this.y = y;
        try {
            this.sprite = ImageIO.read(new File(sprite));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(Graphics graphics, Component component){
        graphics.drawImage(sprite, x, y, shape.getBounds().width, shape.getBounds().height, component);
    }

    public void setShape(Shape shape)
    {
        this.shape = shape;
    }

    public void setSprite(String sprite){
        try {
            this.sprite = ImageIO.read(new File(sprite));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setPos(float x, float y)
    {
        this.x = x;
        this.y = y;
    }
}
