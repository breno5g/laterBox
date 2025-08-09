package dev.breno5g.laterbox;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LaterBoxApplication {

	public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        dotenv.entries().forEach(
                e -> System.setProperty(e.getKey(), e.getValue())
        );
        SpringApplication.run(LaterBoxApplication.class, args);
	}

}
