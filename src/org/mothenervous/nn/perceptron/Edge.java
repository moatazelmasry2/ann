package org.mothenervous.nn.perceptron;

public class Edge {

    private double weight;
    private Neuron parent;
    private Neuron child;

    public Edge(Neuron parent, Neuron child, double weight) {
        this.parent = parent;
        this.child = child;
        this.weight = weight;
    }

    public double getWeight() {
        return this.weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Neuron getParent() {
        return this.parent;
    }

    public void setParent(Neuron parent) {
        this.parent = parent;
    }

    public Neuron getChild() {
        return this.child;
    }

    public void setChild(Neuron child) {
        this.child = child;
    }

}
