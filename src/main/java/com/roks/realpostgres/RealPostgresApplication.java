package com.roks.realpostgres;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class RealPostgresApplication {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public static void main(String[] args) {
		SpringApplication.run(RealPostgresApplication.class, args);
	}

	@GetMapping("/repeat/{str}/{times}")
	public String repeat(@PathVariable("str") String str, @PathVariable("times") int times) {
		final String repeat = jdbcTemplate.queryForList("select repeat('"+str+"', "+times+")", String.class).get(0);
		return String.format("Hello user, repeat of string is, according to DB: %s!", repeat);
	}
}
