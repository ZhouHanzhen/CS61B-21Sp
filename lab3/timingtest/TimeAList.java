package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeAList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeAListConstruction();
    }


    /**
     * Counting the time of creating a AList of size N using  function additive addLast();
     * @param N
     * @return timeInSeconds, the time of creating a AList of size N
     */
    public static double timeOneAListConstruction(int N){
        AList<Integer> sizeN = new AList<>();
        Stopwatch sw = new Stopwatch();
        for (int i = 0; i < N; i++) {
            sizeN.addLast(i);
        }
        double timeInSeconds = sw.elapsedTime();
        return timeInSeconds;
    }

    public static void timeAListConstruction() {
        // TODO: YOUR CODE HERE

        AList<Integer> Ns = new AList<>();
        AList<Integer> opCounts = new AList<>();
        AList<Double> times = new AList<>();

        //Create Ns, times, opCounts
        for (int N = 1000; N <= 128000; N = N * 2) {
            Ns.addLast(N);
            opCounts.addLast(N);
            double timeInSeconds = timeOneAListConstruction(N);
            times.addLast(timeInSeconds);
        }

        //print the tabulation
        printTimingTable(Ns, times, opCounts);
    }


}
