package eu.ncdc.springtesting.common.provider;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DateProvider {

    public LocalDate now() {
        return LocalDate.now();
    }

}
