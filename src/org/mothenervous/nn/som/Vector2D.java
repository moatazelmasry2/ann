package org.mothenervous.nn.som;

public class Vector2D implements Comparable {

    double x;
    double y;

    public Vector2D() {

    }

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double norm() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public Vector2D minus(Vector2D v) {
        return new Vector2D(this.x - v.getX(), this.y - v.y);
    }

    public Vector2D plus(Vector2D vec) {
        double newx = this.x + vec.x;
        double newy = this.y + vec.y;
        return new Vector2D(newx, newy);
    }

    public Vector2D mult(double number) {
        double newx = this.x * number;
        double newy = this.y * number;
        return new Vector2D(newx, newy);
    }

    @Override
    public int compareTo(Object o) {
        Vector2D vec = (Vector2D) o;
        return (this.x > vec.x) && (this.y > vec.y) ? 1 : -1;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("(").append(this.x).append(",").append(this.y).append(")");
        return builder.toString();
    }
}
