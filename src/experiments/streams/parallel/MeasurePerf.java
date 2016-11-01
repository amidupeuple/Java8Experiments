package experiments.streams.parallel;

import java.text.DecimalFormat;
import java.util.function.Function;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * Created by dpivovar on 24.10.2016.
 */
public class MeasurePerf {

    public static void main(String[] args) {
        /*System.out.println("***************************************");
        System.out.println("Iterative way: " + measureSumPerf(new IterativeSumming(), 1_000_000));
        System.out.println("***************************************");*/

        /*System.out.println("***************************************");
        System.out.println("Stream way: " + measureSumPerf(new StreamSumming(), 1_000_000));
        System.out.println("***************************************");*/

        /*System.out.println("***************************************");
        System.out.println("Parallel way: " + measureSumPerf(new ParallelStreamSumming(), 1_000_000));
        System.out.println("***************************************");*/

        System.out.println("***************************************");
        System.out.println("The best time: " + String.format("%.2fms", (double) measureSumPerf(MeasurePerf::sideEffectParallelSum, 1_000_000) / 1_000_000));
        System.out.println("***************************************");
    }

    public static long rangedSum(long n) {
        return LongStream.rangeClosed(1, n).reduce(0L, Long::sum);
    }

    public static long parallelRangedSum(long n) {
        return LongStream.rangeClosed(1, n).parallel().reduce(0L, Long::sum);
    }


    public static long sideEffectParallelSum(long n) {
        Accumulator acc = new Accumulator();
        LongStream.rangeClosed(1, n).parallel().forEach(acc::add);
        return acc.total;
    }

    static class Accumulator {
        public long total = 0;
        public void add(long value) {
            total += value;
        }
    }

    public static long measureSumPerf(Function<Long, Long> adder, long n) {
        long fastest = Long.MAX_VALUE;


        for (int i = 0; i < 10; i++) {
            long start = System.nanoTime();
            long sum = adder.apply(n);
            long duration = (System.nanoTime() - start);
            //System.out.println("Temp duration: " + String.format("%.2fms", (double) duration/1_000_000));
            System.out.println("Result: " + sum);
            if (duration < fastest) fastest = duration;
        }

        return fastest;
    }

    static class IterativeSumming implements Function<Long, Long> {

        @Override
        public Long apply(Long aLong) {
            long result = 0;

            for (long i = 1; i <= aLong; i++) {
                result += i;
            }


            return result;
        }
    }

    static class StreamSumming implements Function<Long, Long> {

        @Override
        public Long apply(Long aLong) {
            return Stream.iterate(1L, i -> i + 1).limit(aLong).reduce(0L, Long::sum);
        }
    }

    static class ParallelStreamSumming implements Function<Long, Long> {

        @Override
        public Long apply(Long aLong) {
            return Stream.iterate(1L, i -> i + 1).limit(aLong).parallel().reduce(0L, Long::sum);
        }
    }
}
