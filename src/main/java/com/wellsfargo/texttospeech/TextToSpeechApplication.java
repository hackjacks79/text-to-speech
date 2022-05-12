package com.wellsfargo.texttospeech;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.google.cloud.texttospeech.v1.TextToSpeechClient;

@SpringBootApplication
public class TextToSpeechApplication {

	@Bean
	public TextToSpeechClient getTextToSpeechClient() {
		TextToSpeechClient textToSpeechClient = null;
		try {
			textToSpeechClient =  TextToSpeechClient.create();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return textToSpeechClient;
	}
	public static void main(String[] args) {
		SpringApplication.run(TextToSpeechApplication.class, args);
	}

}
