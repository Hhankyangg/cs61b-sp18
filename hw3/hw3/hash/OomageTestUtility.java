package hw3.hash;

import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        int[] numbers = new int[M];
        for (Oomage o : oomages) {
            int bucketNum = (o.hashCode() & 0x7FFFFFFF) % M;
            numbers[bucketNum] += 1;
        }
        double upper = oomages.size() / 2.5;
        double lower = oomages.size() / 50.0;
        for (int num : numbers) {
            if (Double.compare(num, lower) < 0 || Double.compare(num, upper) > 0) {
                return false;
            }
        }
        return true;
    }
}
