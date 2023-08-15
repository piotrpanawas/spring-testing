package eu.ncdc.springtesting.car;

import org.springframework.stereotype.Service;

@Service
public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Car addCar(Car car) {
        final String model = car.getModel();
        boolean isAlreadyAdded = carRepository.existsByModel(model);
        if (isAlreadyAdded) {
            throw new IllegalStateException("This car model already exists!");
        }

        return carRepository.save(car);
    }

}
