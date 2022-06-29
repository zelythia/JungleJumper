package net.zelythia;


import javax.swing.*;

public class JumpKing {

    public static void main(String[] args) {
        new JumpKing();
    }

    private JFrame frame;
    private GameEngine engine;

    public JumpKing(){
        frame = new JFrame("Jump King'ish");
        engine = new GameEngine();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(480, 800);
        frame.setResizable(false);

        initializeGameScene();
    }



    public void initializeGameScene(){
        GameScreen panel = new GameScreen();

        frame.getContentPane().add(panel);
        panel.addKeyListener(engine);

        //Creating the game loop
        new Thread( () -> {
            long delta = 0l;

            while (true) {
                long lastTime = System.nanoTime();

                engine.update((float)(delta / 1000000000.0));

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