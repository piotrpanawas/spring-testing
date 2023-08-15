package eu.ncdc.springtesting.shop;

import eu.ncdc.springtesting.car.Car;
import eu.ncdc.springtesting.common.provider.DateProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShopServiceTest {

    @InjectMocks
    private ShopService shopService;

    @Mock
    private ShopRepository shopRepository;

    @Mock
    private DateProvider dateProvider;

    @Captor
    private ArgumentCaptor<Shop> shopCaptor;

    @Test
    void adds_given_car_to_given_shop() {
        // given
        final Car car = new Car();
        final Shop shop = new Shop();

        when(shopRepository.findById(12L)).thenReturn(Optional.of(shop));

        // when
        shopService.addCarToShop(car, 12L);

        // then
        verify(shopRepository).save(shopCaptor.capture());

        final Shop updatedShop = shopCaptor.getValue();
        final List<Car> updatedShopCars = updatedShop.getCars();
        assertThat(updatedShopCars).contains(car);
    }

    @Test
    void sets_availability_for_car_as_now() {
        // given
        final Car car = new Car();
        final Shop shop = new Shop();

        when(shopRepository.findById(12L)).thenReturn(Optional.of(shop));

        final LocalDate now = LocalDate.now();
        when(dateProvider.now()).thenReturn(now);

        // when
        shopService.addCarToShop(car, 12L);

        // then
        verify(shopRepository).save(shopCaptor.capture());

        final Shop updatedShop = shopCaptor.getValue();
        final List<Car> updatedShopCars = updatedShop.getCars();
        final Car updatedCar = updatedShopCars.stream()
                .filter(shopCar -> shopCar == car)
                .findFirst()
                .orElseThrow(IllegalStateException::new);

        assertThat(updatedCar).extracting(Car::getAvailableFrom).isEqualTo(now);
    }

}