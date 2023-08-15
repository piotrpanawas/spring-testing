package eu.ncdc.springtesting.shop;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.ncdc.springtesting.car.Car;
import eu.ncdc.springtesting.car.CarRepository;
import eu.ncdc.springtesting.common.configuration.IntegrationTestConfiguration;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles(IntegrationTestConfiguration.INTEGRATION_TESTS)
public class AddCarIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CarRepository carRepository;

    @Test
    void creates_and_returns_given_car() throws Exception {
        // given
        final Car car = new Car();
        car.setModel("TEST_MODEL");

        final ObjectMapper mapper = new ObjectMapper();
        final String requestAsJson = mapper.writeValueAsString(car);

        // when
        final MvcResult mvcResult = mvc.perform(
                        post("/api/v1/car").contentType(MediaType.APPLICATION_JSON).content(requestAsJson))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        final String responseAsJson = mvcResult.getResponse().getContentAsString();
        final Car actual = mapper.readValue(responseAsJson, Car.class);

        // then
        final SoftAssertions softly = new SoftAssertions();
        softly.assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());
        softly.assertThat(actual.getId()).isNotNull();
        softly.assertThat(actual.getModel()).isEqualTo("TEST_MODEL");
        softly.assertThat(carRepository.findById(actual.getId())).isNotEmpty();
        softly.assertAll();
    }

}
