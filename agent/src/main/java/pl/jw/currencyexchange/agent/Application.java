package pl.jw.currencyexchange.agent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan({ "pl.jw.currency.exchange" })
@EnableScheduling
@EnableMongoRepositories
@Import(ConfigurationMongo.class)
public class Application {

	// http://docs.spring.io/spring/docs/current/spring-framework-reference/html/scheduling.html

	// SchedulerFactoryBean
	// @Bean
	// public SchedulingConfigurer threadPoolTaskExecutor() {
	// ThreadPoolTaskExecutor threadPoolTaskExecutor = new
	// ThreadPoolTaskExecutor();
	// threadPoolTaskExecutor.setCorePoolSize(1);
	// threadPoolTaskExecutor.setMaxPoolSize(1);
	// threadPoolTaskExecutor.setQueueCapacity(1);
	//
	// return threadPoolTaskExecutor;
	// }
	//
	// TaskScheduler
	//
	// Trigger PeriodicTrigger

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
