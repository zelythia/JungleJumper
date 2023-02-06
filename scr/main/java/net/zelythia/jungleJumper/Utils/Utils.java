package net.zelythia.jungleJumper.Utils;

import net.zelythia.jungleJumper.JumpKing;

import java.awt.*;
import java.io.IOException;

public class Utils {

    public static Font font;
    public static Color WHITE = new Color(203, 203, 203);

    static{
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, JumpKing.class.getClassLoader().getResourceAsStream("EduVICWANTBeginner-Bold.ttf"));
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static int lerp(int a, int b, int f){
        return a + f * (b - a);
    }

    public static float lerp(float a, float b, float f){
        return a + f * (b - a);
    }

    public static double lerp(double a, double b, double f){
        return a + f * (b - a);
    }

    public static String time2String(int t){
        String time = Float.toString(t / 1000f);
        if(time.substring(time.indexOf(".")+1).length() > 2){
            time = time.substring(0, time.length()-1);
        }

        return time;
    }

}
