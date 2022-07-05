package net.zelythia.Utils;

public class Vector2D {

    public double x;
    public double y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D(Vector2D v) {
        set(v);
    }

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void set(Vector2D v) {
        this.x = v.x;
        this.y = v.y;
    }


    public double getLength() {
        return Math.sqrt(x * x + y * y);
    }

    public double getLengthSq() {
        return (x * x + y * y);
    }

    public void normalize() {
        double magnitude = getLength();
        x /= magnitude;
        y /= magnitude;
    }

    public Vector2D getNormalized() {
        double magnitude = getLength();
        return new Vector2D(x / magnitude, y / magnitude);
    }

    //=====

    public void clamp(double min, double max){
        if(x > max) x = max;
        else if(x < min) x = min;
        if(y > max) y = max;
        else if(y < min) y = min;
    }


    //=====

    public void add(Vector2D v) {
        this.x += v.x;
        this.y += v.y;
    }

    public void add(double vx, double vy) {
        this.x += vx;
        this.y += vy;
    }

    public static Vector2D add(Vector2D v1, Vector2D v2) {
        return new Vector2D(v1.x + v2.x, v1.y + v2.y);
    }

    public void subtract(Vector2D v) {
        this.x -= v.x;
        this.y -= v.y;
    }

    public void subtract(double vx, double vy) {
        this.x -= vx;
        this.y -= vy;
    }

    public static Vector2D subtract(Vector2D v1, Vector2D v2) {
        return new Vector2D(v1.x - v2.x, v1.y - v2.y);
    }

    public void multiply(double scalar) {
        x *= scalar;
        y *= scalar;
    }

    public void divide(double scalar) {
        x /= scalar;
        y /= scalar;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Vector2D) {
            Vector2D v = (Vector2D) obj;
            return (x == v.x) && (y == v.y);
        }
        return false;
    }

    @Override
    public String toString() {
        return "Vector2d[" + x + ", " + y + "]";
    }
}
