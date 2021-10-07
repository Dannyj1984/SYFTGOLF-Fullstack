package com.hoaxify.hoaxify;

import com.hoaxify.hoaxify.user.User;
import com.hoaxify.hoaxify.user.UserRepository;
import com.hoaxify.hoaxify.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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


	@Autowired
	UserRepository userRepository;
	public static void main(String[] args) {
		SpringApplication.run(HoaxifyApplication.class, args);
	}
	@Bean
	@Profile("dev")
	CommandLineRunner run(UserService userService) {
		return (args) -> {
			User inDB = userRepository.findByUsername("dannyjebb");
			if(inDB == null) {
				User user = new User();
				user.setUsername("dannyjebb");
				user.setFirstname("Danny");
				user.setSurname("Jebb");
				user.setCdh("10103530000");
				user.setEmail("dannyjebb@gmail.com");
				user.setMobile("07956356879");
				user.setHandicap("4.3");
				user.setHomeclub("Stamford");
				user.setPassword("P4ssword");
				user.setAuthority("ADMIN");
				userService.save(user);
			}
			};
		}
	}




