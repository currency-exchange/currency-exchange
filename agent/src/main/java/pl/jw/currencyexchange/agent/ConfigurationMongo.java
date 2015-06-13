package pl.jw.currencyexchange.agent;

import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoFactoryBean;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.Mongo;

@Configuration
@PropertySource(value = "classpath:application-mongo-${spring.profiles.active}.properties")
public class ConfigurationMongo {

	@Value("mongo.host")
	private String host;

	@Value("mongo.port")
	private Integer port;

	@Value("mongo.database")
	private String database;

	@Value("mongo.username")
	private String username;

	@Value("mongo.password")
	private String password;

	@Autowired
	private Mongo mongo;

	@Bean
	public MongoFactoryBean mongoFactoryBean() throws UnknownHostException {
		MongoFactoryBean mongo = new MongoFactoryBean();
		mongo.setHost(host);
		mongo.setPort(port);
		return mongo;
	}

	@Bean
	public MongoDbFactory mongoDbFactory() throws Exception {
		UserCredentials userCredentials = new UserCredentials(username, password);
		return new SimpleMongoDbFactory(mongo, database, userCredentials);
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer getJdbcProperties() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
