package dev.breno5g.laterbox;

import org.springframework.boot.SpringApplication;

public class TestLaterBoxApplication {

	public static void main(String[] args) {
		SpringApplication.from(LaterBoxApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
