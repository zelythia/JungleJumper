package net.zelythia;

import net.zelythia.GameObjects.GameObject;
import net.zelythia.List.List;

import javax.swing.*;
import java.awt.*;

//Panel = Screen = View
public class GameRenderer extends JPanel implements Renderer {

    private int camX;
    private int camY;
    private int camWidth;
    private int camHeight;


    public List<GameObject> gameObjects;

    public GameRenderer(){
        this.setFocusable(true);
        this.setCameraBounds(0,0,480,800);

        gameObjects = new List<GameObject>();
    }


    public void addGameObject(GameObject gameObject){
        gameObjects.add(gameObject);
    }

    @Override
    public JPanel getPanel() {
        return this;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //Drawing the background
        g.setColor(new Color(51, 102, 0));
        g.fillRect(camX,camY,camWidth,camHeight);


        Rectangle camera = new Rectangle(camX,camY,camWidth,camHeight);

        for(int i = 0; i < gameObjects.size; i++){

            GameObject gameObject = gameObjects.get(i);

            //If the gameObjects is inside the camera i.e. visible -> Draw the gameObject
            if(camera.intersects(gameObject.getShape().getBounds2D())){
                g.drawImage(gameObject.getSprite(),
                        (int) (gameObject.getX() - camX), (int) (gameObject.getY() - camY),
                        (int) gameObject.getShape().getWidth(), (int) gameObject.getShape().getHeight(),
                        this
                    );
            }

        }
    }

    public void setCameraBounds(int x, int y, int width,int height){
        this.camX = x;
        this.camY = y;
        this.camWidth = width;
        this.camHeight = height;
    }

    @Override
    public void render(Graphics graphics) {
        super.repaint();
    }
}
