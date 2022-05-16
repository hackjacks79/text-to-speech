package com.tools.texttospeech.model;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TextToSpeechRequestTest {
	@InjectMocks
	TextToSpeechRequest textToSpeechRequest = new TextToSpeechRequest();
	@Before
	public void setUp() {
		textToSpeechRequest.setLanguage("ar-XA-Wavenet-A");
		textToSpeechRequest.setPitch(1.4);
		textToSpeechRequest.setSpeakingRate(3.4);
		textToSpeechRequest.setText("name");
		textToSpeechRequest.setVoice("name");
	}
	@Test
	public void testGetters() {
		assertEquals("ar-XA-Wavenet-A", textToSpeechRequest.getLanguage());
		assertEquals(1.4, textToSpeechRequest.getPitch());
		assertEquals(3.4, textToSpeechRequest.getSpeakingRate());
		assertEquals("name", textToSpeechRequest.getText());
		assertEquals("name", textToSpeechRequest.getVoice());		
	}
}
