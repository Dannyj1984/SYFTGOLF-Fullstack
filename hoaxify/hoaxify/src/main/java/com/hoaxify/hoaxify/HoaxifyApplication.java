package com.hoaxify.hoaxify;

import com.hoaxify.hoaxify.user.User;
import com.hoaxify.hoaxify.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class HoaxifyApplication {

	public static void main(String[] args) {
		SpringApplication.run(HoaxifyApplication.class, args);
	}

	@Bean
	@Profile("!test")
	CommandLineRunner run(UserService userService) {
		return (args) -> {
			IntStream.rangeClosed(1,15)
					.mapToObj(i -> {
						double min = 0.0;
						double max = 34.0;
						User user = new User();
						user.setUsername("user"+i);
						double random = ThreadLocalRandom.current().nextDouble(min, max);
						user.setHandicap(Double.toString(random));
						user.setPassword("P4ssword");
						System.out.println(user.getUsername() +" " + user.getHandicap());
						return user;

					})
					.forEach(userService::save);

		};
	}

}
