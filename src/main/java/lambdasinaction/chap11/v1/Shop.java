package lambdasinaction.chap11.v1;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import static lambdasinaction.chap11.Util.delay;

public class Shop {

    private final String name;
    private final Random random;

    public Shop(String name) {
        this.name = name;
        random = new Random(name.charAt(0) * name.charAt(1) * name.charAt(2));
    }

    /**
     * <b>概要：</b>:
     *      根据产品名称返回价格
     * <b>作者：</b>SUXH</br>
     * <b>日期：</b>2020/3/27 19:18 </br>
     * @param:
     * @return:
     */
    public double getPrice(String product) {
        return calculatePrice(product);
    }

    /**
     * <b>概要：</b>:
     *      计算产品价格
     * <b>作者：</b>SUXH</br>
     * <b>日期：</b>2020/3/27 19:18 </br>
     * @param:
     * @return:
     */
    private double calculatePrice(String product) {
        delay();//模拟其他复杂操作（比如查询供应商及对应折扣）
        int zeroOrOne = new Random().nextInt(2);
        if (zeroOrOne == 1) {
            throw new RuntimeException("product not available");
        }
        return random.nextDouble() * product.charAt(0) + product.charAt(1);
    }

    /**
     * <b>概要：</b>:
     *      异步从其他供应商获取价格信息
     * <b>作者：</b>SUXH</br>
     * <b>日期：</b>2020/3/27 19:38 </br>
     * @param:
     * @return:
     */
    public Future<Double> getPriceAsync(String product) {
        CompletableFuture<Double> futurePrice = new CompletableFuture<>();
        new Thread( () -> {
            try {
                double price = calculatePrice(product);
                //如果价格计算正常结束，完成future操作并设置商品价格
                futurePrice.complete(price);
            } catch (Exception ex) {
                //否则就抛出导致失败的异常，完成这次future操作
                futurePrice.completeExceptionally(ex);
            }
        }).start();
        return futurePrice;
    }

    public String getName() {
        return name;
    }

}
