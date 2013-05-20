package org.mothenervous.nn.som;

import java.util.ArrayList;
import java.util.List;

public class SOMPoint {

    private Vector2D center;
    private double sigma;
    private List<SOMPoint> neighbors = new ArrayList<SOMPoint>();

    public SOMPoint() {

    }

    public SOMPoint(Vector2D center, double sigma) {
        this.center = center;
        this.sigma = sigma;
    }

    public Vector2D getCenter() {
        return this.center;
    }

    public void setCenter(Vector2D center) {
        this.center = center;
    }

    public double getSigma() {
        return this.sigma;
    }

    public void setSigma(double sigma) {
        this.sigma = sigma;
    }

    public List<SOMPoint> getNeighbors() {
        return this.neighbors;
    }
}
