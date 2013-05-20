package blatt7;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Utils {

    /**
     * calculates the euclidean distance between two vectors with the same
     * length
     * 
     * @param p1
     * @param p2
     * @return
     */
    public static double calcEuclideanDistance(double[] p1, double[] p2) {
        if (p1.length != p2.length) {
            throw new IllegalArgumentException();
        }
        double result = 0;
        for (int i = 0; i < p1.length; i++) {
            result += Utils.square(p1[i] - p2[i]);
        }

        return Math.sqrt(result);
    }

    /**
     * Casts an array of Double[] to double[]
     * 
     * @return
     */
    public static double[] castDoubleToPrimitive(Double[] input) {
        double[] result = new double[input.length];
        for (int i = 0; i < input.length; i++) {
            result[i] = input[i];
        }
        return result;
    }

    /**
     * Creates a random equally distributed training pattern
     * 
     * @param numPatterns
     * @param minX
     * @param maxX
     * @param minY
     * @param maxY
     * @return
     */
    public static double[][] createTrainingPattern(int numPatterns, double minX, double maxX, double minY, double maxY) {
        double[] xList = new double[numPatterns];
        double[] yList = new double[numPatterns];

        double x = minX;
        double y = minY;
        double xStep = (maxX - minX) / numPatterns;
        double yStep = (maxY - minY) / numPatterns;
        double[][] result = new double[numPatterns][];

        for (int i = 0; i < numPatterns; i++) {
            xList[i] = new Double(x);
            x += xStep;
            yList[i] = new Double(y);
            y += yStep;
        }

        for (int i = 0; i < numPatterns; i++) {
            x = Utils.getRandom(xList);
            y = Utils.getRandom(yList);
            result[i] = new double[] { x, y };

        }

        return result;
    }

    public static double[] subtractVector(double[] p1, double[] p2) {
        double[] result = new double[p1.length];
        for (int i = 0; i < p1.length; i++) {
            result[i] = p1[i] - p2[i];
        }
        return result;
    }

    public static double[] addVector(double[] p1, double[] p2) {
        double[] result = new double[p1.length];
        for (int i = 0; i < p1.length; i++) {
            result[i] = p1[i] + p2[i];
        }
        return result;
    }

    public static double[] multVector(double[] vector, double num) {
        for (int i = 0; i < vector.length; i++) {
            vector[i] *= num;
        }
        return vector;
    }

    public static double square(double num) {
        return num * num;
    }

    public static double gauss(double[] ci, double[] ck, double sigma) {
        double result = Utils.calcEuclideanDistance(ci, ck);
        result *= result;
        result /= (2 * sigma);
        return Math.exp(result);
    }

    /**
     * Returns a random one dimensional array from a two dimensional array
     * 
     * @param array
     * @return
     */
    public static double[] getRandomArray(double[][] array) {
        Random r = new Random();
        int index = r.nextInt() % array.length - 1;
        if (index < 0) {
            index = 0;
        }
        return array[index];
    }

    /**
     * Returns a random double from an array of double
     * 
     * @param array
     * @return
     */
    public static double getRandom(double[] array) {
        Random r = new Random();
        int index = r.nextInt() % array.length - 1;
        if (index < 0) {
            index = 0;
        }
        return array[index];
    }

    public static double[][] joinArrays(double[][] a1, double[][] a2) {
        double[][] array = new double[a1.length + a2.length][];
        for (int i = 0; i < a1.length; i++) {
            array[i] = a1[i];
        }
        for (int i = 0; i < a2.length; i++) {
            array[i + a1.length] = a2[i];
        }
        return array;
    }

    public static void drawChart(List<List<Neuron>> points, String fileName) {

        XYSeriesCollection collection = new XYSeriesCollection();
        XYSeries series = new XYSeries(new String(""));

        for (int i = 0; i < points.size(); i++) {
            List<Neuron> l = points.get(i);
            for (int j = 0; j < l.size(); j++) {
                Neuron n = l.get(j);
                series.add(n.center[0], n.center[1]);
            }

            collection.addSeries(series);
        }

        JFreeChart chart = ChartFactory.createScatterPlot("Multi-NGas", "x-axis", "y-axis", collection,
                PlotOrientation.VERTICAL, true, true, false);

        chart.setTextAntiAlias(true);
        chart.setAntiAlias(true);

        XYPlot plot = (XYPlot) chart.getPlot();

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(false, true);
        renderer.setBaseShapesFilled(false);

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
}
