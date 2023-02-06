package net.zelythia.jungleJumper.GameObjects;

import net.zelythia.jungleJumper.Collision.CollisionType;

import java.awt.*;

public class Slope extends GameObject{

    private double slopeTranslateX;

    private Slope(Polygon shape, String sprite, double slopeTranslateX) {
        super(shape, sprite);
        this.slopeTranslateX = slopeTranslateX;
        super.setCollisionType(CollisionType.COLLIDE);
    }

    public static Slope Up(int x, int y, int width, int height){
        Polygon polygon = new Polygon(new int[]{width,width,0}, new int[]{0,height,height}, 3);
        polygon.translate(x,y);
        return new Slope(polygon, "slope/up.png", - (double) height/ (double) width);
    }

    public static Slope Down(int x, int y, int width, int height){
        Polygon polygon = new Polygon(new int[]{0,width,0}, new int[]{0,height,height}, 3);
        polygon.translate(x,y);
        return new Slope(polygon, "slope/down.png", (double) height/ (double) width);
    }

    public static Slope UpInv(int x, int y, int width, int height){
        Polygon polygon = new Polygon(new int[]{0,width,0}, new int[]{0,0,height}, 3);
        polygon.translate(x,y);
        return new Slope(polygon, "slope/up_inv.png", 0);
    }

    public static Slope DownInv(int x, int y, int width, int height){
        Polygon polygon = new Polygon(new int[]{0,width,width}, new int[]{0,0,height}, 3);
        polygon.translate(x,y);
        return new Slope(polygon, "slope/down_inv.png", 0);
    }




    private void setSlopeCollisionInteraction(double x){
        slopeTranslateX = x;
    }

    public double getSlopeCollisionInteraction(){
        return slopeTranslateX;
    }

    @Override
    public void setPos(double x, double y) {
        Polygon shape = (Polygon) getShape();
        shape.translate((int) x, (int) y);
        shape.invalidate();
    }
}
