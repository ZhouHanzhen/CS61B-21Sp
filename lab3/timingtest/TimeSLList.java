package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeSLList {
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
        timeGetLast();
    }


    /**
     * Record the time of performing M getLast operations on an SLList
     * @param N : the size of the SLList
     * @param M : times of getLast operations
     * @return timeInSeconds :the time of performing M getLast operations
     */
    public static double timeSLListAddLast(int N, int M) {
        //Create an SLList
        SLList<Integer> intSLL = new SLList<>();

        //Add N items to the SLList
        for (int i = 0; i < N; i++) {
            intSLL.addFirst(i);
        }

        //Start the timer
        Stopwatch sw = new Stopwatch();

        //Perform M getLast operations on the SLList
        for (int j = 0; j < M; j++) {
            int num = intSLL.getLast();
        }

        //Check the timer
        double timeInSeconds = sw.elapsedTime();

        return timeInSeconds;
    }

    public static void timeGetLast() {
        // TODO: YOUR CODE HERE

        //Create Ns, opCounts, times
        AList<Integer> Ns = new AList<>();
        AList<Integer> opCounts = new AList<>();
        AList<Double> times = new AList<>();
        int M = 10000;

        //Add items to Ns, opCounts, times
        for (int N = 1000; N <= 128000; N = N * 2) {
            Ns.addLast(N);
            opCounts.addLast(M);

            double timeInSeconds = timeSLListAddLast(N, M);
            times.addLast(timeInSeconds);
        }

        //print the tabulation
        printTimingTable(Ns, times, opCounts);
    }

}
