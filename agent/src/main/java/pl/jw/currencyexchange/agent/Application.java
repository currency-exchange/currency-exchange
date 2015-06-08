package pl.jw.currencyexchange.agent;

import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoFactoryBean;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.mongodb.Mongo;

@SpringBootApplication
@ComponentScan({ "pl.jw.currency.exchange", "pl.jw.currencyexchange.agent" })
@EnableScheduling
@EnableMongoRepositories
public class Application {

	@Autowired
	private Mongo mongo;

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

	@Bean
	public MongoFactoryBean mongoFactoryBean() throws UnknownHostException {
		MongoFactoryBean mongo = new MongoFactoryBean();
		mongo.setHost("ds039321.mongolab.com");
		mongo.setPort(39321);
		return mongo;
	}

	@Bean
	public MongoDbFactory mongoDbFactory() throws Exception {
		UserCredentials userCredentials = new UserCredentials("sulechów",
				"sul-9099");
		return new SimpleMongoDbFactory(mongo, "vabank", userCredentials);
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
