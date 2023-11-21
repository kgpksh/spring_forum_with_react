package learning.practice.spring_forum_with_react;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringForumWithReactApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringForumWithReactApplication.class, args);
	}

}
