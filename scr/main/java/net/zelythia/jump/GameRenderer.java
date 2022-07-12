package net.zelythia.jump;

import net.zelythia.jump.GameObjects.GameObject;
import net.zelythia.jump.Utils.List.List;
import net.zelythia.jump.Utils.Vector2D;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;

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

        createUI();
    }

    JLabel scoreMultiLabel;
    JLabel timerLabel;

    public void createUI(){
        SpringLayout layout = new SpringLayout();
        this.setLayout(layout);

        scoreMultiLabel = new JLabel();
        this.add(scoreMultiLabel);
        scoreMultiLabel.setOpaque(true);
        scoreMultiLabel.setBackground(new Color(0,0,0,0));
        scoreMultiLabel.setForeground(new Color(255, 255, 255));
        scoreMultiLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD,  25));
        layout.putConstraint(SpringLayout.WEST, scoreMultiLabel, 10, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, scoreMultiLabel, 5, SpringLayout.NORTH, this);

        timerLabel = new JLabel();
        this.add(timerLabel);
        timerLabel.setOpaque(true);
        timerLabel.setBackground(new Color(0,0,0,0));
        timerLabel.setForeground(new Color(255, 255, 255));
        timerLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD,  25));

        layout.putConstraint(SpringLayout.EAST, timerLabel, -10, SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.NORTH, timerLabel, 5, SpringLayout.NORTH, this);
    }

    public void setScoreMulti(Float m){
        scoreMultiLabel.setText(Float.toString(m));
    }

    public void setTimer(Float t){
        timerLabel.setText(Float.toString(t));
    }

    /**
     * Newly added gameObjects will be rendered in front of the others
     */
    public void addGameObject(GameObject gameObject){
        gameObjects.add(gameObject);
    }

    @Override
    public void clearGameObjects(){
        gameObjects.clear();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //Drawing the background
        g.setColor(new Color(51, 102, 0));
        g.fillRect(0, 0, 480, 800);


        RectangularShape camera = new Rectangle2D.Double(camX, camY, camWidth, camHeight);

        for(int i = 0; i < gameObjects.size; i++){
            GameObject gameObject = gameObjects.get(i);

            //If the gameObjects is inside the camera i.e. visible -> Draw the gameObject
            if(camera.intersects(gameObject.getShape().getBounds2D())){
                g.drawImage(
                        gameObject.getSprite(),
                        (int) (gameObject.getX() - camX), (int) (gameObject.getY() - camY),
                        (int) gameObject.getBounds().getWidth(), (int) gameObject.getBounds().getHeight(),
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

    public void setCameraPosition(int x, int y){
        this.camX = x;
        this.camY = y;
    }

    @Override
    public Vector2D getCameraPosition() {
        return new Vector2D(camX,camY);
    }

    @Override
    public void render(Graphics graphics) {
        super.repaint();
    }


    public static class DebugRenderer extends GameRenderer{
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            //Drawing the background
            g.setColor(Color.DARK_GRAY);
            g.fillRect(0, 0, 480, 800);


            for(int i = 0; i < gameObjects.size; i++){
                g.setColor(Color.CYAN);

                GameObject gameObject = gameObjects.get(i);
                if(gameObject.getShape() instanceof Polygon polygon){
                    g.drawPolygon(polygon);
                }
                else if(gameObject.getShape() instanceof RectangularShape shape){
                    g.drawRect((int) shape.getBounds2D().getX(), (int) shape.getBounds2D().getY(), (int) shape.getBounds2D().getWidth(), (int) shape.getBounds2D().getHeight());
                }
            }

        }
    }
}