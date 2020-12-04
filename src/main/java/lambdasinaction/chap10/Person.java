package lambdasinaction.chap10;

import java.util.*;

public class Person {

    private Optional<Car> car = Optional.empty();

    public void setCar(Optional<Car> car) {
        this.car = car;
    }

    public Optional<Car> getCar() {
        return car;
    }
}
