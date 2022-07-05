package net.zelythia.Utils;

public class Utils {

    public static int lerp(int a, int b, int f){
        return a + f * (b - a);
    }

    public static float lerp(float a, float b, float f){
        return a + f * (b - a);
    }

    public static double lerp(double a, double b, double f){
        return a + f * (b - a);
    }


}
