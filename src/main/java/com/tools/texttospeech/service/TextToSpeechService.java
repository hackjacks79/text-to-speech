package com.tools.texttospeech.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.cloud.texttospeech.v1.AudioConfig;
import com.google.cloud.texttospeech.v1.AudioEncoding;
import com.google.cloud.texttospeech.v1.ListVoicesRequest;
import com.google.cloud.texttospeech.v1.ListVoicesResponse;
import com.google.cloud.texttospeech.v1.SynthesisInput;
import com.google.cloud.texttospeech.v1.SynthesizeSpeechResponse;
import com.google.cloud.texttospeech.v1.TextToSpeechClient;
import com.google.cloud.texttospeech.v1.Voice;
import com.google.cloud.texttospeech.v1.VoiceSelectionParams;
import com.google.protobuf.ByteString;
import com.tools.texttospeech.controller.TextToSpeechController;
import com.tools.texttospeech.model.TextToSpeechRequest;
import com.tools.texttospeech.model.VoiceDto;

@Service
public class TextToSpeechService {
	private Logger logger = LoggerFactory.getLogger(TextToSpeechController.class);
	private TextToSpeechClient textToSpeechClient;

	public TextToSpeechService() {
		try {
			this.textToSpeechClient = TextToSpeechClient.create();
		} catch (IOException e) {
			logger.error("init Text2SpeechController", e);
		}
	}

	@PreDestroy
	public void destroy() {
		if (this.textToSpeechClient != null) {
			this.textToSpeechClient.close();
		}
	}

	public SynthesizeSpeechResponse speak(TextToSpeechRequest text2SpeechRequest) throws IOException {

		SynthesisInput input = SynthesisInput.newBuilder().setText(text2SpeechRequest.getText()).build();

		VoiceSelectionParams voiceSelection = VoiceSelectionParams.newBuilder()
				.setLanguageCode(text2SpeechRequest.getLanguage()).setName(text2SpeechRequest.getVoice()).build();

		AudioConfig audioConfig = AudioConfig.newBuilder().setPitch(text2SpeechRequest.getPitch())
				.setSpeakingRate(text2SpeechRequest.getSpeakingRate()).setAudioEncoding(AudioEncoding.MP3).build();

		SynthesizeSpeechResponse response = this.textToSpeechClient.synthesizeSpeech(input, voiceSelection,
				audioConfig);

		return response;

	}

	/**
	 * This method returns supported voices
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<VoiceDto> getSupportedVoices() throws Exception {
		ListVoicesRequest request = ListVoicesRequest.getDefaultInstance();
		ListVoicesResponse listreponse = this.textToSpeechClient.listVoices(request);
		return listreponse.getVoicesList().stream()
				.map(voice -> new VoiceDto(getSupportedLanguage(voice), voice.getName(), voice.getSsmlGender().name()))
				.collect(Collectors.toList());
	}

	/**
	 * This method returns supported language
	 * 
	 * @param voice
	 * @return
	 */
	private static String getSupportedLanguage(Voice voice) {
		List<ByteString> languageCodes = voice.getLanguageCodesList().asByteStringList();
		for (ByteString languageCode : languageCodes) {
			return languageCode.toStringUtf8();
		}
		return null;
	}
}
