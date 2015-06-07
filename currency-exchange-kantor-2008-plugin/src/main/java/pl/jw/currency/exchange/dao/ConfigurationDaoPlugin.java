package pl.jw.currency.exchange.dao;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

@Configuration
@ComponentScan
@Import(ConfigurationDatabase.class)
public class ConfigurationDaoPlugin {

	@Autowired
	private DataSource dataSource;

	@Bean
	public DefaultConfiguration jooq() {
		DefaultConfiguration defaultConfiguration = new DefaultConfiguration();
		defaultConfiguration.setSQLDialect(SQLDialect.FIREBIRD);
		defaultConfiguration.setDataSource(dataSource);
		return defaultConfiguration;
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer getJdbcProperties() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
