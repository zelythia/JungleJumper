package net.zelythia;

import net.zelythia.GameObjects.GameObject;
import net.zelythia.List.List;

import javax.swing.*;
import java.awt.*;

//Panel = Screen = View
public class GameScreen extends JPanel implements Renderer {

    public List<GameObject> gameObjects;

    public GameScreen(){
        this.setFocusable(true);
        this.setBackground(Color.ORANGE);

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

        for(int i = 0; i < gameObjects.size; i++){
            gameObjects.get(i).draw(g, this);
        }
    }

    @Override
    public void render(Graphics graphics) {
        super.repaint();
    }
}
