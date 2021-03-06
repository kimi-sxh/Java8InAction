package lambdasinaction.chap5;
import lambdasinaction.chap4.Dish;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;
import static lambdasinaction.chap4.Dish.menu;

public class Filtering{

    public static void main(String...args){
        //用 predicate过滤
        //Predicate<Dish> isVegetarian = Dish::isVegetarian;
        Predicate<Dish> isVegetarian = (Dish dish) -> dish.isVegetarian();
        List<Dish> vegetarianMenu = menu.stream().filter(isVegetarian).collect(toList());
        vegetarianMenu.forEach(System.out::println);

        // Filtering unique elements
        List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 3, 2, 4);
        numbers.stream()
               .filter(i -> i % 2 == 0)
               .distinct()
               .forEach(System.out::println);

        // Truncating a stream
        List<Dish> dishesLimit3 =
            menu.stream()
                .filter(d -> d.getCalories() > 300)
                .limit(3)
                .collect(toList());

        dishesLimit3.forEach(System.out::println);
        //dishesLimit3.forEach(dish -> System.out.println(dish));

        // Skipping elements
        List<Dish> dishesSkip2 =
            menu.stream()
                .filter(d -> d.getCalories() > 300)
                .skip(2)
                .collect(toList());

        dishesSkip2.forEach(System.out::println);
    }
}
