package eu.ncdc.springtesting.car;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;


class CarServiceTest {

    private CarService carService;
    private CarRepository carRepositoryMock;

    @BeforeEach
    void setup() {
        carRepositoryMock = Mockito.mock(CarRepository.class);
        carService = new CarService(carRepositoryMock);
    }

    @Test
    void adds_given_car_to_catalog_when_model_does_not_exist() {
        // given
        final Car givenCar = new Car();

        // when
        final Car actualCar = carService.addCar(givenCar);

        // then
        verify(carRepositoryMock).save(givenCar);
    }

    @Test
    void does_not_add_car_to_catalog_when_model_already_exists() {
        // given
        final Car givenCar = new Car();
        givenCar.setModel("TEST_MODEL");

        when(carRepositoryMock.existsByModel("TEST_MODEL")).thenReturn(true);

        // when
        try {
            carService.addCar(givenCar);
        } catch (Exception e) {
            // ignored
        }

        // then
        verify(carRepositoryMock, times(0)).save(any());
    }

    @Test
    void throws_exceptions_when_model_exists() {
        // given
        final Car givenCar = new Car();
        givenCar.setModel("TEST_MODEL");

        when(carRepositoryMock.existsByModel("TEST_MODEL")).thenReturn(true);

        // when
        final Throwable thrown = catchThrowable(() -> carService.addCar(givenCar));

        // then
        assertThat(thrown).isNotNull();
    }

    @Test
    void returns_added_car() {
        // given
        final Car givenCar = new Car();

        when(carRepositoryMock.save(givenCar)).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        final Car actualCar = carService.addCar(givenCar);

        // then
        assertThat(actualCar).isEqualTo(givenCar);
    }


}