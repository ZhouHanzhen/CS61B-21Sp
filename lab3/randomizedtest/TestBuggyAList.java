package randomizedtest;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE

    @Test
    public void testThreeAddThreeRemove() {
        AListNoResizing<Integer> noResizingAList = new AListNoResizing<>();
        BuggyAList<Integer> bugAList = new BuggyAList<>();

        noResizingAList.addLast(6);
        bugAList.addLast(6);
        noResizingAList.addLast(17);
        bugAList.addLast(17);
        noResizingAList.addLast(23);
        bugAList.addLast(23);

        int a;
        int b;
        a = noResizingAList.removeLast();
        b = bugAList.removeLast();
        assertEquals(a, b);

        a = noResizingAList.removeLast();
        b = bugAList.removeLast();
        assertEquals(a, b);

        a = noResizingAList.removeLast();
        b = bugAList.removeLast();
        assertEquals(a, b);

    }

    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> bugL = new BuggyAList<>();

        int N = 50000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                bugL.addLast(randVal);
                //System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                // size
                int size = L.size();
                int bugSize = bugL.size();
                //System.out.println("size: " + size);
                //System.out.println("bugSize: " + bugSize);

                assertEquals(size, bugSize);
            } else if (operationNumber == 2) {
                //getLast
                if ((L.size() == 0) || (bugL.size() == 0)) {
                    //System.out.println("size: " + 0);
                } else {
                    int gLast = L.getLast();
                    //System.out.println("getLast(" + gLast + ")");
                    int bugGLast = bugL.getLast();
                    //System.out.println("bugGetLast(" + bugGLast + ")");

                    assertEquals(gLast, bugGLast);
                }
            } else if (operationNumber == 3) {
                //removeLast
                if ((L.size() == 0) || (bugL.size() == 0)) {
                    //System.out.println("size: " + 0);
                } else {
                    int rLast = L.removeLast();
                    //System.out.println("removeLast(" + rLast + ")");
                    int bugRLast = bugL.removeLast();
                   // System.out.println("removeLast(" + bugRLast + ")");

                    assertEquals(rLast, bugRLast);
                }
            }
        }
    }

}
