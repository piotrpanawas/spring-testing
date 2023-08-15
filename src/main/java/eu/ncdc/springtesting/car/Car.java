package eu.ncdc.springtesting.car;

import eu.ncdc.springtesting.shop.Shop;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Car {

    @Id
    @GeneratedValue
    private Long id;

    private String model;

    @ManyToOne
    @JoinColumn
    private Shop shop;

    private LocalDate availableFrom;

}
