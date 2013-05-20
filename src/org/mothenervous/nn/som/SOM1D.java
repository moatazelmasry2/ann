package org.mothenervous.nn.som;

import java.util.ArrayList;
import java.util.List;

public class SOM1D extends AbstractSOM {

    List<SOMPoint> points = new ArrayList<SOMPoint>();

    private SOM1D() {

    }

    public static SOM1D create1DSOM(int length) {
        List<SOMPoint> points = new ArrayList<SOMPoint>();
        for (int i = 0; i < length; i++) {

            SOMPoint p = new SOMPoint(Blatt8Utils.randomVector(), Blatt8Utils.rand());
            if (((i != 0) && (i != length - 1))) {
                SOMPoint neighbor = points.get(i - 1);
                neighbor.getNeighbors().add(p);
                p.getNeighbors().add(neighbor);
            }
            if (i == length - 1) {
                SOMPoint neighbor = points.get(0);
                neighbor.getNeighbors().add(p);
                p.getNeighbors().add(neighbor);
            }
            points.add(p);
        }
        SOM1D som = new SOM1D();
        som.points = points;
        return som;
    }

    @Override
    public void train(Vector2D training) {

        super.train(training);
        double dist = 1.1;
        SOMPoint winnerNeuron = null;
        for (SOMPoint point : this.points) {
            double newDistance = training.minus(point.getCenter()).norm();
            if (newDistance < dist) {
                dist = newDistance;
                winnerNeuron = point;
            }
        }

        if (winnerNeuron != null) {
            for (SOMPoint neighbor : winnerNeuron.getNeighbors()) {
                double temp = this.learnDelta * this.gauss(winnerNeuron, neighbor);
                Vector2D center = neighbor.getCenter();
                Vector2D diff = training.minus(center);
                Vector2D delta = diff.mult(temp);
                neighbor.setCenter(center.plus(delta));
            }
        }
    }

    public List<SOMPoint> getPoints() {
        return this.points;
    }
}
