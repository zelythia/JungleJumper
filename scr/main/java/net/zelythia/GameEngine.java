package net.zelythia;

import net.zelythia.GameObjects.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


//Game controller = Game engine = Control
public class GameEngine implements KeyListener {

    private Renderer renderer;

    public Player player;

    public GameEngine(Renderer renderer){
        this.renderer = renderer;

        initializeGameObjects();
    }

    public void initializeGameObjects()
    {
        player = new Player(new Rectangle(50, 50), "scr/main/resources/player.png", 400, 400);

        renderer.addGameObject(player);
    }


    public void update(float deltaTime){
        calculateColissions();


    }

    public void render(Graphics graphics){
        renderer.render(graphics);
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
