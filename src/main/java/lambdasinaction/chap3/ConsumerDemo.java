package lambdasinaction.chap3;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

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
public class ConsumerDemo {

    public static void main(String[] args) {

        printEach(Arrays.asList(1,2,3,4,5),(Integer i) -> System.out.println(i));
    }

    public static <T> void printEach(List<T> list, Consumer<T> consumer) {
        for(T each:list) {
            consumer.accept(each);
        }
    }
}
