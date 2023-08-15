package eu.ncdc.springtesting.shop;

import eu.ncdc.springtesting.car.Car;
import eu.ncdc.springtesting.common.provider.DateProvider;
import org.springframework.stereotype.Service;

@Service
public class ShopService {

    private final ShopRepository shopRepository;
    private final DateProvider dateProvider;

    public ShopService(ShopRepository shopRepository, DateProvider dateProvider) {
        this.shopRepository = shopRepository;
        this.dateProvider = dateProvider;
    }

    public Car addCarToShop(Car car, Long shopId) {
        final Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new IllegalStateException(String.format("Shop with ID %s not found!", shopId)));

        car.setAvailableFrom(dateProvider.now());
        shop.getCars().add(car);
        shopRepository.save(shop);
        return car;
    }

}
