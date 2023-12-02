package flik;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestFlik {
    @Test
    public void testIsSameNumber() {
        int a = 10;
        int b = 10;
        assertTrue(Flik.isSameNumber(a,b));

        int N = 1000;
        for (int i = 0; i < N; i += 1) {
            int randVal = i;
            int sameVal = i;
            assertTrue(Flik.isSameNumber(randVal, sameVal));
            assertTrue("randVal is equal to sameVal", Flik.isSameNumber(randVal, sameVal));
        }

    }
}
