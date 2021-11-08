package com.syftgolf.syftgolf;

import com.syftgolf.syftgolf.course.Course;
import com.syftgolf.syftgolf.course.CourseRepository;
import com.syftgolf.syftgolf.course.CourseService;
import com.syftgolf.syftgolf.event.Event;
import com.syftgolf.syftgolf.event.EventRepository;
import com.syftgolf.syftgolf.event.EventService;
import com.syftgolf.syftgolf.user.User;
import com.syftgolf.syftgolf.user.UserRepository;
import com.syftgolf.syftgolf.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class SyftgolfApplication {


	@Autowired
	UserRepository userRepository;

	@Autowired
	EventRepository eventRepository;

	@Autowired
	CourseRepository courseRepository;

	public static void main(String[] args) {
		SpringApplication.run(SyftgolfApplication.class, args);
	}
	@Bean
	@Profile("prod")
	CommandLineRunner run(UserService userService, CourseService courseService, EventService eventService) {
		return (args) -> {

			User inDB = userRepository.findByUsername("dannyjebb");
			User inDB2 = userRepository.findByUsername("mikedobson");

			Course courseDB = courseRepository.findByCourseName("fleetwood");

			Event inDBEvent = eventRepository.findByEventname("test event");

			if(inDB2 == null) {
				User user = new User();
				user.setUsername("mikedobson");
				user.setFirstname("Mike");
				user.setSurname("Dobson");
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




