package lambdasinaction.chap3;

import java.util.function.DoubleFunction;

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
 * <li>2020/2/13[SUXH] 新建</li>
 * </ol>
 */
public class MathDemo {
    public static double integrate(DoubleFunction<Double> f, double a, double b) {
        return (f.apply(a) + f.apply(b)) * (b-a)/2.0;
    }

    public static void main(String[] args) {
        double a = 3.0;
        double b = 7.0;
        double initY = 10.0;
        //DoubleFunction<Double> doubleFunction = x -> x+initY;
        //DoubleFunction<Double> doubleFunction = (double x) -> {return x+initY;};
        DoubleFunction<Double> doubleFunction = MathDemo::add;
        System.out.println(integrate(doubleFunction,a,b));
    }

    public static double add(double x) {
        return x+10;
    }
}
