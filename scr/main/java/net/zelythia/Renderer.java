package net.zelythia;

import net.zelythia.GameObjects.GameObject;

import javax.swing.*;
import java.awt.*;

public interface Renderer {
    void render(Graphics graphics);
    void addGameObject(GameObject gameObject);
    void setCameraPosition(int x, int y);
}
