package net.zelythia.GameObjects;

import net.zelythia.CollisionType;

import java.awt.*;
import java.awt.geom.RectangularShape;

public class Solid extends GameObject{

    public Solid(RectangularShape shape, String sprite) {
        super(shape, sprite);
        super.setCollisionType(CollisionType.COLLIDE);
    }

    public Solid(int x, int y, int width, int height, String sprite){
        super(new Rectangle(x, y, width, height), sprite);
        super.setCollisionType(CollisionType.COLLIDE);
    }


}
