package com.tes.buana;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvEntry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.StandardEnvironment;

import java.util.Map;
import java.util.stream.Collectors;

@SpringBootApplication
public class ECommerceApplication {

	public static void main(String[] args) {
		try {
			Map<String, Object> dotenv = Dotenv.configure().load().entries()
					.stream().collect(Collectors.toMap(DotenvEntry::getKey, DotenvEntry::getValue));
			new SpringApplicationBuilder(ECommerceApplication.class)
					.environment(new StandardEnvironment() {
						@Override
						protected void customizePropertySources(MutablePropertySources propertySources) {
							super.customizePropertySources(propertySources);
							propertySources.addLast(new MapPropertySource("dotenvProperties", dotenv));
						}
					}).run(args);
		} catch(Exception e) {
			System.out.println("no .env file found, using server's environment variable");
			SpringApplication.run(ECommerceApplication.class, args);
		}
	}

}
