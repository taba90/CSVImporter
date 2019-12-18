package it.geosolutions.csvmanager.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import it.geosolutions.csvmanager.logging.LoggingAspect;

@Configuration
@EnableAspectJAutoProxy
public class LoggingAspectConfiguration {
	
	@Bean
    public LoggingAspect loggingAspect() {
        return new LoggingAspect();
    }

}
