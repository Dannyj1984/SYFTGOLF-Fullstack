package com.hoaxify.hoaxify;

import com.hoaxify.hoaxify.user.User;
import com.hoaxify.hoaxify.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.text.DecimalFormat;
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
						user.setEmail("user"+ i + "@gmail.com");
						double random = ThreadLocalRandom.current().nextDouble(min, max);
						DecimalFormat df = new DecimalFormat();
						df.setMaximumFractionDigits(1);
						String hcp = df.format(random);
						user.setHandicap(hcp);
						user.setPassword("P4ssword");
						System.out.println(user.getUsername() +" " + user.getEmail());
						return user;

					})
					.forEach(userService::save);

		};
	}

}
