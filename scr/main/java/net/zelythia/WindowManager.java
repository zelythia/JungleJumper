package net.zelythia;

import javax.swing.*;

public class WindowManager extends JFrame {

    public WindowManager(){
        this.setTitle("JumpKing'ish");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setFocusable(true);

        this.setSize(480, 800);
        this.setResizable(false);
    }


    public void setPanel(JPanel panel){
        this.getContentPane().removeAll();
        this.getContentPane().add(panel);
    }
}
