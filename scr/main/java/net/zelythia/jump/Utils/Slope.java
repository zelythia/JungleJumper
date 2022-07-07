package net.zelythia.jump.Utils;

import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;

public class Slope extends RectangularShape {

    float x;
    float y;
    float w;
    float h;
    TYPE type;

    public enum TYPE{
        steep, normal, shallow
    }

    public Slope(float x, float y, float w, float h, TYPE type){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.type = type;
    }


    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public double getWidth() {
        return w;
    }

    @Override
    public double getHeight() {
        return h;
    }

    @Override
    public boolean isEmpty() {
        return w == 0 && h == 0;
    }

    @Override
    public void setFrame(double x, double y, double w, double h) {
        this.x = (float) x;
        this.y = (float) y;
        this.w = (float) w;
        this.h = (float) h;
    }

    @Override
    public Rectangle2D getBounds2D() {
        return new Rectangle2D.Float(x, y, w, h);
    }




    @Override
    public boolean contains(double x, double y) {
        return false;

        if(type == TYPE.normal){

        }
    }

    @Override
    public boolean intersects(double x, double y, double w, double h) {
        return false;
    }

    @Override
    public boolean contains(double x, double y, double w, double h) {
        return false;
    }







    @Override
    public PathIterator getPathIterator(AffineTransform at) {
        return null;
    }
}
