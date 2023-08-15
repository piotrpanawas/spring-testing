package eu.ncdc.springtesting.shop;

import eu.ncdc.springtesting.car.Car;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Shop {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany
    private List<Car> cars = new ArrayList<>();

}
