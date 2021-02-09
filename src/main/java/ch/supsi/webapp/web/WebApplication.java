package ch.supsi.webapp.web;

import ch.supsi.webapp.web.model.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@SpringBootApplication
public class WebApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}

	@Bean
	public CommandLineRunner addUsers(UserRepository userRepository, RoleRepository roleRepository) {
		return (args) -> {
			if(userRepository.count() == 0) {
				BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
				Role adminRole = new Role("ROLE_ADMIN");
				Role userRole = new Role("ROLE_USER");
				roleRepository.save(adminRole);
				roleRepository.save(userRole);

				User user1 = new User("Admin", "Admin", "admin", encoder.encode("admin"));

				user1.setRole(adminRole);

				userRepository.save(user1);
			}
		};
	}

	@Bean
	public CommandLineRunner addCategories(CategoryRepository repository) {
		return (args) -> {
			if(repository.count() == 0) {
				Category category1 = new Category("Vehicles");
				Category category2 = new Category("Sport");
				Category category3 = new Category("Instruments");
				repository.save(category1);
				repository.save(category2);
				repository.save(category3);
			}
		};
	}

	@Bean
	public CommandLineRunner addTypes(TypeRepository repository) {
		return (args) -> {
			if(repository.count() == 0) {
				Type type1 = new Type("Request");
				Type type2 = new Type("Offer");
				repository.save(type1);
				repository.save(type2);
			}
		};
	}

}


