package experiments.streams.parallel;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

/**
 * Created by dpivovar on 26.10.2016.
 */
public class ForkJoinSumCalculator extends RecursiveTask<Long> {

    private final long[] numbers;
    private final int start;
    private final int end;

    public static final long THRESHOLD = 10_000;

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
        int length = end-start;
        if (length <= THRESHOLD) {
            return computeSequentially();
        }

        ForkJoinSumCalculator leftTask = new ForkJoinSumCalculator(numbers, start, start + length/2);
        leftTask.fork();

        ForkJoinSumCalculator rightTask = new ForkJoinSumCalculator(numbers, start + length/2, end);
        Long rightResult = rightTask.computeSequentially();

        Long leftResult = leftTask.join();

        return leftResult + rightResult;
    }

    private Long computeSequentially() {
        long sum = 0;

        for (int i = start; i < end; i++) {
            sum += numbers[i];
        }
        System.out.println(Thread.currentThread().getName() + ": " + sum);
        return sum;
    }

    public static void main(String[] args) {
        long[] numbers = LongStream.range(1, 1_000_000).toArray();
        ForkJoinSumCalculator task = new ForkJoinSumCalculator(numbers);
        System.out.println("Sum: " + new ForkJoinPool().invoke(task));
    }
}
