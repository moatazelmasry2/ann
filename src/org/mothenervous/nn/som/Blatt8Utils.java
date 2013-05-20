package org.mothenervous.nn.som;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Blatt8Utils {

    // Number of circles
    private final static int numCircles = 10;
    // Distance between each two circles
    private final static float distanceCircles = 0.1f;
    // Radius of the largest circle
    private final static float radius = 1.0f;

    public static List<Vector2D> create2DUnitCircle(int numPoints) {
        int pointsProCircle = numPoints / numCircles;
        List<Vector2D> points = new ArrayList<Vector2D>();
        // Used to transfer from one circle to another after generating the
        // points
        float circlePos = distanceCircles;
        for (int i = 0; i < numCircles; i++) {
            double period = 360 / pointsProCircle;
            double degree = period;
            for (int j = 0; j < pointsProCircle; j++) {
                double cosAlpha = Math.cos(degree);
                double sinAlpha = Math.sin(degree);
                double x = sinAlpha * circlePos;
                double y = cosAlpha * circlePos;
                points.add(new Vector2D(x, y));
                degree += period;
            }
            // Moving to the next bigger circle
            circlePos += distanceCircles;
        }
        return points;
    }

    public static List<Vector2D> create2DRing(int numPoints, float width, float outerRadius) {
        int pointsProCircle = numPoints / numCircles;
        List<Vector2D> points = new ArrayList<Vector2D>();
        // Used to transfer from one circle to another after generating the
        // points
        float circlePos = outerRadius - width;
        for (int i = 0; i < numCircles; i++) {
            double period = 360 / pointsProCircle;
            double degree = period;
            for (int j = 0; j < pointsProCircle; j++) {
                double cosAlpha = Math.cos(degree);
                double sinAlpha = Math.sin(degree);
                double x = sinAlpha * circlePos;
                double y = cosAlpha * circlePos;
                points.add(new Vector2D(x, y));
                degree += period;
            }
            // Moving to the next bigger circle
            circlePos += distanceCircles;
        }
        return points;
    }

    public static void drawChart(List<Vector2D> points, String fileName) {

        XYSeriesCollection collection = new XYSeriesCollection();
        XYSeries series = new XYSeries(new String(""));

        for (Vector2D vec : points) {
            series.add(vec.getX(), vec.getY());
        }

        collection.addSeries(series);

        JFreeChart chart = ChartFactory.createScatterPlot("whatever", "x-axis", "y-axis", collection,
                PlotOrientation.HORIZONTAL, true, true, false);

        chart.setTextAntiAlias(true);
        chart.setAntiAlias(true);

        XYPlot plot = (XYPlot) chart.getPlot();

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(false, true);

        plot.setRenderer(renderer);

        BufferedImage image = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        chart.draw(g2, new Rectangle(new Dimension(500, 500)));
        File file = new File("output/" + fileName);
        try {
            ImageIO.write(image, "png", file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static double rand() {
        double rand = Math.random();
        if (rand == 0) {
            return 0.1;
        }
        if (rand == 1) {
            return 0.9;
        }
        return rand;
    }

    public static double randNegative() {
        double rand = Math.random();
        if (rand == 0) {
            rand = 0.1;
        }
        if (rand == 1) {
            rand = 0.9;
        }
        double rand2 = Math.random();
        if (rand2 <= 0.5) {
            rand *= -1;
        }

        return rand;
    }

    public static Vector2D randomVector() {
        double x = randNegative();
        double y = randNegative();
        return new Vector2D(x, y);
    }

    public static List<Vector2D> som1DToVector2D(List<SOMPoint> points) {
        List<Vector2D> out = new ArrayList<Vector2D>();
        for (SOMPoint point : points) {
            out.add(point.getCenter());
        }
        return out;
    }

    public static List<Vector2D> som2DToVector2D(List<List<SOMPoint>> points) {
        List<Vector2D> out = new ArrayList<Vector2D>();
        for (List<SOMPoint> columns : points) {
            for (SOMPoint point : columns) {
                out.add(point.getCenter());
            }
        }
        return out;
    }
}
