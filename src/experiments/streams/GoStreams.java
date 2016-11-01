package experiments.streams;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

/**
 * Created by dpivovar on 12.10.2016.
 */
public class GoStreams {
    public static void main(String[] args) {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario","Milan");
        Trader alan = new Trader("Alan","Cambridge");
        Trader brian = new Trader("Brian","Cambridge");

        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );

        //1
        List<Transaction> result = transactions.stream()
                .filter(t -> t.getYear() == 2011)
                .sorted((o1, o2) ->  new Integer(o1.getValue()).compareTo(new Integer(o2.getValue()))).collect(Collectors.toList());
        System.out.println(result);
        System.out.println("\n///////////////////////////////////////\n\n" );

        //2
        List<String> result2 = transactions.stream().map(o -> o.getTrader().getCity()).distinct().collect(Collectors.toList());
        System.out.println(result2);
        System.out.println("\n///////////////////////////////////////\n\n" );

        //3
        List<Trader> result3 = transactions.stream().map(Transaction::getTrader).distinct().filter(o -> o.getCity() == "Cambridge").sorted(comparing(Trader::getName)).collect(Collectors.toList());
        System.out.println(result3);
        System.out.println("\n///////////////////////////////////////\n\n" );

        //4
        String result4 = transactions.stream().map(Transaction::getTrader).distinct().map(Trader::getName).sorted(String::compareTo).reduce((s1, s2) -> s1 + ", " + s2).get();
        System.out.println(result4);
        System.out.println("\n///////////////////////////////////////\n\n" );

        //5
        boolean result5 = transactions.stream().map(Transaction::getTrader).distinct().map(Trader::getCity).anyMatch(c -> c.equals("Milan"));
        System.out.println(result5);
        System.out.println("\n///////////////////////////////////////\n\n" );

        //6
        transactions.stream().filter(t -> t.getTrader().getCity().equals("Cambridge")).map(Transaction::getValue).forEach(System.out::println);
        System.out.println("\n///////////////////////////////////////\n\n" );

        //7
        Optional<Integer> result7 = transactions.stream().map(Transaction::getValue).reduce((i1, i2) -> i1 > i2 ? i1 : i2);
        System.out.println(result7);
        System.out.println("\n///////////////////////////////////////\n\n" );

        //8
        Optional<Transaction> result8 = transactions.stream().reduce((tr1, tr2) -> tr1.getValue() < tr2.getValue() ? tr1 : tr2);
        System.out.println(result8.get());
    }
}
