package com.syftgolf.syftgolf;

import com.syftgolf.syftgolf.course.Course;
import com.syftgolf.syftgolf.course.CourseRepository;
import com.syftgolf.syftgolf.course.CourseService;
import com.syftgolf.syftgolf.event.Event;
import com.syftgolf.syftgolf.event.EventRepository;
import com.syftgolf.syftgolf.event.EventService;
import com.syftgolf.syftgolf.society.Society;
import com.syftgolf.syftgolf.society.SocietyRepository;
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
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDate;
import java.util.Date;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class SyftgolfApplication {


	@Autowired
	UserRepository userRepository;

	@Autowired
	EventRepository eventRepository;

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	SocietyRepository societyRepository;


	public static void main(String[] args) {
		SpringApplication.run(SyftgolfApplication.class, args);
	}
//	@Bean
//	@Profile("dev")
//	CommandLineRunner run(UserService userService, CourseService courseService, EventService eventService) {
//		return (args) -> {
//
//			User inDB = userRepository.findByUsername("user1");
//			User inDB2 = userRepository.findByUsername("user2");
//			User inDB3 = userRepository.findByUsername("user3");
//			User inDB4 = userRepository.findByUsername("user4");
//			User inDB5 = userRepository.findByUsername("user5");
//			User inDB6 = userRepository.findByUsername("user6");
//			User inDB7 = userRepository.findByUsername("user7");
//			User inDB8 = userRepository.findByUsername("user8");
//			User inDB9 = userRepository.findByUsername("user9");
//			User inDB10 = userRepository.findByUsername("user10");
//
//			Course courseDB = courseRepository.findByCourseName("fleetwood");
//
//			Event inDBEvent = eventRepository.findByEventname("test event");
//
//			Society soc = new Society("test");
//			Society soc2 = new Society("test2");
//			societyRepository.save(soc);
//			societyRepository.save(soc2);
//
//			if(inDB == null) {
//				User user = new User();
//				user.setUsername("user1");
//				user.setFirstname("Mike");
//				user.setSurname("Dobson");
//				user.setCdh("10103530000");
//				user.setEmail("dannyjebb@gmail.com");
//				user.setMobile("07956356879");
//				user.setHandicap(17.8);
//				user.setHomeclub("Stamford");
//				user.setSochcpred(0);
//				user.setPassword("P4ssword");
//				user.setRole("ADMIN");
//				user.setSociety(soc);
//				userService.save(user);
//			}
//
//			if(inDB2 == null) {
//				User user = new User();
//				user.setUsername("user2");
//				user.setFirstname("Danny");
//				user.setSurname("Jebb");
//				user.setCdh("10103530000");
//				user.setEmail("dannyjebb@gmail.com");
//				user.setMobile("07956356879");
//				user.setHandicap(3.9);
//				user.setHomeclub("Stamford");
//				user.setSochcpred(0);
//				user.setPassword("P4ssword");
//				user.setRole("SUPERUSER");
//				user.setSociety(soc);
//				userService.save(user);
//			}
//
//			if(inDB3 == null) {
//				User user = new User();
//				user.setUsername("user3");
//				user.setFirstname("Guest");
//				user.setSurname("User3");
//				user.setCdh("1010101010");
//				user.setEmail("guest@gmail.com");
//				user.setMobile("07777777777");
//				user.setHandicap(3.9);
//				user.setHomeclub("Augusta");
//				user.setSochcpred(0);
//				user.setPassword("P4ssword");
//				user.setRole("ADMIN");
//				user.setSociety(soc2);
//				userService.save(user);
//			}
//
//			if(inDB4 == null) {
//				User user = new User();
//				user.setUsername("user4");
//				user.setFirstname("Guest");
//				user.setSurname("User4");
//				user.setCdh("1010101010");
//				user.setEmail("guest@gmail.com");
//				user.setMobile("07777777777");
//				user.setHandicap(3.9);
//				user.setHomeclub("Augusta");
//				user.setSochcpred(0);
//				user.setPassword("P4ssword");
//				user.setRole("ADMIN");
//				user.setSociety(soc2);
//				userService.save(user);
//			}
//
//			if(inDB5 == null) {
//				User user = new User();
//				user.setUsername("user5");
//				user.setFirstname("Guest");
//				user.setSurname("User5");
//				user.setCdh("1010101010");
//				user.setEmail("guest@gmail.com");
//				user.setMobile("07777777777");
//				user.setHandicap(3.9);
//				user.setHomeclub("Augusta");
//				user.setSochcpred(0);
//				user.setPassword("P4ssword");
//				user.setRole("ADMIN");
//				user.setSociety(soc2);
//				userService.save(user);
//			}
//
//			if(inDB6 == null) {
//				User user = new User();
//				user.setUsername("user6");
//				user.setFirstname("Guest");
//				user.setSurname("User6");
//				user.setCdh("1010101010");
//				user.setEmail("guest@gmail.com");
//				user.setMobile("07777777777");
//				user.setHandicap(3.9);
//				user.setHomeclub("Augusta");
//				user.setSochcpred(0);
//				user.setPassword("P4ssword");
//				user.setRole("ADMIN");
//				user.setSociety(soc2);
//				userService.save(user);
//			}
//
//			if(inDB7 == null) {
//				User user = new User();
//				user.setUsername("user7");
//				user.setFirstname("Guest");
//				user.setSurname("User7");
//				user.setCdh("1010101010");
//				user.setEmail("guest@gmail.com");
//				user.setMobile("07777777777");
//				user.setHandicap(3.9);
//				user.setHomeclub("Augusta");
//				user.setSochcpred(0);
//				user.setPassword("P4ssword");
//				user.setRole("ADMIN");
//				user.setSociety(soc2);
//				userService.save(user);
//			}
//
//			if(inDB8 == null) {
//				User user = new User();
//				user.setUsername("user8");
//				user.setFirstname("Guest");
//				user.setSurname("User8");
//				user.setCdh("1010101010");
//				user.setEmail("guest@gmail.com");
//				user.setMobile("07777777777");
//				user.setHandicap(3.9);
//				user.setHomeclub("Augusta");
//				user.setSochcpred(0);
//				user.setPassword("P4ssword");
//				user.setRole("ADMIN");
//				user.setSociety(soc2);
//				userService.save(user);
//			}
//
//			if(inDB9 == null) {
//				User user = new User();
//				user.setUsername("user9");
//				user.setFirstname("Guest");
//				user.setSurname("User9");
//				user.setCdh("1010101010");
//				user.setEmail("guest@gmail.com");
//				user.setMobile("07777777777");
//				user.setHandicap(3.9);
//				user.setHomeclub("Augusta");
//				user.setSochcpred(0);
//				user.setPassword("P4ssword");
//				user.setRole("ADMIN");
//				user.setSociety(soc2);
//				userService.save(user);
//			}
//
//			if(inDB10 == null) {
//				User user = new User();
//				user.setUsername("user10");
//				user.setFirstname("Guest");
//				user.setSurname("User10");
//				user.setCdh("1010101010");
//				user.setEmail("guest@gmail.com");
//				user.setMobile("07777777777");
//				user.setHandicap(3.9);
//				user.setHomeclub("Augusta");
//				user.setSochcpred(0);
//				user.setPassword("P4ssword");
//				user.setRole("ADMIN");
//				user.setSociety(soc2);
//				userService.save(user);
//			}
//
//
//
//			if(courseDB == null){
//				Course course = new Course();
//				course.setCourseName("fleetwood");
//				course.setCourseRating(70.2);
//				course.setSlopeRating(128);
//				course.setPar(70);
//				course.setPostCode("SK15 3DT");
//				courseService.save(course);
//			}
//
//			Course course2 = new Course();
//			course2.setCourseName("course2");
//			course2.setCourseRating(70.2);
//			course2.setSlopeRating(128);
//			course2.setPar(70);
//			course2.setPostCode("SK15 3DT");
//			courseService.save(course2);
//
//			Course course3 = new Course();
//			course3.setCourseName("fleetwood2");
//			course3.setCourseRating(70.2);
//			course3.setSlopeRating(128);
//			course3.setPar(70);
//			course3.setPostCode("SK15 3DT");
//			courseService.save(course3);
//
//			Course course4 = new Course();
//			course4.setCourseName("fleetwood4");
//			course4.setCourseRating(70.2);
//			course4.setSlopeRating(128);
//			course4.setPar(70);
//			course4.setPostCode("SK15 3DT");
//			courseService.save(course4);
//
//			Course course5 = new Course();
//			course5.setCourseName("fleetwood5");
//			course5.setCourseRating(70.2);
//			course5.setSlopeRating(128);
//			course5.setPar(70);
//			course5.setPostCode("SK15 3DT");
//			courseService.save(course5);
//
//			Course course6 = new Course();
//			course6.setCourseName("fleetwood6");
//			course6.setCourseRating(70.2);
//			course6.setSlopeRating(128);
//			course6.setPar(70);
//			course6.setPostCode("SK15 3DT");
//			courseService.save(course6);
//
//			Course course7 = new Course();
//			course7.setCourseName("fleetwood7");
//			course7.setCourseRating(70.2);
//			course7.setSlopeRating(128);
//			course7.setPar(70);
//			course7.setPostCode("SK15 3DT");
//			courseService.save(course7);
//
//			Course course8 = new Course();
//			course8.setCourseName("fleetwood8");
//			course8.setCourseRating(70.2);
//			course8.setSlopeRating(128);
//			course8.setPar(70);
//			course8.setPostCode("SK15 3DT");
//			courseService.save(course8);
//
//			Course course9 = new Course();
//			course9.setCourseName("fleetwood9");
//			course9.setCourseRating(70.2);
//			course9.setSlopeRating(128);
//			course9.setPar(70);
//			course9.setPostCode("SK15 3DT");
//			courseService.save(course9);
//
//			Course course10 = new Course();
//			course10.setCourseName("fleetwood10");
//			course10.setCourseRating(70.2);
//			course10.setSlopeRating(128);
//			course10.setPar(70);
//			course10.setPostCode("SK15 3DT");
//			courseService.save(course10);
//
//
//
//				Course newCourse = courseRepository.findByCourseName("fleetwood");
//				Event event = new Event();
//				event.setEventname("test event");
//				event.setCost(12.00);
//				event.setCourse(newCourse);
//				event.setDate(12-12-2021);
//				event.setEventtype("singles");
//				event.setInfo("Some info");
//				event.setMaxentrants(12);
//				event.setQualifier(true);
//				eventService.save(event);
//
//			Event event2 = new Event();
//			event2.setEventname("test event2");
//			event2.setCost(12.00);
//			event2.setCourse(newCourse);
//			event2.setDate(LocalDate.ofEpochDay(12-12-2021));
//			event2.setEventtype("singles");
//			event2.setInfo("Some info");
//			event2.setMaxentrants(12);
//			event2.setQualifier(true);
//			eventService.save(event2);
//
//			Event event3 = new Event();
//			event3.setEventname("test event3");
//			event3.setCost(12.00);
//			event3.setCourse(newCourse);
//			event3.setDate(LocalDate.ofEpochDay(12-12-2021));
//			event3.setEventtype("singles");
//			event3.setInfo("Some info");
//			event3.setMaxentrants(12);
//			event3.setQualifier(true);
//			eventService.save(event3);
//
//			Event event4 = new Event();
//			event4.setEventname("test event4");
//			event4.setCost(12.00);
//			event4.setCourse(newCourse);
//			event4.setDate(LocalDate.ofEpochDay(12-12-2021));
//			event4.setEventtype("singles");
//			event4.setInfo("Some info");
//			event4.setMaxentrants(12);
//			event4.setQualifier(true);
//			eventService.save(event4);
//
//			Event event5 = new Event();
//			event5.setEventname("test event5");
//			event5.setCost(12.00);
//			event5.setCourse(newCourse);
//			event5.setDate(LocalDate.ofEpochDay(12-12-2021));
//			event5.setEventtype("singles");
//			event5.setInfo("Some info");
//			event5.setMaxentrants(12);
//			event5.setQualifier(true);
//			eventService.save(event5);
//
//			Event event6 = new Event();
//			event6.setEventname("test event6");
//			event6.setCost(12.00);
//			event6.setCourse(newCourse);
//			event6.setDate(LocalDate.ofEpochDay(12-12-2021));
//			event6.setEventtype("singles");
//			event6.setInfo("Some info");
//			event6.setMaxentrants(12);
//			event6.setQualifier(true);
//			eventService.save(event6);
//
//			Event event7 = new Event();
//			event7.setEventname("test event7");
//			event7.setCost(12.00);
//			event7.setCourse(newCourse);
//			event7.setDate(LocalDate.ofEpochDay(12-12-2021));
//			event7.setEventtype("singles");
//			event7.setInfo("Some info");
//			event7.setMaxentrants(12);
//			event7.setQualifier(true);
//			eventService.save(event7);
//
//			Event event8 = new Event();
//			event8.setEventname("test event8");
//			event8.setCost(12.00);
//			event8.setCourse(newCourse);
//			event8.setDate(LocalDate.ofEpochDay(12-12-2021));
//			event8.setEventtype("singles");
//			event8.setInfo("Some info");
//			event8.setMaxentrants(12);
//			event8.setQualifier(true);
//			eventService.save(event8);
//
//			Event event9 = new Event();
//			event9.setEventname("test event9");
//			event9.setCost(12.00);
//			event9.setCourse(newCourse);
//			event9.setDate(LocalDate.ofEpochDay(12-12-2021));
//			event9.setEventtype("singles");
//			event9.setInfo("Some info");
//			event9.setMaxentrants(12);
//			event9.setQualifier(true);
//			eventService.save(event9);
//
//			Event event10 = new Event();
//			event10.setEventname("test event10");
//			event10.setCost(12.00);
//			event10.setCourse(newCourse);
//			event10.setDate(LocalDate.ofEpochDay(12-12-2021));
//			event10.setEventtype("singles");
//			event10.setInfo("Some info");
//			event10.setMaxentrants(12);
//			event10.setQualifier(true);
//			eventService.save(event10);
//
			//};


		//}
	}




