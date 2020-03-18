package lambdasinaction.chap8;

import java.util.function.Consumer;

/**
 * <b>概要：</b>:
 *      模版设计模式传递行为
 * <b>作者：</b>SUXH</br>
 * <b>日期：</b>2020/3/17 22:13 </br>
 */
public class OnlineBankingLambda {

    public static void main(String[] args) {
        new OnlineBankingLambda().processCustomer(1337, (Customer c) -> System.out.println("Hello!"));
        new OnlineBankingLambda().processCustomer(1338,c-> System.out.println(c.getName()));
    }

    public void processCustomer(int id, Consumer<Customer> makeCustomerHappy){
        Customer c = Database.getCustomerWithId(id);
        makeCustomerHappy.accept(c);
    }

    // dummy Customer class
    static private class Customer {

        public Customer() {
        }

        public Customer(String name) {
            this.name = name;
        }

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
    // dummy Database class
    static private class Database{
        static Customer getCustomerWithId(int id){
            if(id == 1337) {
                return new Customer();
            } else {
                return new Customer("xxx");
            }
        }
    }
}
