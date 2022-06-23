package net.zelythia;


import javax.swing.*;

public class JumpKing {

    public static void main(String[] args) {
        new JumpKing();
    }

    public JumpKing(){
        JFrame frame = new JFrame("Jump King'ish");

        GamePanel panel = new GamePanel();

        frame.getContentPane().add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);
        frame.setSize(480, 800);


        frame.setResizable(false);

        new Thread( () -> {
            while (true) {
                panel.process();
                try {
                    Thread.sleep(50); //20 ticks/s
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}