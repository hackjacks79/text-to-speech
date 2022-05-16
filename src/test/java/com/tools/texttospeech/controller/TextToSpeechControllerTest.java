package com.tools.texttospeech.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;

import com.tools.texttospeech.model.VoiceDto;
import com.tools.texttospeech.service.TextToSpeechService;

@RunWith(MockitoJUnitRunner.class)
public class TextToSpeechControllerTest {
	@InjectMocks
	TextToSpeechController textToSpeechController = new TextToSpeechController();
	@Mock
	TextToSpeechService textToSpeechService;

	@Test
	public void getSupportedVoicesTest() throws Exception {
		when(textToSpeechService.getSupportedVoices()).thenReturn(getlistVoiceDto());
		List<VoiceDto> voiceDtoList = textToSpeechService.getSupportedVoices();
		assertEquals(2, voiceDtoList.size());
	}

	@Test
	public void getSupportedVoicesExceptionTest() {
		List<VoiceDto> voiceDtoList = new ArrayList<VoiceDto>();
		try {
			when(textToSpeechService.getSupportedVoices()).thenThrow(new Exception());
			voiceDtoList = textToSpeechService.getSupportedVoices();

		} catch (Exception e) {

			e.printStackTrace();
		}
		assertEquals(0, voiceDtoList.size());
	}

	private List<VoiceDto> getlistVoiceDto() {
		List<VoiceDto> voiceDtoList = new ArrayList<VoiceDto>();
		voiceDtoList.add(new VoiceDto("ar-XA", "ar-XA-Wavenet-A", "FEMALE"));
		voiceDtoList.add(new VoiceDto("ar-XA", "ar-XA-Wavenet-A", "MALE"));
		return voiceDtoList;
	}

	@Test
	public void speakTest() throws IOException {
		textToSpeechController.setText2speechService(new TextToSpeechService());
		ResponseEntity<InputStreamResource> responseEntity = textToSpeechController.speak("en-GB", "en-GB-Wavenet-A",
				"Some Name", 1.2, 3.2);
		assertNotNull(responseEntity.getBody());
	}

	@Test
	public void speakExceptionTest() {
		ResponseEntity<InputStreamResource> response = null;
		try {
			when(textToSpeechService.speak(any())).thenThrow(new IOException());
			response =textToSpeechController.speak("ar-XA-Wavenet-A", "ar-XA", "Some Name", 1.2, 3.2);

		} catch (IOException e) {
			
		}
		assertNull(response);
	}

	@Test
	public void voicesTest() throws IOException {
		textToSpeechController.setText2speechService(new TextToSpeechService());
		List<VoiceDto> voiceList = textToSpeechController.getSupportedVoices();
		assertNotNull(voiceList);
	}

	@Test
	public void voicesExceptionTest() throws IOException {
		List<VoiceDto> voiceList = null;
		try {
			textToSpeechController.setText2speechService(textToSpeechService);
			when(textToSpeechService.getSupportedVoices()).thenThrow(new Exception());
			voiceList = textToSpeechController.getSupportedVoices();
		} catch (Exception e) {
			
		}
		assertNull(voiceList);
	}
	@Test
	public void helthTest() throws IOException {
		assertNotNull(textToSpeechController.healthCheck());
	}
}
