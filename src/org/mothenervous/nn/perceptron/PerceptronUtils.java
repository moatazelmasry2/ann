package org.mothenervous.nn.perceptron;


public class PerceptronUtils {

    public static double calculateFermi(double x) {
        double emx = Math.exp(-1 * x);
        return 1 / (1 + emx);
    }

    public static double fx(double x) {
        double cosx = Math.cos(x / 5);
        double abs = Math.abs(x);
        double sinx = Math.sin(1 / (abs + 1));
        return 100 * cosx + sinx - 50;
    }

    /**
     * @return random number between [-10,10]
     */
    public static double generateRandom() {
        double rand = Math.random() * 10;
        double rand2 = Math.random();
        if (rand2 > 0.5) {
            return rand;
        } else {
            return rand * (-1);
        }
    }
}
