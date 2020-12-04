package lambdasinaction.chap11;

import java.util.Random;

import static lambdasinaction.chap11.Util.delay;
import static lambdasinaction.chap11.Util.format;

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
    public String getPrice(String product) {
        double price = calculatePrice(product);
        Discount.Code code = Discount.Code.values()[random.nextInt(Discount.Code.values().length)];
        return name + ":" + price + ":" + code;
    }

    public double calculatePrice(String product) {
        delay();
        return format(random.nextDouble() * product.charAt(0) + product.charAt(1));
    }

    public String getName() {
        return name;
    }
}
