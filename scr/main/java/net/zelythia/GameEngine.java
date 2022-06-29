package net.zelythia;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


//Game controller = Game engine = Control
public class GameEngine implements KeyListener {

    private JPanel panel;

    Player player;

    public GameEngine(){

    }

    public void initializeGameObjects()
    {
        player = new Player(new Rectangle(50, 50), "scr/main/resources/player.png", 400, 400);
    }


    public void update(float deltaTime){
        calculateColissions();
        
        
        //Do all game logic here
        player.draw(null, panel);
    }

    public void calculateColissions(){

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
