package org.mothenervous.nn.perceptron;

import java.util.ArrayList;
import java.util.List;

public class Perceptron {

    final int input = 2;
    final int hidden;
    final int output = 1;
    final double learnRule = 0.4;

    List<InputNeuron> inputNeorons = new ArrayList<InputNeuron>(2);
    List<HiddenNeuron> hiddenNeorons = null;
    OutputNeuron outputNeoron = new OutputNeuron();

    public Perceptron(int hidden) {
        this.hidden = hidden;
        this.hiddenNeorons = new ArrayList<HiddenNeuron>(this.hidden);
        for (int i = 0; i < this.input; i++) {
            InputNeuron iNeuron = new InputNeuron();
            this.inputNeorons.add(iNeuron);
            for (int j = 0; j < this.hidden; j++) {

                HiddenNeuron h = null;
                // creating the hidden layer neurons
                if (i == 0) {
                    // Bias Neuron
                    iNeuron.setLastOutput(1);
                    h = new HiddenNeuron();
                    this.hiddenNeorons.add(h);
                    // connect hidden neuron to output neuron
                    Edge e1 = new Edge(h, this.outputNeoron, PerceptronUtils.generateRandom());
                    h.getOutEdges().add(e1);
                    this.outputNeoron.getInputEdges().add(e1);
                } else {
                    h = this.hiddenNeorons.get(j);
                }
                // Connecting input neurons to hidden neurons
                Edge edge = new Edge(iNeuron, h, PerceptronUtils.generateRandom());
                iNeuron.getOutEdges().add(edge);
                h.getInputEdges().add(edge);

            }
        }
    }

    /**
     * @param teacher
     *            to compare to the output
     * @param training
     *            pattern to apply into the network
     * @return error of the network (difference between teacher and output)
     */
    public double train(double teacher, double training) {
        double out = this.forward(training);
        this.backward(out, teacher);
        return out;
    }

    public double forward(double training) {
        this.inputNeorons.get(1).setLastOutput(training);

        // calculating outputs for Neurons in the hidden layer
        for (HiddenNeuron h : this.hiddenNeorons) {
            double weightedSum = 0;
            // Calculating weighted sum
            for (Edge iEdge : h.getInputEdges()) {
                weightedSum += iEdge.getWeight() * iEdge.getParent().getLastOutput();
            }
            double fermi = PerceptronUtils.calculateFermi(weightedSum);
            h.setLastOutput(fermi);
        }

        // Calculating weighted sum for output
        double weightedSumO = 0;
        for (Edge iEdge : this.outputNeoron.getInputEdges()) {
            weightedSumO += iEdge.getWeight() * iEdge.getParent().getLastOutput();
        }
        return PerceptronUtils.calculateFermi(weightedSumO);
    }

    public void backward(double output, double teacher) {

        // Compute delta output
        double deltaoutput = output * (1 - output) * (teacher - output);

        for (Edge edge : this.outputNeoron.getInputEdges()) {
            HiddenNeuron hidden = (HiddenNeuron) edge.getParent();
            double last = hidden.getLastOutput();
            // Compute delta hidden
            double deltaHidden = last * (1 - last) * edge.getWeight() * deltaoutput;
            hidden.setDelta(deltaHidden);
        }

        // compute weight Change
        // Update weight of edges connecting hidden Neurons to Output Neuron
        for (Edge edge : this.outputNeoron.getInputEdges()) {
            double deltaWeight = deltaoutput * edge.getParent().getLastOutput() * this.learnRule;
            edge.setWeight(edge.getWeight() + deltaWeight);
        }

        // update weight of edges connecting input and hidden neurons
        for (HiddenNeuron hidden : this.hiddenNeorons) {
            for (Edge edge : hidden.getInputEdges()) {
                double deltaWeight = hidden.getDelta() * edge.getParent().getLastOutput() * this.learnRule;
                edge.setWeight(edge.getWeight() + deltaWeight);
            }
        }
    }
}
