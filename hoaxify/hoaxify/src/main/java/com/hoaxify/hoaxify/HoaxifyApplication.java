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
//	@Bean
//	@Profile("dev")
//	CommandLineRunner run(UserService userService) {
//		return (args) -> {
//			IntStream.rangeClosed(1,15)
//					.mapToObj(i -> {
//						User user = new User();
//						user.setUsername("User " + i);
//						user.setFirstname("Guest");
//						user.setSurname("User " + i);
//						user.setCdh("1010101010");
//						user.setEmail("user" + i + "@gmail.com");
//						user.setMobile("07777777777");
//						user.setHandicap("3.0");
//						user.setHomeClub("Stamford");
//						user.setPassword("P4ssword");
//						return user;
//					})
//					.forEach(userService::save);
//				 };
//		}
	}




