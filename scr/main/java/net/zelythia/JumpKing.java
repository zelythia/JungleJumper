package net.zelythia;


import net.zelythia.List.List;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class JumpKing {

    public static void main(String[] args) {
        new JumpKing();
    }

    private JFrame frame;
    private GameEngine engine;

    public JumpKing(){
        test();

        frame = new JFrame("Jump King'ish");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(480, 800);
        frame.setResizable(false);

        initializeGameScene();
    }

    public void test(){

    }


    public void initializeGameScene(){
        GameScreen panel = new GameScreen();
        frame.getContentPane().add(panel);
        panel.addKeyListener(engine);


        engine = new GameEngine(panel);




        //Creating the game loop
        new Thread( () -> {
            long delta = 0l;

            while (true) {
                long lastTime = System.nanoTime();

                engine.update((float)(delta / 1000000000.0));
                engine.render(frame.getGraphics());

                delta = System.nanoTime() - lastTime;
                if (delta < 20000000L) {
                    try {
                        Thread.sleep((20000000L - delta) / 1000000L);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }).start();
    }
}