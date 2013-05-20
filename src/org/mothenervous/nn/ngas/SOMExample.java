package blatt7;

public class SOMExample {

    public static void main(String[] args) {

        int n = 2;
        int k = 2;
        int fk = 16;

        // 0.1 ≤ x1 ≤ 0.2 und a 0.1 ≤ x2 ≤ 0.5 und dem Bereich 0.7 ≤ x1 ≤ 0.9
        // und 0.7 ≤ x2 ≤ 0.9

        double[][] t1 = Utils.createTrainingPattern(100, 0.1, 0.2, 0.1, 0.5);
        double[][] t2 = Utils.createTrainingPattern(100, 0.7, 0.9, 0.7, 0.9);
        double[][] training = Utils.joinArrays(t1, t2);
        NGAS ngas = new NGAS(n, k, fk, training);
        for (int i = 0; i < 20; i++) {
            ngas.trainNetwork(1000);
            String filename = "pic" + i + ".png";
            Utils.drawChart(ngas.lists, filename);
        }
    }
}
