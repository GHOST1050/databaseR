package com.mihir.databaseR;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Slf4j
@SpringBootApplication
public class DatabaseRApplication implements CommandLineRunner {

	private final DataSource dataSource;

    public DatabaseRApplication(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static void main(String[] args) {
		SpringApplication.run(DatabaseRApplication.class, args);
	}

	@Override
	public void run(final String... args){
        log.info("Datasource: {}", dataSource.toString());
		final JdbcTemplate restTemplate = new JdbcTemplate(dataSource);
		restTemplate.execute("select 1");

	}

}
