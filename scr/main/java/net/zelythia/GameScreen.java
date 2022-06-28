package net.zelythia;

import net.zelythia.GameObjects.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

//Panel = Screen = View
public class GameScreen extends JPanel implements Tickable {

    Player player;


    public GameScreen(){
        this.setFocusable(true);
        this.setBackground(Color.ORANGE);

        initializeGameObjects();
    }


    public void initializeGameObjects(){
        player = new Player(new Rectangle(50, 50), "scr/main/resources/player.png", 400, 400);
    }

    @Override
    protected void paintComponent(Graphics g) {
        //gameObject.draw(g, this);

        player.draw(g, this);
    }

    @Override
    public void process() {
        super.repaint();
    }
}
