import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        List<Integer> list1 = Arrays.asList(3, 4, 5, 6);
        /*final List<Integer> list2 = Arrays.asList(0 , 1);
        final int c = 8;
        System.out.println(list1);
        List<List<Integer>> result = list1.stream().map(i1 -> list2.stream().map(i -> Arrays.asList(i1, i)).filter(l -> ((l.get(0) + l.get(1)) % 3) == 0 ).collect(Collectors.toList())).flatMap(List::stream).collect(Collectors.toList());
        System.out.println(result);*/

        //System.out.println(list1.stream().map(i -> 1).reduce(Integer::sum));

        /*Stream<int[]> pythagoreanTriples = IntStream.rangeClosed(1, 100).boxed()
                .flatMap(a -> IntStream.range(a, 100).filter(b -> Math.sqrt(a*a + b*b) % 1.0 == 0 ).mapToObj(b -> new int[]{a, b, (int)Math.sqrt(a*a + b*b)}));
        pythagoreanTriples.limit(5).forEach(t -> System.out.println(t[0] + ", " + t[1] + ", " + t[2] ));*/

        Stream.generate(Math::random).limit(10).forEach(System.out::println);
    }
}
