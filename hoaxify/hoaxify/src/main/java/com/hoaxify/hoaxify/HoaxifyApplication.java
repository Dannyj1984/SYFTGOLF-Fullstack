package com.hoaxify.hoaxify;

import com.hoaxify.hoaxify.course.Course;
import com.hoaxify.hoaxify.course.CourseRepository;
import com.hoaxify.hoaxify.course.CourseService;
import com.hoaxify.hoaxify.event.Event;
import com.hoaxify.hoaxify.event.EventRepository;
import com.hoaxify.hoaxify.event.EventService;
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
import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class HoaxifyApplication {


	@Autowired
	UserRepository userRepository;

	@Autowired
	EventRepository eventRepository;

	@Autowired
	CourseRepository courseRepository;

	public static void main(String[] args) {
		SpringApplication.run(HoaxifyApplication.class, args);
	}
	@Bean
	@Profile("prod")
	CommandLineRunner run(UserService userService, CourseService courseService, EventService eventService) {
		return (args) -> {

			User inDB = userRepository.findByUsername("dannyjebb");
			User inDB2 = userRepository.findByUsername("mikedobson");

			Course courseDB = courseRepository.findByCourseName("fleetwood");

			Event inDBEvent = eventRepository.findByEventname("test event");

			if(inDB == null) {
				User user = new User();
				user.setUsername("dannyjebb");
				user.setFirstname("Danny");
				user.setSurname("Jebb");
				user.setCdh("10103530000");
				user.setEmail("dannyjebb@gmail.com");
				user.setMobile("07956356879");
				user.setHandicap(4.3);
				user.setHomeclub("Stamford");
				user.setSochcpred(0);
				user.setPassword("P4ssword");
				user.setRole("ADMIN");
				userService.save(user);
			}

			if(inDB2 == null) {
				User user = new User();
				user.setUsername("mikedobson");
				user.setFirstname("Danny");
				user.setSurname("Jebb");
				user.setCdh("10103530000");
				user.setEmail("dannyjebb@gmail.com");
				user.setMobile("07956356879");
				user.setHandicap(4.3);
				user.setHomeclub("Stamford");
				user.setSochcpred(0);
				user.setPassword("P4ssword");
				user.setRole("ADMIN");
				userService.save(user);
			}

			if(courseDB == null){
				Course course = new Course();
				course.setCourseName("fleetwood");
				course.setCourseRating(70.2);
				course.setSlopeRating(128);
				course.setPar(70);
				course.setPostCode("SK15 3DT");
				courseService.save(course);
			}

			if(inDBEvent == null){
				Course newCourse = courseRepository.findByCourseName("fleetwood");
				Event event = new Event();
				event.setEventname("test event");
				event.setCost(12.00);
				event.setCourse(newCourse);
				event.setDate(LocalDate.ofEpochDay(12-12-2021));
				event.setEventtype("singles");
				event.setInfo("Some info");
				event.setMaxentrants(12);
				event.setQualifier(true);
				eventService.save(event);
			}
			};


		}
	}




