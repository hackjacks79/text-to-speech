package com.tools.texttospeech.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class VoiceDtoTest {
	@InjectMocks
	VoiceDto voiceDto ;
	@Before
	public void setup() {
		voiceDto= new VoiceDto("ar-XA-Wavenet-A","ar-XA","FEMALE");
	}
	
	@Test
	public void testGetters() {
		assertEquals("ar-XA-Wavenet-A", voiceDto.getLanguage());
		assertEquals("ar-XA", voiceDto.getName());
		assertEquals("FEMALE", voiceDto.getGender());
	}
}
