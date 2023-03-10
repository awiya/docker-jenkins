package com.idihia;

import com.idihia.entity.ThemeParkRide;
import com.idihia.repository.ThemeParkRideRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner start(ThemeParkRideRepository repository){
		return args -> {
			repository.saveAll(
					List.of(
							ThemeParkRide.builder().name("Rollercoaster").description("Train ride that speeeds yo along").thrillFactor(3).vomitFactor(5).build(),
							ThemeParkRide.builder().name("Log flume").description("Boat ride that plenty of splahs").thrillFactor(4).vomitFactor(2).build(),
							ThemeParkRide.builder().name("teacups").description("Spinning ride in a giant tea-cup").thrillFactor(5).vomitFactor(6).build()
					)
			);
		};
	}

}
