package net.zelythia.jump.GameObjects;

import net.zelythia.jump.Collision.CollisionType;

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
