package gh2;

import deque.ArrayDeque;
import deque.Deque;




public class GuitarString {
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    /* Buffer for storing sound data. */
    private Deque<Double> buffer;

    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {
        buffer = new ArrayDeque<>();
        int capacity = (int) (Math.round(SR / frequency));
        for (int i = 0; i < capacity; i++) {
            buffer.addLast(0.0);
        }
    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        int size = buffer.size();
        for (int i = 0; i < size; i++) {
            buffer.removeFirst();
        }
        for (int i = 0; i < size; i++) {
            double r = Math.random() - 0.5;
            buffer.addFirst(r);
        }
    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {
        double newDouble = DECAY * ((buffer.get(0) + buffer.get(1)) / 2);
        buffer.removeFirst();
        buffer.addLast(newDouble);
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        return buffer.get(0);
    }
}

