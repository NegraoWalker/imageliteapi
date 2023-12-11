package com.walker.imageliteapi;

import com.walker.imageliteapi.domain.entity.Image;
import com.walker.imageliteapi.domain.enums.ImageExtension;
import com.walker.imageliteapi.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ImageliteapiApplication {
	@Bean
	public CommandLineRunner commandLineRunner(@Autowired ImageRepository imageRepository){
		return args -> {
			Image image = Image.builder().extension(ImageExtension.JPEG).name("myimage").tags("teste").size(1000L).build();
			imageRepository.save(image);
		};

	}
	public static void main(String[] args) {
		SpringApplication.run(ImageliteapiApplication.class, args);
	}

}
