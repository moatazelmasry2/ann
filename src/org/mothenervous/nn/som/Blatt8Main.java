package org.mothenervous.nn.som;

import java.util.List;

public class Blatt8Main {

    public static void main(String[] args) {

        // Aufgabe 8.2. End
        // create2DUnitCircle und create2DRing ist Aufgabe 8.1
        List<Vector2D> training = Blatt8Utils.create2DUnitCircle(1000);
        Blatt8Utils.drawChart(training, "unitcircle.png");
        SOM2D som = SOM2D.create2DSOM(10, 10);
        som.setTrainingSet(training);

        String prefix = "2d_circle_";
        for (int i = 0; i < 100000; i++) {
            som.iteration();
            if (i % 1000 == 0) {
                StringBuilder builder = new StringBuilder(prefix);
                builder.append(i).append(".png");
                Blatt8Utils.drawChart(Blatt8Utils.som2DToVector2D(som.getPoints()), builder.toString());
            }
        }

        // Aufgabe8.3
        training = Blatt8Utils.create2DRing(1000, 0.3f, 1.0f);
        Blatt8Utils.drawChart(training, "ring.png");
        SOM1D som1d = SOM1D.create1DSOM(20);
        som1d.setTrainingSet(training);
        prefix = "ring_20_";
        for (int i = 0; i < 100000; i++) {
            som1d.iteration();
            if (i % 1000 == 0) {
                StringBuilder builder = new StringBuilder(prefix);
                builder.append(i).append(".png");
                Blatt8Utils.drawChart(Blatt8Utils.som1DToVector2D(som1d.getPoints()), builder.toString());
            }
        }

        // Aufgabe8.4
        som1d = SOM1D.create1DSOM(20);
        som1d.setTrainingSet(training);
        prefix = "ring_200_";
        for (int i = 0; i < 100000; i++) {
            som1d.iteration();
            if (i % 1000 == 0) {
                StringBuilder builder = new StringBuilder(prefix);
                builder.append(i).append(".png");
                Blatt8Utils.drawChart(Blatt8Utils.som1DToVector2D(som1d.getPoints()), builder.toString());
            }
        }
    }
}
