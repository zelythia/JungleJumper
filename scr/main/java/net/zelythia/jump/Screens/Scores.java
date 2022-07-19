package net.zelythia.jump.Screens;

import net.zelythia.jump.DB;
import net.zelythia.jump.JumpKing;
import net.zelythia.jump.Utils.Utils;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Scores extends JPanel implements ActionListener {

    Image backgroundImage;

    public Scores(){
        this.setFocusable(true);
        this.setOpaque(true);
        this.setLayout(null);

        try {
            backgroundImage = ImageIO.read(new File("scr/main/resources/background.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        JLabel title = new JLabel("<HTML><U>TOP PLAYERS</U></HTML>");
        title.setBounds(20, 20, 440, 80);
        title.setFont(Utils.font.deriveFont(50f).deriveFont(Font.BOLD));
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setForeground(Utils.WHITE);


        List<String> displayList = new ArrayList<String>();

        JSONArray topPlayers = DB.getTopPlayers();
        System.out.println(topPlayers);

        for(int i = 0; i < topPlayers.length(); i++){
            if(topPlayers.get(i) instanceof JSONObject player){
                displayList.add("   " + Math.addExact(i, 1) + ".   " + player.get("name").toString() + " - Score: " + player.get("score").toString() + " - " + player.get("time").toString() + "s");
            }
        }

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setBackground(new Color(58, 70, 56, 255));
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        int y = 20;

        for(String s: displayList){
            JLabel label = new JLabel(s);
            label.setPreferredSize(new Dimension(400, 50));
            label.setMinimumSize(new Dimension(400, 50));
            label.setMaximumSize(new Dimension(400, 50));
            y += 50;

            label.setForeground(new Color(203, 203, 203));
            label.setBorder(new LineBorder(new Color(203, 203, 203)));
            label.setFont(Utils.font.deriveFont(20f));

            panel.add(label);
            panel.add(Box.createRigidArea(new Dimension(20, 20)));
        }

        JScrollPane scrollPane = new JScrollPane(panel);

        scrollPane.setBounds(20, 100, 440, 580);
        scrollPane.setViewportView(panel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        scrollPane.getVerticalScrollBar().setForeground(Color.orange);

        //Exit Button

        JButton back = new JButton("Main Menu");
        back.addActionListener(this);
        back.setBounds(20, 710, 440, 70);
        back.setFont(Utils.font.deriveFont(50f));
        back.setForeground(new Color(203, 203, 203));
        back.setBackground(new Color(64, 106, 71));
        back.setBorder(new LineBorder(Color.darkGray, 5));

        this.add(back);
        this.add(title);
        this.add(scrollPane);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(backgroundImage, 0, 0, backgroundImage.getWidth(this), backgroundImage.getHeight(this), this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JumpKing.initializeMainMenu();
    }
}
