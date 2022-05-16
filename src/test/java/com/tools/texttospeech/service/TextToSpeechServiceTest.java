package com.tools.texttospeech.service;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.google.cloud.texttospeech.v1.SynthesizeSpeechResponse;
import com.tools.texttospeech.model.TextToSpeechRequest;
import com.tools.texttospeech.model.VoiceDto;

@RunWith(MockitoJUnitRunner.class)
public class TextToSpeechServiceTest {
	TextToSpeechService textToSpeechService = new TextToSpeechService();

	@Test
	public void speakTest() {
		SynthesizeSpeechResponse synthesizeSpeechResponse = null;
		try {
			synthesizeSpeechResponse = textToSpeechService
					.speak(new TextToSpeechRequest("en-GB", "en-GB-Wavenet-A", "hi", 1.5, 2.3));

		} catch (Exception e) {

		}
		assertNotNull(synthesizeSpeechResponse);
	}
	
	
	@Test
	public void getSupportedVoicesTest() {
		List<VoiceDto>  voiceDtoList = null;
		try {
		  voiceDtoList = textToSpeechService.getSupportedVoices();
		} catch (Exception e) {
			
		}
		assertNotNull(voiceDtoList);
	}
	@Test
	public void testDestory() {
		textToSpeechService.destroy();
	}
}
