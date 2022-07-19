package net.zelythia.jump.Screens;

import net.zelythia.jump.JumpKing;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class MainMenu extends JPanel implements ActionListener, KeyListener, FocusListener, MouseListener {

    //Image image;
    Image backgroundImage;
    Font font;

    JTextField nameField;
    JButton startGame;
    JButton scores;
    JButton exit;

    public MainMenu() {
        this.setFocusable(true);
        this.setOpaque(true);

        try {
            backgroundImage = ImageIO.read(new File("scr/main/resources/background.png"));
            font = Font.createFont(Font.TRUETYPE_FONT, new File("scr/main/resources/EduVICWANTBeginner-Bold.ttf"));
        } catch (IOException | FontFormatException e) {
            throw new RuntimeException(e);
        }

        this.addComponents();
    }

    int buttonHeight = 70;

    public void addComponents(){
        this.setLayout(null);

        nameField = new JTextField("Name");
        nameField.addActionListener(this);
        nameField.setHorizontalAlignment(JTextField.CENTER);
        nameField.setBounds(100, 50, 280, 50);
        nameField.setBorder(null);
        nameField.addKeyListener(this);
        nameField.addFocusListener(this);
        nameField.setFont(font.deriveFont(40f).deriveFont(Font.BOLD));
        nameField.setForeground(new Color(203, 203, 203));
        nameField.setBackground(new Color(64, 106, 71));


        startGame = new JButton("Start");
        startGame.addActionListener(this);
        startGame.setBounds(20, 300, 440, buttonHeight);
        startGame.setFont(font.deriveFont(50f));
        startGame.setForeground(new Color(203, 203, 203));
        startGame.setBackground(new Color(64, 106, 71));
        startGame.setBorder(new LineBorder(Color.darkGray, 5));
        startGame.addMouseListener(this);

        scores = new JButton("Scores");
        scores.addActionListener(this);
        scores.setBounds(20, 400, 440, buttonHeight);
        scores.setFont(font.deriveFont(50f));
        scores.setForeground(new Color(203, 203, 203));
        scores.setBackground(new Color(64, 106, 71));
        scores.setBorder(new LineBorder(Color.darkGray, 5));
        scores.addMouseListener(this);

        exit = new JButton("Exit");
        exit.addActionListener(this);
        exit.setBounds(20, 710, 440, buttonHeight);
        exit.setFont(font.deriveFont(50f));
        exit.setForeground(new Color(203, 203, 203));
        exit.setBackground(new Color(64, 106, 71));
        exit.setBorder(new LineBorder(Color.darkGray, 5));
        exit.addMouseListener(this);


        this.add(nameField);
        this.add(startGame);
        this.add(scores);
        this.add(exit);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(backgroundImage, 0, 0, backgroundImage.getWidth(this), backgroundImage.getHeight(this), this);

        g.setColor(new Color(1,1,1, 100));
        g.fillRect(20, 150, 440, 100);
    }





    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource().equals(nameField)){

        }
        else if(e.getSource().equals(startGame)) {
            JumpKing.initializeGameScene();
        }
        else if(e.getSource().equals(scores)) {
            JumpKing.loadScoreMenu();
        }
        else if(e.getSource().equals(exit)) {
            System.exit(0);
        }
    }


    boolean namePlaceholderChange = false;

    @Override
    public void keyTyped(KeyEvent e) {
        if(!namePlaceholderChange){
            namePlaceholderChange = true;
            nameField.setText("");
        }

        JumpKing.username = nameField.getText();
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


    //TextFiled event
    @Override
    public void focusGained(FocusEvent e) {
        if(!namePlaceholderChange){
            namePlaceholderChange = true;
            nameField.setText("");
        }
    }

    @Override
    public void focusLost(FocusEvent e) {

    }


    //Button Mouse Hover events
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        e.getComponent().setBackground(new Color(60, 87, 65));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        e.getComponent().setBackground(new Color(64, 106, 71));
    }
}
