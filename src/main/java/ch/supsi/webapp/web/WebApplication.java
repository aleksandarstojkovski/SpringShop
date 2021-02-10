package ch.supsi.webapp.web;

import ch.supsi.webapp.web.model.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
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
				User user2 = new User("alesto", "alesto", "alesto", encoder.encode("ciaociao"));

				user1.setRole(adminRole);
				user2.setRole(userRole);

				userRepository.save(user1);
				userRepository.save(user2);
			}
		};
	}

	@Bean
	public CommandLineRunner addCategories(CategoryRepository categoryRepository, SottoCategoryRepository sottoCategoryRepository) {
		return (args) -> {
			if(categoryRepository.count() == 0 && sottoCategoryRepository.count() == 0) {
				Category category1 = new Category("Vehicles");
				Category category2 = new Category("Sport");

				SottoCategory sottoCategory1 = new SottoCategory("Berlina");
				SottoCategory sottoCategory2 = new SottoCategory("Sportiva");
				SottoCategory sottoCategory3 = new SottoCategory("SUV");

				SottoCategory sottoCategory4 = new SottoCategory("Estremi");
				SottoCategory sottoCategory5 = new SottoCategory("Acquatici");
				SottoCategory sottoCategory6 = new SottoCategory("Rilassant");

				category1.setSottoCategorie(new ArrayList<>());
				category1.getSottoCategorie().add(sottoCategory1);
				category1.getSottoCategorie().add(sottoCategory2);
				category1.getSottoCategorie().add(sottoCategory3);

				category2.setSottoCategorie(new ArrayList<>());
				category2.getSottoCategorie().add(sottoCategory4);
				category2.getSottoCategorie().add(sottoCategory5);
				category2.getSottoCategorie().add(sottoCategory6);

				sottoCategory1.setCategory(category1);
				sottoCategory2.setCategory(category1);
				sottoCategory3.setCategory(category1);

				sottoCategory4.setCategory(category2);
				sottoCategory5.setCategory(category2);
				sottoCategory6.setCategory(category2);

				categoryRepository.save(category1);
				categoryRepository.save(category2);

				sottoCategoryRepository.save(sottoCategory1);
				sottoCategoryRepository.save(sottoCategory2);
				sottoCategoryRepository.save(sottoCategory3);
				sottoCategoryRepository.save(sottoCategory4);
				sottoCategoryRepository.save(sottoCategory5);
				sottoCategoryRepository.save(sottoCategory6);


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


