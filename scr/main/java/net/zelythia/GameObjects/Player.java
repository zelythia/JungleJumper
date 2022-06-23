package net.zelythia.GameObjects;

import java.awt.*;
import java.io.IOException;

public class Player extends GameObject{

    public Player(Shape shape, String spritePath, int x, int y) throws IOException {
        super(shape, spritePath, x, y);
    }
}
