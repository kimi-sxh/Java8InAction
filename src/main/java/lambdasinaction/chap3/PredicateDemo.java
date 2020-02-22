package lambdasinaction.chap3;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/**
 * <h3>概要:</h3>
 * TODO(请在此处填写概要)
 * <br>
 * <h3>功能:</h3>
 * <ol>
 * <li>TODO(这里用一句话描述功能点)</li>
 * </ol>
 * <h3>履历:</h3>
 * <ol>
 * <li>2020/2/7[SUXH] 新建</li>
 * </ol>
 */
public class PredicateDemo {

    public static void main(String[] args) {
        List<String> strs = new ArrayList<>();
        strs.add("1");
        strs.add("");
        strs.add("2");

        Predicate<String> predicate = (String s) -> !s.isEmpty();
        List<String> result = filter(strs, predicate);
        System.out.println(result);

        //3.6 list集合中是否包含某个元素
        List<String> fruits = new ArrayList<>();
        fruits.add("apple");
        fruits.add("pineapple");
        fruits.add("strawberry");
        //BiPredicate<String,String> biPredicate = (String each,String choose) -> each.equalsIgnoreCase(choose);
        BiPredicate<String,String> biPredicate = String::equalsIgnoreCase;
        List<String> chooseResult = filter(fruits,biPredicate,"pineapple");
        System.out.println(chooseResult);
    }

    public static <T> List<T> filter(List<T> list, Predicate<T> t) {
        List<T> result = new ArrayList<>();
        for(T each:list) {
            if(t.test(each)) {
                result.add(each);
            }
        }
        return result;
    }

    public static <T,U> List<T> filter(List<T> list, BiPredicate<T,U> biPredicate,U choose) {
        List<T> result = new ArrayList<>();
        for(T each:list) {
            if(biPredicate.test(each,choose)) {
                result.add(each);
            }
        }
        return result;
    }
}
