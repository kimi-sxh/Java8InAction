package lambdasinaction.chap10;

import java.util.*;

public class Car {

    private Optional<Insurance> insurance = Optional.empty();

    public Optional<Insurance> getInsurance() {
        return insurance;
    }
}
