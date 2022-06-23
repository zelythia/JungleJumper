package net.zelythia.GameObjects;

import net.zelythia.Tickable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

public class GameObject implements Tickable {

    public Shape shape;
    public Image sprite;
    public int x;
    public int y;


    public GameObject(Shape shape, String sprite, int x, int y) throws IOException {
        this.shape = shape;
        this.sprite = ImageIO.read(new File(sprite));
        this.x = x;
        this.y = y;
    }


    public void draw(Graphics graphics, Component component){
        graphics.drawImage(sprite, x, y, shape.getBounds().width, shape.getBounds().height, component);
    }

    @Override
    public void process() {

    }
}
