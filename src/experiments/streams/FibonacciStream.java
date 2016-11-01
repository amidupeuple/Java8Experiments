package experiments.streams;

import java.util.function.IntSupplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by dpivovar on 13.10.2016.
 */
public class FibonacciStream {
    public static void main(String[] args) {

        /*Stream.iterate(new int[]{0, 1}, o -> new int[]{o[1], (o[0] + o[1])})
                .limit(20)
                .forEach(o -> System.out.println("(" + o[0] + ", " + o[1] + ")"));*/

        //via generator
        IntSupplier fib = new IntSupplier() {
            private int prev = 0;
            private int curr = 1;

            @Override
            public int getAsInt() {
                int oldPrev = prev;
                int newCur = prev + curr;
                prev = curr;
                curr = newCur;
                return oldPrev;
            }
        };

        IntStream.generate(fib).limit(5).forEach(System.out::println);
    }
}
