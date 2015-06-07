package pl.jw.currencyexchange.agent;

import javax.sql.DataSource;

import org.jooq.SQLDialect;
import org.jooq.impl.DefaultConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan(value = "pl.jw.currency.exchange")
public class Application {

	@Autowired
	private DataSource dataSource;

	@Bean
	public DefaultConfiguration jooq() {
		DefaultConfiguration defaultConfiguration = new DefaultConfiguration();
		defaultConfiguration.setSQLDialect(SQLDialect.FIREBIRD);
		defaultConfiguration.setDataSource(dataSource);
		return defaultConfiguration;
	}
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
