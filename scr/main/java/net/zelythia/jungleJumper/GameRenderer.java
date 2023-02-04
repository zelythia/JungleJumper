package net.zelythia.jungleJumper;

import net.zelythia.jungleJumper.GameObjects.GameObject;
import net.zelythia.jungleJumper.GameObjects.Player;
import net.zelythia.jungleJumper.Utils.List.List;
import net.zelythia.jungleJumper.Utils.Utils;
import net.zelythia.jungleJumper.Utils.Vector2D;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.io.File;
import java.io.IOException;

//Panel = Screen = View
public class GameRenderer extends JPanel implements ActionListener {

    protected int camX;
    protected int camY;
    protected int camWidth;
    protected int camHeight;

    private final SpringLayout layout = new SpringLayout();
    private Image backgroundImage;

    public List<GameObject> gameObjects;

    public GameRenderer() {
        this.setFocusable(true);
        this.setCameraBounds(0,0,480,800);

        try {
            backgroundImage = ImageIO.read(new File("scr/main/resources/levleBg.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        gameObjects = new List<GameObject>();


        createUI();
    }

    JLabel scoreMultiLabel;
    JLabel timerLabel;

    public void createUI(){
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

    public void setTimer(int t){
        if(t==0){
            timerLabel.setText("0.00");
        }
        else{
            timerLabel.setText(Utils.time2String(t));
        }
    }

    /**
     * Newly added gameObjects will be rendered in front of the others
     */
    public void add(GameObject gameObject){
        gameObjects.add(gameObject);
    }

    public void clearGameObjects(){
        gameObjects.clear();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //Drawing the background
//        g.setColor(new Color(51, 102, 0));
//        g.fillRect(0, 0, 480, 800);

        g.drawImage(backgroundImage, 0, 0, 480, 900, this);

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

    public Vector2D getCameraPosition() {
        return new Vector2D(camX,camY);
    }

    public void render(Graphics graphics) {
        super.repaint();
    }


    public void displayFinishScreen(float score, float time){
        JPanel bg = new JPanel();
        bg.setBackground(new Color(0,0,0,150));
        bg.setOpaque(true);
        bg.setLayout(null);

        layout.putConstraint(SpringLayout.WEST, bg, 0, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.EAST, bg, 0, SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.NORTH, bg, 150, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.SOUTH, bg, -150, SpringLayout.SOUTH, this);

        JLabel finishedText = new JLabel("<HTML><U>FINISHED!</U></HTML>");
        finishedText.setFont(Utils.font.deriveFont(50f).deriveFont(Font.BOLD));
        finishedText.setForeground(Utils.WHITE);
        finishedText.setBounds(20,20,440,50);
        finishedText.setHorizontalAlignment(JLabel.CENTER);
        bg.add(finishedText);

        JLabel nameLabel = new JLabel("Name: " + JumpKing.username);
        nameLabel.setFont(Utils.font.deriveFont(40f).deriveFont(Font.BOLD));
        nameLabel.setForeground(Utils.WHITE);
        nameLabel.setBounds(20,100,440,50);
        nameLabel.setHorizontalAlignment(JLabel.CENTER);
        bg.add(nameLabel);

        JLabel scoreLAbel = new JLabel("Score: " + score);
        scoreLAbel.setFont(Utils.font.deriveFont(40f).deriveFont(Font.BOLD));
        scoreLAbel.setForeground(Utils.WHITE);
        scoreLAbel.setBounds(20,180,440,50);
        scoreLAbel.setHorizontalAlignment(JLabel.CENTER);
        bg.add(scoreLAbel);


        JLabel timeLabel = new JLabel("Time: " + time + "s");
        timeLabel.setFont(Utils.font.deriveFont(40f).deriveFont(Font.BOLD));
        timeLabel.setForeground(Utils.WHITE);
        timeLabel.setBounds(20,260,440,50);
        timeLabel.setHorizontalAlignment(JLabel.CENTER);
        bg.add(timeLabel);

        JButton mainMenu = new JButton("Main Menu");
        mainMenu.setBounds(80, 380, 320, 70);
        mainMenu.setFont(Utils.font.deriveFont(50f));
        mainMenu.setForeground(new Color(203, 203, 203));
        mainMenu.setBackground(new Color(64, 106, 71));
        mainMenu.setBorder(new LineBorder(Color.darkGray, 5));
        mainMenu.addActionListener(this);
        bg.add(mainMenu);

        this.add(bg);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JumpKing.loadMainMenu();
    }


    public static class DebugRenderer extends GameRenderer{

        private List<GameObject> debugObjects = new List<>();

        public DebugRenderer(){
            camWidth = 580;
            camHeight = 900;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            //Drawing the background
            g.setColor(Color.DARK_GRAY);
            g.fillRect(0, 0, 580, 900);

            RectangularShape camera = new Rectangle2D.Double(camX, camY, camWidth, camHeight);

            for(int i = 0; i < gameObjects.size; i++){
                g.setColor(Color.CYAN);

                if(camera.intersects(gameObjects.get(i).getShape().getBounds2D())){
                    GameObject gameObject = gameObjects.get(i);
                    if(gameObject instanceof Player){
                        g.setColor(Color.GREEN);
                    }
                    if(gameObject.getShape() instanceof Polygon p){
                        Polygon polygon = new Polygon(p.xpoints, p.ypoints, p.npoints);

                        polygon.translate(-camX, -camY);
                        g.drawPolygon(polygon);
                    }
                    else if(gameObject.getShape() instanceof RectangularShape shape){
                        g.drawRect(Math.round((float) shape.getBounds2D().getX()) - camX, Math.round((float) shape.getBounds2D().getY()) - camY, Math.round((float) shape.getBounds2D().getWidth()), Math.round((float) shape.getBounds2D().getHeight()));
                    }
                }
            }

            for(int i = 0; i < debugObjects.size; i++) {
                g.setColor(Color.MAGENTA);

                if (camera.intersects(debugObjects.get(i).getShape().getBounds2D())) {
                    GameObject gameObject = debugObjects.get(i);
                    if(gameObject.getShape() instanceof Polygon p){
                        Polygon polygon = new Polygon(p.xpoints, p.ypoints, p.npoints);

                        polygon.translate(-camX, -camY);
                        g.drawPolygon(polygon);
                    }
                    else if(gameObject.getShape() instanceof RectangularShape shape){
                        g.drawRect((int) shape.getBounds2D().getX() - camX, (int) shape.getBounds2D().getY() - camY, (int) shape.getBounds2D().getWidth(), (int) shape.getBounds2D().getHeight());
                    }
                }
            }

            clearDebugObjects();
            clearGameObjects();
        }

        public void addDebugObject(GameObject shape){
            debugObjects.add(shape);
        }

        public void clearDebugObjects(){
            debugObjects.clear();
        }
    }
}
