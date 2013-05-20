package org.mothenervous.nn.perceptron;

public class HiddenNeuron extends Neuron {

    double delta;

    public double getDelta() {
        return this.delta;
    }

    public void setDelta(double delta) {
        this.delta = delta;
    }

    @Override
    public boolean isHidden() {
        return true;
    }

    public double calcOutput() {
        return 0;
    }
}
