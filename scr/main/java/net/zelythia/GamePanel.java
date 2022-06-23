package net.zelythia;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//Panel = Screen
public class GamePanel extends JPanel implements KeyListener, Tickable {

    public GamePanel(){
        addKeyListener(this);
        setFocusable(true);
        setBackground(Color.ORANGE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        //gameObject.draw(g, this);
    }

    @Override
    public void process() {
        super.repaint();
    }



    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
