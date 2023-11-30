package hw3.hash;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TestComplexOomage {

    @Test
    public void testHashCodeDeterministic() {
        ComplexOomage so = ComplexOomage.randomComplexOomage();
        int hashCode = so.hashCode();
        for (int i = 0; i < 100; i += 1) {
            assertEquals(hashCode, so.hashCode());
        }
    }

    /* This should pass if your OomageTestUtility.haveNiceHashCodeSpread
       is correct. This is true even though our given ComplexOomage class
       has a flawed hashCode. */
    @Test
    public void testRandomOomagesHashCodeSpread() {
        List<Oomage> oomages = new ArrayList<>();
        int N = 10000;

        for (int i = 0; i < N; i += 1) {
            oomages.add(ComplexOomage.randomComplexOomage());
        }

        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(oomages, 10));
    }

    @Test
    public void testHashCode() {
        List<Integer> param1 = new LinkedList<>();
        param1.add(1);
        param1.add(7);
        param1.add(2);
        param1.add(3);
        param1.add(4);
        param1.add(6);
        ComplexOomage so1 = new ComplexOomage(param1);
        List<Integer> param2 = new LinkedList<>();
        param2.add(7);
        param2.add(8);
        param2.add(2);
        param2.add(3);
        param2.add(4);
        param2.add(6);
        ComplexOomage so2 = new ComplexOomage(param2);
        assertEquals(so1.hashCode(), so2.hashCode());
    }

    /*
     * that shows the flaw in the hashCode function.
     */
    @Test
    public void testWithDeadlyParams() {
        List<Oomage> deadlyList = new ArrayList<>();

        for (int i = 0; i < 1000; i += 1) {
            List<Integer> params = new ArrayList<>(8);
            for (int j = 0; j < params.size(); j += 1) {
                if (j < 4) {
                    params.add(StdRandom.uniform(0, 256));
                } else {
                    params.add(j);
                }
            }
            deadlyList.add(new ComplexOomage(params));
        }
        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(deadlyList, 10));
    }
}
