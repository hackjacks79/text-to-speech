package com.tools.texttospeech.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.cloud.texttospeech.v1.SynthesizeSpeechResponse;
import com.google.common.io.ByteSource;
import com.tools.texttospeech.model.TextToSpeechRequest;
import com.tools.texttospeech.model.VoiceDto;
import com.tools.texttospeech.service.TextToSpeechService;

@RestController
@CrossOrigin
public class TextToSpeechController {
	private Logger logger = LoggerFactory.getLogger(TextToSpeechController.class);
	@Autowired
	private TextToSpeechService text2speechService;

	public TextToSpeechService getText2speechService() {
		return text2speechService;
	}

	public void setText2speechService(TextToSpeechService text2speechService) {
		this.text2speechService = text2speechService;
	}

	@GetMapping("voices")
	public List<VoiceDto> getSupportedVoices() {
		List<VoiceDto> listVoiceDto = null;
		try {
			listVoiceDto = text2speechService.getSupportedVoices();
		} catch (Exception e) {
			logger.error("Failed to reterieve supported voices" + e.getMessage());
		}
		return listVoiceDto;

	}

	@PostMapping("speak")
	public ResponseEntity<InputStreamResource> speak(@RequestParam("language") String language,
			@RequestParam("voice") String voice, @RequestParam("text") String text, @RequestParam("pitch") double pitch,
			@RequestParam("speakingRate") double speakingRate) throws IOException {
		InputStreamResource resource = null;
		SynthesizeSpeechResponse response = null;
		try {
			response = text2speechService.speak(new TextToSpeechRequest(language, voice, text, pitch, speakingRate));

			resource = new InputStreamResource(ByteSource.wrap(response.getAudioContent().toByteArray()).openStream());
		} catch (Exception e) {
			logger.error("Failed to fetch default audia " + e.getMessage());
		}

		return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/octet-stream"))
				.contentLength(response.getAudioContent().toByteArray().length).body(resource);

	}

	@RequestMapping("/_ah/health")
	public ResponseEntity<String> healthCheck() {
		return new ResponseEntity<>("Healthy", HttpStatus.OK);
	}

}
