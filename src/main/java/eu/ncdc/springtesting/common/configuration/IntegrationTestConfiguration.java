package eu.ncdc.springtesting.common.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import static eu.ncdc.springtesting.common.configuration.IntegrationTestConfiguration.INTEGRATION_TESTS;

@Configuration
@Profile(INTEGRATION_TESTS)
@PropertySource("classpath:application-integrationtest.properties")
public class IntegrationTestConfiguration {

    public static final String INTEGRATION_TESTS = "integration-tests";

}
