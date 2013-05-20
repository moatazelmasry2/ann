package org.mothenervous.nn.som;

import java.util.ArrayList;
import java.util.List;

public class SOM2D extends AbstractSOM {

    private List<List<SOMPoint>> points = new ArrayList<List<SOMPoint>>();

    private SOM2D() {
    };

    public static SOM2D create1DSOM(int length, boolean cyclic) {
        return null;
    }

    public static SOM2D create2DSOM(int length, int width) {

        List<List<SOMPoint>> lines = new ArrayList<List<SOMPoint>>();
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                List<SOMPoint> column = new ArrayList<SOMPoint>();
                lines.add(column);
                SOMPoint p = new SOMPoint(Blatt8Utils.randomVector(), Blatt8Utils.rand());
                if ((i != 0)) {
                    SOMPoint neighbor = lines.get(i - 1).get(j);
                    neighbor.getNeighbors().add(p);
                    p.getNeighbors().add(neighbor);
                }

                if (j != 0) {
                    SOMPoint neighbor = lines.get(i).get(j - 1);
                    neighbor.getNeighbors().add(p);
                    p.getNeighbors().add(neighbor);
                }
                lines.get(i).add(p);
            }
        }
        SOM2D som = new SOM2D();
        som.points = lines;
        return som;
    }

    public List<List<SOMPoint>> getPoints() {
        return this.points;
    }

    @Override
    public void train(Vector2D training) {

        super.train(training);
        double dist = 1.1;
        SOMPoint winnerNeuron = null;
        for (List<SOMPoint> lines : this.points) {
            for (SOMPoint point : lines) {
                double newDistance = training.minus(point.getCenter()).norm();
                if (newDistance < dist) {
                    dist = newDistance;
                    winnerNeuron = point;
                }
            }
        }

        for (SOMPoint neighbor : winnerNeuron.getNeighbors()) {
            double temp = this.learnDelta * this.gauss(winnerNeuron, neighbor);
            Vector2D center = neighbor.getCenter();
            Vector2D diff = training.minus(center);
            Vector2D delta = diff.mult(temp);
            neighbor.setCenter(center.plus(delta));
        }
    }

}
