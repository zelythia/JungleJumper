package net.zelythia.jump.GameObjects;

import net.zelythia.jump.Collision.CollisionType;
import net.zelythia.jump.GameEngine;

import java.awt.geom.Rectangle2D;

public class Coin extends GameObject implements Collectible{

    public Coin(double x, double y) {
        super(new Rectangle2D.Double(x, y, 20, 20), "scr/main/resources/coin.png");
        super.setCollisionType(CollisionType.INTERACTION);
    }

    @Override
    public void onCollect(GameEngine engine) {
        System.out.println("Collected a coin!");
        engine.removeGameObject(this);
        engine.addScoreMultiplier(10);
        engine.levelFinished();
    }
}
