package net.zelythia.GameObjects;

import java.awt.*;
import java.io.IOException;

public class Player extends GameObject{

    public float vel_x, vel_y;

    public Player(Shape shape, String spritePath, int x, int y) {
        super(shape, spritePath, x, y);
    }

    public void setVelocity(float x, float y){
        vel_x = x;
        vel_y = y;
    }
}
