package net.zelythia.jungleJumper.Screens;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ImageBackground extends JPanel {

    Image backgroundImage;

    public ImageBackground(String path){
        try {
             backgroundImage = ImageIO.read(new File(path));
         } catch (IOException e) {
             throw new RuntimeException(e);
         }
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, backgroundImage.getWidth(this), backgroundImage.getHeight(this), this);
    }
}
