package pl.jw.currencyexchange.agent;

import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.convert.CustomConversions;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

@Configuration
@PropertySource(value = "classpath:application-mongo-${spring.profiles.active}.properties")
public class ConfigurationMongo extends AbstractMongoConfiguration {

	@Value("${mongo.host}")
	private String host;

	@Value("${mongo.port}")
	private Integer port;

	@Value("${mongo.database}")
	private String database;

	@Value("${mongo.username}")
	private String username;

	@Value("${mongo.password}")
	private String password;

	@Override
	protected String getDatabaseName() {
		return database;
	}

	@Override
	public Mongo mongo() throws Exception {
		return new MongoClient(host, port);
	}

	@Override
	protected UserCredentials getUserCredentials() {
		return new UserCredentials(username, password);
	}

	/****************************************************************/

	@Override
	@Bean
	public CustomConversions customConversions() {
		return new CustomConversions(Arrays.asList(new LocalDateToStringConverter(), new StringToLocalDateConverter()));
	}

	public class LocalDateToStringConverter implements Converter<LocalDate, String> {
		@Override
		public String convert(LocalDate localDate) {
			return localDate.toString();
		}
	}

	public class StringToLocalDateConverter implements Converter<String, LocalDate> {
		@Override
		public LocalDate convert(String source) {
			return LocalDate.parse(source);
		}
	}

	/****************************************************************/

	@Bean
	public static PropertySourcesPlaceholderConfigurer getMongoProperties() {
		return new PropertySourcesPlaceholderConfigurer();
	}

}
