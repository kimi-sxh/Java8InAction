package lambdasinaction.chap3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

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
public class FunctionDemo {

    public static void main(String[] args) {
        List<Integer> lengths = map(Arrays.asList("kimi","tracy","curry"),String::length);
        System.out.println(lengths);

        //3.6
        List<Integer> strToInteger = map(Arrays.asList("1","2","3"),Integer::parseInt);
        System.out.println(strToInteger);

        Function<Integer,Integer> f = a -> a+1;
        Function<Integer,Integer> g = a -> a*2;
        Function<Integer,Integer> h = f.andThen(g);
        System.out.println(h.apply(1));
    }

    /**
     * <b>概要：</b>:
     *      对list<T>应用函数function获得list<R>
     * <b>作者：</b>SUXH</br>
     * <b>日期：</b>2020/6/3 10:47 </br>
     * @param list 集合数据
     * @param function 函数式接口Function
     * @return:
     */
    public static <T,R> List<R> map(List<T> list, Function<T,R> function) {
        List<R> result = new ArrayList<>();
        for(T each:list) {
            result.add(function.apply(each));
        }
        return result;
    }
}
