package pl.jw.currency.exchange.dao;

import javax.sql.DataSource;

import org.jooq.SQLDialect;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan
@Import(ConfigurationDatabase.class)
public class ConfigurationDaoPlugin {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private DefaultConfiguration jooqConfiguration;

	@Bean
	public DefaultConfiguration jooq() {
		DefaultConfiguration defaultConfiguration = new DefaultConfiguration();
		defaultConfiguration.setSQLDialect(SQLDialect.FIREBIRD);
		defaultConfiguration.setDataSource(dataSource);
		return defaultConfiguration;
	}

	@Bean
	public DefaultDSLContext dsl() {
		return new DefaultDSLContext(jooqConfiguration);
	}

}
