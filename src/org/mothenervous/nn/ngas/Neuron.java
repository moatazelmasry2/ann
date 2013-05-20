package blatt7;

public class Neuron implements Comparable<Neuron> {

    public double[] center;
    // distance to the input vector
    public double dist = 99999;

    // a number to indicate to which Network this Neuron belongs to
    public int networkIndex;

    @Override
    public int compareTo(Neuron o) {
        if (this.dist < o.dist) {
            return -1;
        } else if (this.dist == o.dist) {
            return 0;
        } else {
            return 1;
        }
    }
}
