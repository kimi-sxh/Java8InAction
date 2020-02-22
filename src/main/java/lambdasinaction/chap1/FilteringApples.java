package lambdasinaction.chap1;

import lambdasinaction.chap3.FunctionDemo;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class FilteringApples{

    public static void main(String ... args){

        List<Apple> inventory = Arrays.asList(new Apple(80,"green"),
                                              new Apple(155, "green"),
                                              new Apple(120, "red"));	

        //-----------------------------------   3.6.1 ---------------------------------------------------------//
        //筛选绿色苹果（参见3.6.1） 指向静态方法的方法应用
        Predicate<Apple> isGreenPredicate = FilteringApples::isGreenApple;
        List<Apple> greenApples = filterApples(inventory, isGreenPredicate);
        System.out.println(greenApples);
        Predicate<Apple> isHeavyPredicate = FilteringApples::isHeavyApple;
        List<Apple> heavyApples = filterApples(inventory, isHeavyPredicate);
        System.out.println(heavyApples);
        //等价上面
        List<Apple> greenApples2 = filterApples(inventory, (Apple a) -> "green".equals(a.getColor()));
        System.out.println(greenApples2);
        List<Apple> heavyApples2 = filterApples(inventory, (Apple a) -> a.getWeight() > 150);
        System.out.println(heavyApples2);
        //-------------------------------------- 3.6.1 ------------------------------------------------------//
        
        // []
        List<Apple> weirdApples = filterApples(inventory, (Apple a) -> a.getWeight() < 80 || 
                                                                       "brown".equals(a.getColor()));
        System.out.println(weirdApples);

        //-------------------------------------- 3.6.2 ------------------------------------------------------//
        Supplier<Apple> supplier = Apple::new;
        System.out.println(supplier.get());

        Function<Integer,Apple> function = Apple::new;
        System.out.println(function.apply(160));

        List<Integer> weight = Arrays.asList(7,3,4,10);
        Function<Integer, Apple> functionsAppleList = Apple::new;
        System.out.println(FunctionDemo.map(weight,functionsAppleList));

        BiFunction<Integer,String,Apple> biFunction = Apple::new;
        System.out.println(biFunction.apply(14,"green"));
        //-------------------------------------- 3.6.2 ------------------------------------------------------//
    }

    public static List<Apple> filterGreenApples(List<Apple> inventory){
        List<Apple> result = new ArrayList<>();
        for (Apple apple: inventory){
            if ("green".equals(apple.getColor())) {
                result.add(apple);
            }
        }
        return result;
    }

    public static List<Apple> filterHeavyApples(List<Apple> inventory){
        List<Apple> result = new ArrayList<>();
        for (Apple apple: inventory){
            if (apple.getWeight() > 150) {
                result.add(apple);
            }
        }
        return result;
    }

    /**
     * <b>概要：</b>:
     *      是否为绿色苹果
     * <b>作者：</b>SUXH</br>
     * <b>日期：</b>2020/2/13 10:42 </br>
     * @param apple apple对象
     * @return true: 绿色苹果 false:非绿色苹果
     */
    public static boolean isGreenApple(Apple apple) {
        return "green".equals(apple.getColor()); 
    }

    /**
     * <b>概要：</b>:
     *      是否是个重苹果
     * <b>作者：</b>SUXH</br>
     * <b>日期：</b>2020/2/13 10:43 </br>
     * @param apple apple对象
     * @return true: 重苹果 false:轻苹果
     */
    public static boolean isHeavyApple(Apple apple) {
        return apple.getWeight() > 150;
    }

    public static List<Apple> filterApples(List<Apple> inventory, Predicate<Apple> p){
        List<Apple> result = new ArrayList<>();
        for(Apple apple : inventory){
            if(p.test(apple)){
                result.add(apple);
            }
        }
        return result;
    }       

    public static class Apple {
        private int weight = 0;
        private String color = "";

        public Apple() {
        }

        public Apple(int weight) {
            this.weight = weight;
        }

        public Apple(int weight, String color){
            this.weight = weight;
            this.color = color;
        }

        public Integer getWeight() {
            return weight;
        }

        public void setWeight(Integer weight) {
            this.weight = weight;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String toString() {
            return "Apple{" +
                   "color='" + color + '\'' +
                   ", weight=" + weight +
                   '}';
        }
    }

}
