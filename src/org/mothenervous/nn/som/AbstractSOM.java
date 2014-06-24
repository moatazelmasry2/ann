package org.mothenervous.nn.som;

import java.util.List;

public abstract class AbstractSOM {

    protected double learnDelta = 0.4;
    protected List<Vector2D> trainingSet = null;
    //comment further
    protected double gauss(SOMPoint source, SOMPoint destination) {
        double norm = source.getCenter().minus(destination.getCenter()).norm();
        double sigma = destination.getSigma();
        double param = (norm * norm) / (2 * sigma * sigma);
        return Math.exp(-1 * param);
    }

    public void train(Vector2D training) {
        if (this.learnDelta > 0.01) {
            this.learnDelta -= 0.0001;
        }
    }

    public void setTrainingSet(List<Vector2D> trainingSet) {
        this.trainingSet = trainingSet;
    }

    public void iteration() {
        if (this.trainingSet == null) {
            System.err.println("Training set is empty");
            return;
        }

        for (Vector2D training : this.trainingSet) {
            this.train(training);
        }

    }
}
