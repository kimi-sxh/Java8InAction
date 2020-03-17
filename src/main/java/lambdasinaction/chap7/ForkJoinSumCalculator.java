package lambdasinaction.chap7;

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

import static lambdasinaction.chap7.ParallelStreamsHarness.FORK_JOIN_POOL;

public class ForkJoinSumCalculator extends RecursiveTask<Long> {

    /** 不再将任务分解为子任务的数组大小 */
    public static final long THRESHOLD = 10_000;

    /** 要求和的数组 */
    private final long[] numbers;

    /** 子任务处理数组的起始 */
    private final int start;

    /** 子任务处理数组的终止位置 */
    private final int end;

    /**
     * <b>概要：</b>:
     *      公共构造函数创建主任务
     * <b>作者：</b>SUXH</br>
     * <b>日期：</b>2020/3/16 21:10 </br>
     * @param numbers
     * @return:
     */
    public ForkJoinSumCalculator(long[] numbers) {
        this(numbers, 0, numbers.length);
    }

    private ForkJoinSumCalculator(long[] numbers, int start, int end) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        int length = end - start;
        if (length <= THRESHOLD) {
            return computeSequentially();
        }
        //①创建一个子任务来为数组的前一半求和并异步执行
        ForkJoinSumCalculator leftTask = new ForkJoinSumCalculator(numbers, start, start + length/2);
        leftTask.fork();
        //②创建一个子任务来为数组的后一半求和并同步执行
        ForkJoinSumCalculator rightTask = new ForkJoinSumCalculator(numbers, start + length/2, end);
        Long rightResult = rightTask.compute();
        //③读取第一个任务的结果，如果尚未完成则等待
        Long leftResult = leftTask.join();
        return leftResult + rightResult;
    }

    /**
     * <b>概要：</b>:
     *      在子任务不在可分时计算结果的简单方法（顺序计算）
     * <b>作者：</b>SUXH</br>
     * <b>日期：</b>2020/3/16 21:13 </br>
     * @param:
     * @return:
     */
    private long computeSequentially() {
        long sum = 0;
        for (int i = start; i < end; i++) {
            sum += numbers[i];
        }
        return sum;
    }

    /**
     * <b>概要：</b>:
     *      分支/合并求和
     * <b>作者：</b>SUXH</br>
     * <b>日期：</b>2020/3/16 21:15 </br>
     * @param:
     * @return:
     */
    public static long forkJoinSum(long n) {
        long[] numbers = LongStream.rangeClosed(1, n).toArray();
        ForkJoinTask<Long> task = new ForkJoinSumCalculator(numbers);
        return FORK_JOIN_POOL.invoke(task);
    }
}