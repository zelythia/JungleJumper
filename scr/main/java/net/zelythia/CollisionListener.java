package net.zelythia;

import net.zelythia.GameObjects.GameObject;

public interface CollisionListener {
    void onCollision(CollisionData collisionData);
}
