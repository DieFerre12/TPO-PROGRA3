package com.edutec.demoneo4j;

import org.neo4j.cypherdsl.core.renderer.Configuration;
import org.neo4j.cypherdsl.core.renderer.Dialect;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.GraphDatabase;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
@SpringBootApplication
@EnableNeo4jRepositories
public class Demoneo4jApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(Demoneo4jApplication.class, args);
	}

	@Bean
	Configuration cypherDslConfiguration() {
		return Configuration.newConfig().withDialect(Dialect.NEO4J_5).build();
	}

	@Override
	public void run(String... args) throws Exception {
		try (var driver = GraphDatabase.driver("neo4j+s://e77bf700.databases.neo4j.io",
				AuthTokens.basic("neo4j", "nWzVWKyE8J6702ojkmfoKm0SmEK3WUncXh23T2kArU4"))) {
			driver.verifyConnectivity();
			System.out.println("Connection established.");
		}
	}

}
