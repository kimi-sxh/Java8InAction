package lambdasinaction.chap6;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;
import static java.util.stream.Collector.Characteristics.*;

public class ToListCollector<T> implements Collector<T, List<T>, List<T>> {

    /**
     * <b>概要：</b>:
     *      建立新的容器
     * <b>作者：</b>SUXH</br>
     * <b>日期：</b>2020/3/14 16:42 </br>
     * @return 创建空的累加器，供数据收集过程使用
     */
    @Override
    public Supplier<List<T>> supplier() {
        return ArrayList::new;
        //return () -> new ArrayList<T>();
    }

    /**
     * <b>概要：</b>:
     *      将元素添加到结果容器
     * <b>作者：</b>SUXH</br>
     * <b>日期：</b>2020/3/14 16:43 </br>
     * @param:
     * @return:
     */
    @Override
    public BiConsumer<List<T>, T> accumulator() {
        return List::add;
        //return (list, item) -> list.add(item);
    }

    @Override
    public Function<List<T>, List<T>> finisher() {
        return i -> i;
    }

    @Override
    public BinaryOperator<List<T>> combiner() {
        return (list1, list2) -> {
            list1.addAll(list2);
            return list1;
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.unmodifiableSet(EnumSet.of(IDENTITY_FINISH, CONCURRENT));
    }
}
