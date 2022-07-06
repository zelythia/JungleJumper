package net.zelythia.jump;

import net.zelythia.jump.GameObjects.GameObject;

import java.awt.*;

public interface Renderer {
    void render(Graphics graphics);
    void addGameObject(GameObject gameObject);
    void setCameraPosition(int x, int y);
}
