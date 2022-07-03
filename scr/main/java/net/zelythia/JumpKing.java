package net.zelythia;


import javax.swing.*;

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
        frame.setFocusable(true);

        initializeGameScene();
    }

    public void test(){

    }


    public void initializeGameScene(){
        GameRenderer panel = new GameRenderer();
        frame.getContentPane().add(panel);


        engine = new GameEngine(panel);

        frame.addKeyListener(engine);

        //Creating the game loop
        new Thread( () -> {
            long delta = 0l;

            while (true) {
                long lastTime = System.nanoTime();

                engine.update((float)(delta / 1000000000.0));
                engine.render(frame.getGraphics());

                delta = System.nanoTime() - lastTime;
                if (delta < 16666666L) {
                    try {
                        // 16666666 is roughly 60 calls per second
                        // Dividing to convert to milliseconds
                        Thread.sleep((16666666L - delta) / 1000000L);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }).start();
    }
}