package pl.jw.currencyexchange.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import pl.jw.currencyexchange.model.mongo.ConfigurationMongo;

@SpringBootApplication
@Import(ConfigurationMongo.class)
public class Application {
	// https://spring.io/blog/2015/01/12/spring-and-angular-js-a-secure-single-page-application
	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);
	}

}
