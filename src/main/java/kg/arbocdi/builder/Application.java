package kg.arbocdi.builder;

import com.fasterxml.jackson.databind.ObjectMapper;
import kg.arbocdi.builder.cfg.spring.DbCleaner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@SpringBootApplication
@ConfigurationPropertiesScan
@EntityScan(basePackageClasses = {Application.class})
@ComponentScan(basePackageClasses = {Application.class}, excludeFilters = @ComponentScan.Filter(classes = DbCleaner.class, type = FilterType.ASSIGNABLE_TYPE))
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        return new Jackson2ObjectMapperBuilder().failOnEmptyBeans(false).failOnUnknownProperties(false).build();
    }
}
