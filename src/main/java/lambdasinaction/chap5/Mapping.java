package lambdasinaction.chap5;

import lambdasinaction.chap4.*;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static lambdasinaction.chap4.Dish.menu;

public class Mapping{

    public static void main(String...args){

        // map
        Stream<String> finalStream = menu.stream()
                                     .map(Dish::getName);
        List<String> dishNames = finalStream.collect(toList());
        System.out.println(dishNames);

        // map
        List<String> words = Arrays.asList("Hello", "World");
        List<Integer> wordLengths = words.stream()
                                         .map(String::length)
                                         .collect(toList());
        System.out.println(wordLengths);

        // flatMap
        words.stream()
                 .flatMap((String line) -> Arrays.stream(line.split("")))
                 .distinct()
                 .forEach(System.out::println);

        // flatMap
        List<Integer> sourceInts = Arrays.asList(1,2,3,4,5);
        List<Integer> resultInts = sourceInts.stream()
                .map(n->n*n)
                .collect(toList());
        System.out.println(resultInts);

        // flatMap
        List<Integer> demo1 = Arrays.asList(1,2,3);
        List<Integer> demo2 = Arrays.asList(4,5);
        Stream<int[]> stream = demo1.stream().flatMap(i->
                demo2.stream().map(j->new int[]{i,j}));
        List<int[]> demo1AndDemo2Result = stream.collect(toList());
        demo1AndDemo2Result.forEach(System.out::println);

        // flatMap

        List<int[]> divide3Result = demo1.stream().flatMap(i->
                demo2.stream()
                        .filter(j->(i+j)%3==0)
                        .map(j->new int[]{i,j})
            ).collect(toList());
        divide3Result.forEach(System.out::println);

        // flatMap
        List<Integer> numbers1 = Arrays.asList(1,2,3,4,5);
        List<Integer> numbers2 = Arrays.asList(6,7,8);
        List<int[]> pairs =
                        numbers1.stream()
                                .flatMap((Integer i) -> numbers2.stream()
                                                       .map((Integer j) -> new int[]{i, j})
                                 )
                                .filter(pair -> (pair[0] + pair[1]) % 3 == 0)
                                .collect(toList());
        pairs.forEach(pair -> System.out.println("(" + pair[0] + ", " + pair[1] + ")"));
    }
}
