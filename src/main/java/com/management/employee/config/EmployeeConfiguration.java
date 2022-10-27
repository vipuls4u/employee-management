package com.management.employee.config;


import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

/**
 *
 *
 */
@Configuration
@ComponentScan
@EntityScan("com.management.employee")
@EnableJpaRepositories("com.management.employee")
@PropertySource("classpath:db-config.properties")
public class EmployeeConfiguration {

	protected Logger logger;

	public EmployeeConfiguration() {
		logger = Logger.getLogger(getClass().getName());
	}

	/**
	 * Creates an in-memory "rewards" database populated with test data for fast
	 * testing
	 */
	@Bean
	public DataSource dataSource() {
		logger.info("dataSource() invoked");

		// Create an in-memory H2 relational database containing some demo
		DataSource dataSource = (new EmbeddedDatabaseBuilder()).addScript("classpath:testdb/schema.sql")
				.addScript("classpath:testdb/data.sql").build();
		logger.info("dataSource = " + dataSource);

		// Sanity check
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<Map<String, Object>> employees = jdbcTemplate.queryForList("SELECT name FROM T_EMPLOYEE");
		logger.info("System has " + employees.size() + " Employee");

		// Populate with random Salary
		Random rand = new Random();

		for (Map<String, Object> item : employees) {
			String name = (String) item.get("name");
			BigDecimal salary = new BigDecimal(rand.nextInt(10000000) / 100.0).setScale(2, RoundingMode.HALF_UP);
			jdbcTemplate.update("UPDATE T_EMPLOYEE SET salary = ? WHERE name = ?", salary, name);
		}

		// Populate with Email ID
		for (Map<String, Object> item : employees) {
			String name = (String) item.get("name");
			String emailId = name+"@testCompany.com";
			jdbcTemplate.update("UPDATE T_EMPLOYEE SET EMAIL_ID = ? WHERE name = ?", emailId, name);
		}

		return dataSource;

	}

}
