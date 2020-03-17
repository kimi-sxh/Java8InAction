package lambdasinaction.chap6;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;
import static lambdasinaction.chap6.Dish.dishTags;
import static lambdasinaction.chap6.Dish.menu;

/**
 * <h3>概要:</h3>
 *  6.3 分组 单一分组 多级分组 子组统计
 * <br>
 * <h3>功能:</h3>
 * <ol>
 * <li>TODO(这里用一句话描述功能点)</li>
 * </ol>
 * <h3>履历:</h3>
 * <ol>
 * <li>2020/2/13[SUXH] 新建</li>
 * </ol>
 */
public class Grouping {

    enum CaloricLevel { DIET, NORMAL, FAT };

    public static void main(String ... args) {
        System.out.println("Dishes grouped by type: " + groupDishesByType());
        System.out.println("Dish names grouped by type: " + groupDishNamesByType());
        System.out.println("Dish tags grouped by type: " + groupDishTagsByType());
        System.out.println("Caloric dishes grouped by type: " + groupCaloricDishesByType());
        System.out.println("Dishes grouped by caloric level: " + groupDishesByCaloricLevel());
        System.out.println("Dishes grouped by type and caloric level: " + groupDishedByTypeAndCaloricLevel());
        System.out.println("Count dishes in groups: " + countDishesInGroups());
        System.out.println("Most caloric dishes by type: " + mostCaloricDishesByType());
        System.out.println("Most caloric dishes by type: " + mostCaloricDishesByTypeWithoutOprionals());
        System.out.println("Sum calories by type: " + sumCaloriesByType());
        System.out.println("Caloric levels by type: " + caloricLevelsByType());
    }




    //----------------------- 6.3 单一分组----------------------
    /**
     * <b>概要：</b>:
     *      根据菜肴类型分组
     * <b>作者：</b>SUXH</br> 
     * <b>日期：</b>2020/3/7 20:13 </br> 
     * @return 分组后的菜肴
     */
    private static Map<Dish.Type, List<Dish>> groupDishesByType() {
        Function<Dish, Dish.Type> getType = Dish::getType;
        Collector<Dish, ?, Map<Dish.Type, List<Dish>>> dishMapCollector = Collectors.groupingBy(getType);
        return menu.stream().collect(dishMapCollector);
    }
    /**
     * <b>概要：</b>:
     *      每个类型下有哪些菜肴
     * <b>作者：</b>SUXH</br>
     * <b>日期：</b>2020/3/7 20:46 </br>
     * @return:
     */
    private static Map<Dish.Type, List<String>> groupDishNamesByType() {
        Collector<Dish, ?, List<String>> mapping = Collectors.mapping(Dish::getName, toList());
        Collector<Dish, ?, Map<Dish.Type, List<String>>> dishMapCollector = Collectors.groupingBy(Dish::getType, mapping);
        return menu.stream().collect(dishMapCollector);
    }
    /**
     * <b>概要：</b>:
     *      根据自定义热量进行分组
     * <b>作者：</b>SUXH</br>
     * <b>日期：</b>2020/3/7 20:14 </br>
     * @return:
     */
    private static Map<CaloricLevel, List<Dish>> groupDishesByCaloricLevel() {
        Function<Dish,CaloricLevel> function = dish -> {
            if (dish.getCalories() <= 400) return CaloricLevel.DIET;//清单
            else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;//正常
            else return CaloricLevel.FAT;//油脂
        };
        Collector<Dish, ?, Map<CaloricLevel, List<Dish>>> dishMapCollector = groupingBy(function);
        return menu.stream().collect(dishMapCollector);
    }
    //----------------------- 6.3 单一分组----------------------




    //----------------------- 6.3.1 多级分组-------------------
    private static Map<Dish.Type, Map<CaloricLevel, List<Dish>>> groupDishedByTypeAndCaloricLevel() {
        //1.先按照热量来分组
        Function<Dish,CaloricLevel> function = (Dish dish) -> {
            if (dish.getCalories() <= 400) return CaloricLevel.DIET;
            else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
            else return CaloricLevel.FAT;
        };
        Collector<Dish, ?, Map<CaloricLevel, List<Dish>>> subMapCollector = groupingBy(function);
        //2.再根据类型分组
        Collector<Dish, ?, Map<Dish.Type, Map<CaloricLevel, List<Dish>>>> dishMapCollector = groupingBy(Dish::getType, subMapCollector);

        return menu.stream().collect(dishMapCollector);
    }
    //----------------------- 6.3.1 多级分组-------------------




    //----------------------- 6.3.2按子组收集数据--------------
    /**
     * <b>概要：</b>:
     *      计算每个菜肴类型个数
     * <b>作者：</b>SUXH</br>
     * <b>日期：</b>2020/3/7 20:31 </br>
     * @return:
     */
    private static Map<Dish.Type, Long> countDishesInGroups() {
        Collector<Dish, ?, Long> counting = Collectors.counting();
        Collector<Dish, ?, Map<Dish.Type, Long>> dishMapCollector = Collectors.groupingBy(Dish::getType, counting);
        return menu.stream().collect(dishMapCollector);
    }
    /**
     * <b>概要：</b>:
     *      计算每个菜肴类型总的热量
     * <b>作者：</b>SUXH</br>
     * <b>日期：</b>2020/3/7 20:36 </br>
     * @return:
     */
    private static Map<Dish.Type, Integer> sumCaloriesByType() {
        Collector<Dish, ?, Integer> dishIntegerCollector = Collectors.summingInt(Dish::getCalories);
        Collector<Dish, ?, Map<Dish.Type, Integer>> dishMapCollector = Collectors.groupingBy(Dish::getType, dishIntegerCollector);
        return menu.stream().collect(dishMapCollector);
    }
    /**
     * <b>概要：</b>:
     *      筛选出每个类型中菜肴热量最大的菜肴
     * <b>作者：</b>SUXH</br>
     * <b>日期：</b>2020/3/7 20:37 </br>
     * @return:
     */
    private static Map<Dish.Type, Dish> mostCaloricDishesByTypeWithoutOprionals() {
        Collector<Dish, ?, Optional<Dish>> dishOptionalCollector = Collectors.maxBy(Comparator.comparingInt(Dish::getCalories));
        Collector<Dish, ?, Dish> dishDishCollector = Collectors.collectingAndThen(dishOptionalCollector, Optional::get);//optional的数据转换为dish
        Collector<Dish, ?, Map<Dish.Type, Dish>> dishMapCollector = Collectors.groupingBy(Dish::getType, dishDishCollector);
        return menu.stream().collect(dishMapCollector);
    }
    //----------------------- 按子组收集数据--------------

    //--------------------6.3.2 高级分组------------------------
    /**
     * <b>概要：</b>:
     *      分组每个类型对应所有菜肴的所有标签
     * <b>作者：</b>SUXH</br>
     * <b>日期：</b>2020/3/7 20:59 </br>
     * @param:
     * @return:
     */
    private static Map<Dish.Type, Set<String>> groupDishTagsByType() {
        //需要使用flatMapping将不同流进行flat为一个流
        Collector<Dish, ?, Set<String>> dishSetCollector = Collectors.flatMapping(dish -> dishTags.get(dish.getName()).stream(), toSet());
        Collector<Dish, ?, Map<Dish.Type, Set<String>>> dishMapCollector = Collectors.groupingBy(Dish::getType, dishSetCollector);
        return menu.stream().collect(dishMapCollector);
    }
    //--------------------6.3.2 高级分组------------------------

    /**
     * <b>概要：</b>:
     *      筛选出每个类型中菜肴热量大于500
     * <b>作者：</b>SUXH</br>
     * <b>日期：</b>2020/3/7 21:01 </br>
     * @return:
     */
    private static Map<Dish.Type, List<Dish>> groupCaloricDishesByType() {
        Predicate<Dish> predicate = dish -> dish.getCalories() > 500;
        Collector<Dish, ?, List<Dish>> filtering = Collectors.filtering(predicate, toList());
        Collector<Dish, ?, Map<Dish.Type, List<Dish>>> dishMapCollector = Collectors.groupingBy(Dish::getType, filtering);
        return menu.stream().collect(dishMapCollector);
    }







    private static Map<Dish.Type, Optional<Dish>> mostCaloricDishesByType() {
        return menu.stream().collect(
                groupingBy(Dish::getType,
                        reducing((Dish d1, Dish d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2)));
    }





    private static Map<Dish.Type, Set<CaloricLevel>> caloricLevelsByType() {
        return menu.stream().collect(
                groupingBy(Dish::getType, mapping(
                        dish -> { if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                        else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                        else return CaloricLevel.FAT; },
                        toSet() )));
    }
}
