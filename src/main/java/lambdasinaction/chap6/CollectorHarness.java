package lambdasinaction.chap6;

import java.util.function.*;

public class CollectorHarness {

    public static void main(String[] args) {
        //6.6.2节 比较收集器性能
        System.out.println("Partitioning done in: " + execute(PartitionPrimeNumbers::partitionPrimes) + " msecs");
        //System.out.println("Partitioning done in: " + execute(PartitionPrimeNumbers::partitionPrimesWithCustomCollector) + " msecs" );
    }

    private static long execute(Consumer<Integer> primePartitioner) {
        long fastest = Long.MAX_VALUE;
        for (int i = 0; i < 10; i++) {//判断100万内质数和合数
            long start = System.nanoTime();
            primePartitioner.accept(1_000_000);
            long duration = (System.nanoTime() - start) / 1_000_000;
            if (duration < fastest) {
                fastest = duration;
            }
            System.out.println("done in " + duration);
        }
        return fastest;
    }
}
