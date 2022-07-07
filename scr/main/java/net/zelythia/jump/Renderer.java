package net.zelythia.jump;

import net.zelythia.jump.GameObjects.GameObject;
import net.zelythia.jump.Utils.Vector2D;

import java.awt.*;

public interface Renderer {
    void render(Graphics graphics);
    void addGameObject(GameObject gameObject);
    void clearGameObjects();
    void setCameraPosition(int x, int y);
    Vector2D getCameraPosition();
}
