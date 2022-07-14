package net.zelythia.jump.Screens;

import net.zelythia.jump.JumpKing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JPanel implements ActionListener {

    //Image image;

    public MainMenu(){
        this.setFocusable(true);

        JButton startGame = new JButton("Start");


        startGame.addActionListener(this);

        this.add(startGame);


        // try {
        //     image = ImageIO.read("scr/main/resources/image.png");
        // } catch (IOException e) {
        //     throw new RuntimeException(e);
        // }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //g.drawImage(image, x, y, width, height);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       if(e.getActionCommand() == "Start"){
            JumpKing.initializeGameScene();
       }

    }
}
