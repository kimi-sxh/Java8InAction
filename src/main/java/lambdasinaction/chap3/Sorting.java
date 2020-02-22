package lambdasinaction.chap3;

import java.util.*;
import java.util.function.Function;

import static java.util.Comparator.comparing;

public class Sorting {

    public static void main(String...args){

        /****************************** 3.7 **********************************/
        //3.7.1 传递代码
        List<Apple> inventory = new ArrayList<>();
        inventory.addAll(Arrays.asList(new Apple(80,"green"), new Apple(155, "green"), new Apple(120, "red")));
        inventory.sort(new AppleComparator());
        System.out.println(inventory);

        // reshuffling things a little
        inventory.set(1, new Apple(30, "green"));
        
        // 2
        // [Apple{color='green', weight=30}, Apple{color='green', weight=80}, Apple{color='green', weight=155}]
        inventory.sort(new Comparator<Apple>() {
            public int compare(Apple a1, Apple a2){
                return a1.getWeight().compareTo(a2.getWeight()); 
        }});
        System.out.println(inventory);

        // reshuffling things a little
        inventory.set(1, new Apple(20, "red"));
        
        // 3
        // [Apple{color='red', weight=20}, Apple{color='green', weight=30}, Apple{color='green', weight=155}]
        inventory.sort((a1, a2) -> a1.getWeight().compareTo(a2.getWeight()));
        System.out.println(inventory);
        
        // reshuffling things a little
        inventory.set(1, new Apple(10, "red"));
        
        // 4
        // [Apple{color='red', weight=10}, Apple{color='red', weight=20}, Apple{color='green', weight=155}]
        Function<Apple,Integer> appleCompareFunction = Apple::getWeight;
        inventory.sort(comparing(appleCompareFunction));
        System.out.println(inventory);

        //******************************* 3.7 ******************************************//

        //******************************* 3.8 ******************************************//
        // 3.8.1 逆序排序
        inventory.sort(comparing(appleCompareFunction).reversed());
        System.out.println(inventory);

        //3.8.2 比较器链
        // reshuffling things a little
        inventory.set(0, new Apple(20, "green"));
        inventory.sort(comparing(appleCompareFunction).reversed().thenComparing(Apple::getColor).reversed());
        System.out.println(inventory);
    }

    public static class Apple {
        private Integer weight = 0;
        private String color = "";

        public Apple(Integer weight, String color){
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

    static class AppleComparator implements Comparator<Apple> {
        public int compare(Apple a1, Apple a2){
            return a1.getWeight().compareTo(a2.getWeight());
        }
    }
}
