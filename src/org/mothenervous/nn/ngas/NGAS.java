package org.mothenervous.nn.ngas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * NGas is a special form of a som network where there are no connection between entities of the network
 * 
 * @author elmasry
 *
 */
public class NGAS {

    public static double learnRate = 0.7;
    // width of gauss glocke
    public static double sigma = 0.4;
    // Number of nearest neighbors to train
    public int numNeighbors = 2;

    private final int n;
    private final int k;
    private final int fk;
    private double[][] training = null;
    public List<List<Neuron>> lists;

    /**
     * @param n
     *            : N Input Dimension
     * @param k
     *            number of neural sets
     * @param fk
     *            number of neurons in each set
     */
    public NGAS(int n, int k, int fk, double[][] training) {
        this.n = n;
        this.k = k;
        this.fk = fk;
        this.training = training;
        this.checkParams(n, k, fk, training);
        this.initNGas(n, k, fk, training);
    }

    public double[][] getTraining() {
        return this.training;
    }

    public void setTraining(double[][] training) {
        this.training = training;
    }

    public void trainNetwork(int numIterations) {
        for (int i = 0; i < numIterations; i++) {
            double[] t = Utils.getRandomArray(this.training);
            this.train(t);
        }
    }

    private void train(double[] pattern) {
        Neuron winner = this.getWinner(pattern);
        List<Neuron> neighbors = this.getNearestNeighbors(winner, this.numNeighbors);
        for (Neuron n : neighbors) {
            this.adaptCenter(winner, n, pattern);
        }
    }

    private Neuron getWinner(double[] pattern) {
        Neuron winner = new Neuron();
        winner.dist = 99999;
        for (List<Neuron> list : this.lists) {
            for (Neuron n : list) {
                double dist = Utils.calcEuclideanDistance(n.center, pattern);
                n.dist = dist;
                if (dist < winner.dist) {
                    winner = n;

                }
            }
        }
        return winner;
    }

    private void adaptCenter(Neuron ci, Neuron ck, double[] pattern) {
        double tmp = learnRate * Utils.gauss(ci.center, ck.center, sigma);
        double[] deltaCk = Utils.multVector(Utils.subtractVector(pattern, ck.center), tmp);
        ck.center = Utils.addVector(ck.center, deltaCk);
    }

    /**
     * Returns the nearest neighbors to a winning neuron
     * 
     * @param winner
     * @param numNeighbors
     * @return
     */
    private List<Neuron> getNearestNeighbors(Neuron winner, int numNeighbors) {
        List<Neuron> list = this.lists.get(winner.networkIndex);
        for (Neuron n : list) {
            double dist = Utils.calcEuclideanDistance(winner.center, n.center);
            n.dist = dist;
        }
        Collections.sort(list);
        // first element is the winner neuron itself, so we skip it
        return list.subList(1, numNeighbors + 1);
    }

    private void initNGas(int n, int k, int fk, double[][] training) {
        this.lists = new ArrayList<List<Neuron>>(k);

        for (int j = 0; j < k; j++) {
            List<Neuron> l = new ArrayList<Neuron>();
            for (int i = 0; i < fk; i++) {
                Neuron neuron = new Neuron();
                // Initializing the center of the neuron with a random training
                // pattern

                neuron.center = Utils.getRandomArray(training);
                neuron.networkIndex = j;
                l.add(neuron);
            }
            this.lists.add(l);
        }
    }

    private void checkParams(int n, int k, int fk, double[][] training) {
        if ((n < 1) || (n > 6)) {
            throw new IllegalArgumentException("Input dimension must be between (including 1 and 6)");
        }

        for (double[] t : training) {
            if (t.length != n) {
                throw new IllegalArgumentException("length of training pattern not equal input dimension");
            }
        }
    }
}
