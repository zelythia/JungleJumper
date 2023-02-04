package net.zelythia.jungleJumper;


import net.zelythia.jungleJumper.Screens.MainMenu;
import net.zelythia.jungleJumper.Screens.Scores;

public class JumpKing {

    public static void main(String[] args) {
        new JumpKing();
    }

    public static WindowManager windowManager;

    public static String username = "";

    public JumpKing(){
        windowManager = new WindowManager();

        DB.getSessionID();
        loadMainMenu();
    }

    //TODO Time not correctly in DB

    public static void loadMainMenu(){
        windowManager.setPanel(new MainMenu());
    }

    public static void loadScoreMenu(){
        Scores panel = new Scores();

        windowManager.setPanel(panel);
    }

    static long WAIT_TIME = 16666666L;
    public static void initializeGameScene(){
        GameRenderer panel = new GameRenderer();
        windowManager.setPanel(panel);


        GameEngine engine = new GameEngine(panel);

        windowManager.addKeyListener(engine);

        //For debugging only
//        WAIT_TIME = 150000000L;

        //Creating the game loop
        new Thread( () -> {
            long delta = 0l;

            while (true) {
                long lastTime = System.nanoTime();

                engine.update((float)(delta / 1000000000.0));
                engine.render(windowManager.getGraphics());

                delta = System.nanoTime() - lastTime;
                if (delta < WAIT_TIME) {
                    try {
                        // 16666666 is roughly 60 calls per second
                        // Dividing to convert to milliseconds
                        Thread.sleep((WAIT_TIME - delta) / 1000000L);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }).start();
    }
}