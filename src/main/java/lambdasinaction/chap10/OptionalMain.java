package lambdasinaction.chap10;

import java.util.*;

import static java.util.stream.Collectors.toSet;

public class OptionalMain {

    public static String getCarInsuranceName(Optional<Person> person) {
        return person.flatMap(Person::getCar)
                     .flatMap(Car::getInsurance)
                     .map(Insurance::getName)
                     .orElse("Unknown");
    }

    public static Set<String> getCarInsuranceNames(List<Person> persons) {
        return persons.stream()
                      .map(Person::getCar)
                      .map(optCar -> optCar.flatMap(Car::getInsurance))
                      .map(optInsurance -> optInsurance.map(Insurance::getName))
                      .flatMap(Optional::stream)
                      .collect(toSet());
    }

    public static void main(String[] args) {
        Person person = new Person();
        Car car = new Car();
        Optional<Car> carOptional = Optional.ofNullable(car);
        person.setCar(carOptional);
        System.out.println(getCarInsuranceName(Optional.ofNullable(person)));
    }
}
