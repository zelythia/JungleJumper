package net.zelythia.jump.GameObjects;


import net.zelythia.jump.Utils.Vector2D;

import java.awt.geom.RectangularShape;

public class Player extends GameObject {

    public Vector2D vel;
    public double maxSpeed;

    public Player(RectangularShape shape, String spritePath) {
        this(shape, spritePath, 5);
    }

    public Player(RectangularShape shape, String spritePath, double maxSpeed) {
        super(shape, spritePath);
        vel = new Vector2D(0,0);
        this.maxSpeed = maxSpeed;
    }

    @Override
    public void setPos(double x, double y) {
        RectangularShape shape = (RectangularShape) super.getShape();
        shape.setFrame(x, y, shape.getWidth(), shape.getHeight());
    }

    @Override
    public RectangularShape getShape() {
        return (RectangularShape) super.getShape();
    }
}
