package pl.jw.currency.exchange.dao;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:application-context-dto-${spring.profiles.active}.properties")
public class ConfigurationDatabase {

	@Value(value = "${jdbc.driverClassName}")
	private String jdbcDriverClassName;

	@Value(value = "${jdbc.url}")
	private String jdbcUrl;

	@Value(value = "${jdbc.username}")
	private String jdbcUsername;

	@Value(value = "${jdbc.password}")
	private String jdbcPasswordl;

	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(jdbcDriverClassName);
		dataSource.setUrl(jdbcUrl);
		dataSource.setUsername(jdbcUsername);
		dataSource.setPassword(jdbcPasswordl);
		return dataSource;
	}
}
