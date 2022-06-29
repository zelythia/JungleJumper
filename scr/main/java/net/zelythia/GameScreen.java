package net.zelythia;

import net.zelythia.GameObjects.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

//Panel = Screen = View
public class GameScreen extends JPanel {

    public GameScreen(){
        this.setFocusable(true);
        this.setBackground(Color.ORANGE);
    }


    @Override
    protected void paintComponent(Graphics g) {
        //gameObject.draw(g, this);

        //player.draw(g, this);
    }

}
