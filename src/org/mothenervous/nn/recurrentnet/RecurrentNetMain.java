package org.mothenervous.nn.recurrentnet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

import org.encog.neural.activation.ActivationTANH;
import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.data.basic.BasicNeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.training.Train;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;
import org.encog.neural.pattern.ElmanPattern;
import org.encog.util.Format;

import charts.ChartsUtils;
import charts.QueuedMap;

public class RecurrentNetMain {

    private StringBuilder errors = new StringBuilder();
    private StringBuilder meanLearnRate = new StringBuilder();
    // Train the network for 8 hours
    private final int TRAINING_PERIOD = 480;

    public RecurrentNetMain() {

        BasicNetwork n = this.createRecurrentNetwork(2, 3, 1);

        // The maximum number from 5 bits is 31
        int combinations = 31;
        List<NeuralDataSet> list = this.createTrainData(combinations, 5);
        ResilientPropagation rp = new ResilientPropagation(n, list.get(0));

        // Train the network for 8 hours, and switch between training pattern
        // every one minute
        int numMinutes = 1;
        Iterator<NeuralDataSet> it = list.iterator();
        int counter = 0;
        while (it.hasNext()) {
            this.trainConsole(rp, n, it.next(), numMinutes, "log.txt");
            counter++;
            if (counter >= this.TRAINING_PERIOD) {
                break;
            }
            // we reached the end of the list. go the the list begining
            if (!it.hasNext()) {
                it = list.iterator();
            }
        }

        // After training the network we check to see if it deliveres the
        // correct answers
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new FileWriter("log.txt", true), true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Max number we can represent with 8 bits is 255
        for (int i = 0; i < 255; i++) {
            for (int j = 0; j < 255; j++) {
                NeuralDataSet nds = this.createBinaryNeuralDataSet(i, j, i + j, 8);
                rp.setTraining(nds);
                rp.iteration();
                String message = "The Error when calculating " + i + "" + j + " is " + rp.getError();
                System.out.println(message);
                writer.println(message);
            }
        }

        writer.close();

    }

    public BasicNetwork createRecurrentNetwork(int input, int hidden, int output) {
        // This is an implementation of the Recurrent ntwork required in the
        // Assigment
        // On setting the number of hidden Neurons, two layers are created, a
        // BasicLayer and a ContextLayer
        // The context Layer is implemented for the recurrent network. It feeds
        // its output back into its BasicLayer
        // So the Network looks as Follows> Input->Hidden->Context->Output
        // This way we unfolded the network using BPTT in 2-steps as required by
        // the assigment
        // If we need more than 2-steps, then we can create another ContextLayer
        // which feeds its output to the old ContextLayer
        ElmanPattern ep = new ElmanPattern();
        ep.setInputNeurons(2);
        ep.setOutputNeurons(1);
        ep.addHiddenLayer(3);
        // Use TanH function as activation function
        ep.setActivationFunction(new ActivationTANH());
        BasicNetwork network = ep.generate();
        network.getStructure().finalizeStructure();
        // (new ConsistentRandomizer(-1, 1)).randomize(network);
        return network;
    }

    public void createGraph(String errors, String learnRates, String imagePath) {
        QueuedMap<String, String> keyedValues = new QueuedMap<String, String>();

        if (errors != null) {
            keyedValues.put("Errors", errors.toString());
        }
        if (learnRates != null) {
            keyedValues.put("Mean Learnrate", learnRates.toString());
        }

        BufferedImage img = ChartsUtils.createXYLineChart("RProp", keyedValues, 500, 500, false, true,
                ChartsUtils.ORIENTATION_VERTICAL, "image/png", "Epochen", "Fehler");
        File outputfile = new File(imagePath);
        try {
            ImageIO.write(img, "png", outputfile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Train the network, using the specified training algorithm, and send the
     * output to the console.
     * 
     * @param train
     *            The training method to use.
     * @param network
     *            The network to train.
     * @param trainingSet
     *            The training set.
     * @param minutes
     *            The number of minutes to train for.
     * @param imgPath
     *            creates a graph representing the error development
     * @param filePath
     *            writes the training output in a log file
     */
    private void trainConsole(final Train train, final BasicNetwork network, final NeuralDataSet trainingSet,
            final int minutes, String filePath) {

        int epoch = 1;
        long remaining;

        System.out.println("Beginning training...");
        final long start = System.currentTimeMillis();

        train.iteration();

        double stError = train.getError();

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new FileWriter(filePath, true), true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ResilientPropagation rp = (ResilientPropagation) train;
        do {
            train.iteration();

            this.errors.append(epoch).append(",").append(train.getError()).append(";");
            this.meanLearnRate.append(epoch).append(",").append(train.getError()).append(";");
            final long current = System.currentTimeMillis();
            final long elapsed = (current - start) / 1000;// seconds
            remaining = minutes - elapsed / 60;

            String message = "Iteration #"
                    + Format.formatInteger(epoch)
                    + " Error:"
                    // + Format.formatPercent(train.getError()) +
                    // " elapsed time = "
                    + train.getError()
                    // + " elapsed time = " + Format.formatTimeSpan((int)
                    // elapsed)
                    + " time left = " + Format.formatTimeSpan((int) remaining * 60) + "mean LearnRule="
                    + this.getMeanValue(rp.getLastGradient());
            System.out.println(message);
            if (writer != null) {
                writer.println(message);
            }
            epoch++;
        } while (remaining > 0);

        double lastError = train.getError();
        System.out.println("firstError - lastError in this iteration=" + (stError - lastError));

        try {
            if (writer != null) {
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param trainData
     *            null. It will be filled by the function
     * @param teacher
     *            null. It will be filled by the function
     * @param dataSize
     *            size of trainData and teacher
     */
    public List<NeuralDataSet> createTrainData(int combinations, int numBits) {

        List<NeuralDataSet> list = new ArrayList<NeuralDataSet>();

        for (int i = 0; i < combinations; i++) {
            for (int j = 0; j < combinations; j++) {
                list.add(this.createBinaryNeuralDataSet(i, j, i + j, numBits));
            }
        }
        return list;
    }

    /**
     * <code>
     * As described in the assigment, it takes two numbers and break them into
     * their binary form. then form a training pattern/array for them and the
     * teacher. For example we want to have 3 + 5 = 8 in 4 bit representation, where:  
     * 3 = 0011, 5 = 0101, 8 = 1000. 
     * So the training array will look like: double[][] trainData = { { 1, 1 }, { 0, 1 }, { 1, 0 }, { 0, 0 } };
     *  and  double[][] teacher = { { 0 }, { 0 }, { 0 }, { 1 } };
     *   Note that the bits are represented from right to left (least significant bit)
     *   </code>
     * 
     * @param intput1
     * @param input2
     * @param idealOutput
     * @param numBits
     * @return
     */
    private NeuralDataSet createBinaryNeuralDataSet(int intput1, int input2, int idealOutput, int numBits) {
        double[][] trainData = new double[numBits][2];
        double[][] teacher = new double[numBits][1];
        String x = this.continueLeadingZeros(Integer.toBinaryString(intput1), numBits);
        String y = this.continueLeadingZeros(Integer.toBinaryString(input2), numBits);
        String output = this.continueLeadingZeros(Integer.toBinaryString(idealOutput), numBits);
        for (int i = numBits - 1; i >= 0; i--) {
            String tmp = String.valueOf(x.charAt(i));
            trainData[i][0] = Double.parseDouble((tmp));
            tmp = String.valueOf(y.charAt(i));
            trainData[i][1] = Double.parseDouble((tmp));
            tmp = String.valueOf(output.charAt(i));
            teacher[i][0] = Double.parseDouble((tmp));
        }
        return new BasicNeuralDataSet(trainData, teacher);
    }

    private String continueLeadingZeros(String num, int numBits) {
        StringBuilder builder = new StringBuilder(num);
        while (builder.length() < numBits) {
            builder.insert(0, "0");
        }
        return builder.toString();
    }

    /**
     * Reverse the given Integer and append to it trailing zeros if needed to
     * reach a certain size, 5 or 8 in our case Integer.reverse() is not
     * alternative for such a problem.
     * 
     * @param i
     * @param numBits
     * @return
     */
    private double getReverseDouble(int i, int numBits) {
        String num = Integer.toBinaryString(i);
        StringBuilder builder = new StringBuilder().append(num);
        while (builder.length() < numBits) {
            // Append zeros on the left hand side
            builder.insert(0, "0");
        }
        // reverse and return the string
        return Double.parseDouble(builder.reverse().toString());
    }

    private double getMeanValue(double[] values) {
        double sum = 0;
        for (double i : values) {
            sum += i;
        }
        return sum / values.length;
    }

    public static void main(String[] args) {
        new RecurrentNetMain();
    }
}
