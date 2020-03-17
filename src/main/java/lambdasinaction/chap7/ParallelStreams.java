package lambdasinaction.chap7;

import java.util.function.BinaryOperator;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class ParallelStreams {

    /**
     * <b>概要：</b>:
     *      1-n的数字和
     * <b>作者：</b>SUXH</br>
     * <b>日期：</b>2020/3/15 19:09 </br>
     * @param:
     * @return:
     */
    public static long iterativeSum(long n) {
        long result = 0;
        for (long i = 0; i <= n; i++) {
            result += i;
        }
        return result;
    }

    /**
     * <b>概要：</b>:
     *      1-n的数字和
     * <b>作者：</b>SUXH</br>
     * <b>日期：</b>2020/3/15 19:09 </br>
     * @param:
     * @return:
     */
    public static long sequentialSum(long n) {
        BinaryOperator<Long> sum = Long::sum;
        return Stream.iterate(1L, i -> i + 1).limit(n).reduce(sum).get();
    }

    /**
     * <b>概要：</b>:
     *      并行处理1到n的求和
     * <b>作者：</b>SUXH</br>
     * <b>日期：</b>2020/3/15 19:20 </br>
     * @param n 数字
     * @return 1到n的求和
     */
    public static long parallelSum(long n) {
        return Stream.iterate(1L, i -> i + 1).limit(n).parallel().reduce(Long::sum).get();
    }

    /**
     * <b>概要：</b>:
     *      串行处理1到n的求和（rangeClosed）
     * <b>作者：</b>SUXH</br>
     * <b>日期：</b>2020/3/15 19:26 </br>
     * @param:
     * @return:
     */
    public static long rangedSum(long n) {
        return LongStream.rangeClosed(1, n).reduce(Long::sum).getAsLong();
    }

    /**
     * <b>概要：</b>:
     *      并行处理1到n的求和（rangeClosed：没有装箱开箱的开销，会生成数字范围更容易拆分成更小的独立块）
     * <b>作者：</b>SUXH</br>
     * <b>日期：</b>2020/3/15 19:26 </br>
     * @param:
     * @return:
     */
    public static long parallelRangedSum(long n) {
        return LongStream.rangeClosed(1, n).parallel().reduce(Long::sum).getAsLong();
    }

    /**
     * <b>概要：</b>:
     *      串行处理1到n的求和
     * <b>作者：</b>SUXH</br>
     * <b>日期：</b>2020/3/15 19:34 </br>
     * @param:
     * @return:
     */
    public static long sideEffectSum(long n) {
        Accumulator accumulator = new Accumulator();
        LongStream.rangeClosed(1, n).forEach(accumulator::add);
        return accumulator.total;
    }

    /**
     * <b>概要：</b>:
     *      错误并行处理1到n的求和
     * <b>作者：</b>SUXH</br>
     * <b>日期：</b>2020/3/15 19:34 </br>
     * @param:
     * @return:
     */
    public static long sideEffectParallelSum(long n) {
        Accumulator accumulator = new Accumulator();
        LongStream.rangeClosed(1, n).parallel().forEach(accumulator::add);
        return accumulator.total;
    }

    public static class Accumulator {
        private long total = 0;

        public void add(long value) {
            total += value;
        }
    }
}
