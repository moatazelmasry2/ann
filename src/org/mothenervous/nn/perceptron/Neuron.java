package org.mothenervous.nn.perceptron;

import java.util.ArrayList;
import java.util.List;

public abstract class Neuron {

    protected List<Edge> inputEdges = new ArrayList<Edge>();
    protected List<Edge> outEdges = new ArrayList<Edge>();
    protected double lastOutput = 0;

    public List<Edge> getInputEdges() {
        return this.inputEdges;
    }

    public List<Edge> getOutEdges() {
        return this.outEdges;
    }

    public boolean isInput() {
        return false;
    }

    public boolean isHidden() {
        return false;
    }

    public boolean isOutput() {
        return false;
    }

    public double getLastOutput() {
        return this.lastOutput;
    }

    public void setLastOutput(double lastOutput) {
        this.lastOutput = lastOutput;
    }
}
