package experiments.streams.collectors;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.IntStream;

/**
 * Created by dpivovar on 21.10.2016.
 */
public class PrimeNumbersCollector implements Collector<Integer, Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> {
    @Override
    public Supplier<Map<Boolean, List<Integer>>> supplier() {
        return () -> new HashMap<Boolean, List<Integer>>() {{
            put(true, new ArrayList<Integer>());
            put(false, new ArrayList<Integer>());
        }};
    }

    @Override
    public BiConsumer<Map<Boolean, List<Integer>>, Integer> accumulator() {
        return (Map<Boolean, List<Integer>> acc, Integer candidate) -> acc.get(isPrime(acc.get(true), candidate)).add(candidate);
    }

    private Boolean isPrime(List<Integer> integers, Integer candidate) {
        int sqRootOfCandidate = (int) Math.sqrt((double) candidate);
        return integers.stream().filter(i -> (i <= sqRootOfCandidate) && (i != 1)).noneMatch(ii -> candidate % ii == 0);
    }

    @Override
    public BinaryOperator<Map<Boolean, List<Integer>>> combiner() {
        return null;
    }

    @Override
    public Function<Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> finisher() {
        return Function.identity();
    }

    @Override
    public Set<Characteristics> characteristics() {
        Set<Characteristics> characteristicsSet = new HashSet<Characteristics>();
        characteristicsSet.add(Characteristics.IDENTITY_FINISH);
        return Collections.unmodifiableSet(characteristicsSet);
    }

    public static void main(String[] args) {
        IntStream.rangeClosed(1, 8012).boxed().collect(new PrimeNumbersCollector()).forEach((aBoolean, integers) -> {
            if (aBoolean)
                System.out.println("Primes: ");
            else
                System.out.println("None Primes: ");
            System.out.println(integers);
            return;});
    }
}
