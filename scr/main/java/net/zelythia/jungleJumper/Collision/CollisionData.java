package net.zelythia.jungleJumper.Collision;

import net.zelythia.jungleJumper.GameObjects.GameObject;

public record CollisionData(GameObject collider, Side side) {
}
