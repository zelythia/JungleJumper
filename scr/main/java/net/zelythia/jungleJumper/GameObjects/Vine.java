package net.zelythia.jungleJumper.GameObjects;

import net.zelythia.jungleJumper.Collision.CollisionType;

import java.awt.*;

public class Vine extends GameObject implements Interactible{
    public Vine(Shape shape, String sprite) {
        super(shape, sprite);
        setCollisionType(CollisionType.INTERACTION);
    }


    @Override
    public void onInteraction(Player player) {
        player.vel.x *= 2;
        player.vel.y -= 15;
    }

    @Override
    public GameObject getGameObject() {
        return this;
    }
}
