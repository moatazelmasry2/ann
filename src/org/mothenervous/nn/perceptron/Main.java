package org.mothenervous.nn.perceptron;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.mothenervous.nn.charts.ChartsUtils;
import org.mothenervous.nn.charts.QueuedMap;

public class Main {

    public Perceptron createNTrainPerceptron(int hiddenNeurons, String outputGraph) {
        Perceptron p = new Perceptron(hiddenNeurons);
        List<Double> errors = new ArrayList<Double>();

        StringBuilder builder1 = new StringBuilder(5000);
        StringBuilder builder2 = new StringBuilder(5000);
        StringBuilder builder3 = new StringBuilder(5000);

        for (int i = 0; i < 1001; i++) {
            double training = PerceptronUtils.generateRandom();
            double teacher = PerceptronUtils.fx(training);
            double output = p.train(teacher, training);
            double error = teacher - output;
            errors.add(new Double(error));
            builder1.append(i).append(",").append(training);
            builder2.append(i).append(",").append(teacher);
            builder3.append(i).append(",").append(error);
            if (i < 1000) {
                builder1.append(";");
                builder2.append(";");
                builder3.append(";");
            }
        }

        QueuedMap<String, String> keyedValues = new QueuedMap<String, String>();
        keyedValues.put("f(x)", builder1.toString());
        keyedValues.put("g(x)", builder2.toString());
        System.out.println(builder1.toString());
        // keyedValues.put("Fehler", builder3.toString());

        BufferedImage img = ChartsUtils.createXYLineChart("BPE", keyedValues, 500, 500, false, true,
                ChartsUtils.ORIENTATION_VERTICAL, "image/png", "Epochen", "Fehler");
        File outputfile = new File(outputGraph);
        try {
            ImageIO.write(img, "png", outputfile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }

    public static void main(String[] args) {
        try {
            Main m = new Main();
            m.createNTrainPerceptron(10, "hidden_10.png");
            m.createNTrainPerceptron(100, "hidden_100.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
