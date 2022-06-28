package net.zelythia.GameObjects;

import net.zelythia.Tickable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Vector;


//Model
public class GameObject implements Tickable {

    public Shape shape;
    public Image sprite;
    public int x;
    public int y;


    public GameObject(Shape shape, String sprite, int x, int y){
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

    @Override
    public void process() {

    }
}
