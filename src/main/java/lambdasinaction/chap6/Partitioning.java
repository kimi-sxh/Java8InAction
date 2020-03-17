package lambdasinaction.chap6;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.Collector;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.*;
import static lambdasinaction.chap6.Dish.menu;

public class Partitioning {

    public static void main(String ... args) {
        //6.4.1节  分区
        System.out.println("Dishes partitioned by vegetarian: " + partitionByVegeterian());
        System.out.println("Vegetarian Dishes by type: " + vegetarianDishesByType());
        System.out.println("Most caloric dishes by vegetarian: " + mostCaloricPartitionedByVegetarian());
    }

    /**
     * <b>概要：</b>:
     *      根据是否为素菜分区
     * <b>作者：</b>SUXH</br>
     * <b>日期：</b>2020/3/15 10:40 </br>
     * @return true-对应素菜列表，false-对应非素菜列表
     */
    private static Map<Boolean, List<Dish>> partitionByVegeterian() {
        return menu.stream().collect(partitioningBy(Dish::isVegetarian));
    }

    /**
     * <b>概要：</b>:
     *  根据是否为素菜分区，才形成二级map
     * <b>作者：</b>SUXH</br>
     * <b>日期：</b>2020/3/15 10:45 </br>
     * @param:
     * @return:
     */
    private static Map<Boolean, Map<Dish.Type, List<Dish>>> vegetarianDishesByType() {
        Function<Dish, Dish.Type> getType = Dish::getType;
        Collector<Dish, ?, Map<Dish.Type, List<Dish>>> dishMapCollector = groupingBy(getType);//按照类型分组
        Predicate<Dish> isVegetarian = Dish::isVegetarian;//是否为素菜
        Collector<Dish, ?, Map<Boolean, Map<Dish.Type, List<Dish>>>> dishMapCollector1 = partitioningBy(isVegetarian, dishMapCollector);
        return menu.stream().collect(dishMapCollector1);
    }

    /**
     * <b>概要：</b>:
     *      找出素菜和非素菜中热量最高的菜
     * <b>作者：</b>SUXH</br>
     * <b>日期：</b>2020/3/15 11:01 </br>
     * @return true-热量最高的菜 false-非素菜中热量最高的菜
     */
    private static Map<Boolean, String> mostCaloricPartitionedByVegetarian() {
        Predicate<Dish> isVegetarian = Dish::isVegetarian;
        ToIntFunction<Dish> getCalories = Dish::getCalories;
        Collector<Dish, ?, Optional<Dish>> dishOptionalCollector = maxBy(comparingInt(getCalories));
        //将结果容器应用最终转换
        Function<Optional<Dish>,String> finisher = (dishOptional)->dishOptional.get().getName()+":"+dishOptional.get().getCalories();
        Collector<Dish, ?, String> dishDishCollector = collectingAndThen(dishOptionalCollector, finisher);
        Collector<Dish, ?, Map<Boolean, String>> dishMapCollector = partitioningBy(isVegetarian, dishDishCollector);
        return menu.stream().collect(dishMapCollector);
    }
}

