package lambdasinaction.chap6;

import java.util.*;
import java.util.function.*;

import static java.util.stream.Collectors.*;
import static lambdasinaction.chap6.Dish.menu;

/**
 * <h3>概要:</h3>
 *  6.2 规约和汇总（计算最大值，最小值，平均值，总和，连接字符串等）
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
public class Summarizing {

    public static void main(String ... args) {
        System.out.println("Nr. of dishes: " + howManyDishes());
        System.out.println("The most caloric dish is: " + findMostCaloricDish());
        System.out.println("The most caloric dish is: " + findMostCaloricDishUsingComparator());
        System.out.println("Total calories in menu: " + calculateTotalCalories());
        System.out.println("Average calories in menu: " + calculateAverageCalories());
        System.out.println("Menu statistics: " + calculateMenuStatistics());
        System.out.println("Short menu: " + getShortMenu());
        System.out.println("Short menu comma separated: " + getShortMenuCommaSeparated());
    }

    //-----------------6.2.1 计算流中最大值和最小值----------------------------
    /**
     * <b>概要：</b>:
     *      计算菜单量：Collectors#counting()
     * <b>作者：</b>SUXH</br>
     * <b>日期：</b>2020/3/7 19:50 </br>
     * @param:
     * @return:
     */
    private static long howManyDishes() {
        return menu.stream().collect(counting());
    }

    /**
     * <b>概要：</b>:
     *      计算最大热量
     * <b>作者：</b>SUXH</br>
     * <b>日期：</b>2020/3/7 19:51 </br>
     * @param:
     * @return:
     */
    private static Dish findMostCaloricDish() {
        return menu.stream().collect(reducing((d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2)).get();
    }

    /**
     * <b>概要：</b>:
     *      使用Comparator计算最大热量
     * <b>作者：</b>SUXH</br>
     * <b>日期：</b>2020/3/7 19:54 </br>
     * @param:
     * @return:
     */
    private static Dish findMostCaloricDishUsingComparator() {
        Comparator<Dish> dishComparator = Comparator.comparingInt(Dish::getCalories);
        Optional<Dish> dishOptional = menu.stream().collect(maxBy(dishComparator));
        return dishOptional.get();
//        Comparator<Dish> dishCaloriesComparator = Comparator.comparingInt(Dish::getCalories);
//        BinaryOperator<Dish> moreCaloricOf = BinaryOperator.maxBy(dishCaloriesComparator);
//        return menu.stream().collect(reducing(moreCaloricOf)).get();
    }
    //-----------------6.2.1 计算流中最大值和最小值----------------------------


    //-------------------6.2.2 汇总 计算总和，平均值--------------------
    private static int calculateTotalCalories() {
        return menu.stream().collect(summingInt(Dish::getCalories));
    }

    private static Double calculateAverageCalories() {
        return menu.stream().collect(averagingInt(Dish::getCalories));
    }

    private static IntSummaryStatistics calculateMenuStatistics() {
        return menu.stream().collect(summarizingInt(Dish::getCalories));
    }
    //-------------------6.2.2 汇总 计算最大值，最小值，平均值--------------------


    //--------------------6.2.3 连接字符串--------------------------
    private static String getShortMenu() {
        return menu.stream().map(Dish::getName).collect(joining());
    }

    private static String getShortMenuCommaSeparated() {
        return menu.stream().map(Dish::getName).collect(joining(", "));
    }
    //--------------------6.2.3 连接字符串--------------------------
}
