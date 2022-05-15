package com.wellsfargo.texttospeech;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PreDestroy;

import com.google.common.io.ByteSource;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.texttospeech.v1.AudioConfig;
import com.google.cloud.texttospeech.v1.AudioEncoding;
import com.google.cloud.texttospeech.v1.ListVoicesRequest;
import com.google.cloud.texttospeech.v1.ListVoicesResponse;
import com.google.cloud.texttospeech.v1.SynthesisInput;
import com.google.cloud.texttospeech.v1.SynthesizeSpeechResponse;
import com.google.cloud.texttospeech.v1.TextToSpeechClient;
import com.google.cloud.texttospeech.v1.TextToSpeechSettings;
import com.google.cloud.texttospeech.v1.Voice;
import com.google.cloud.texttospeech.v1.VoiceSelectionParams;
import com.google.protobuf.ByteString;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@RestController
@CrossOrigin
public class Text2SpeechController {

	  private TextToSpeechClient textToSpeechClient;

	  public Text2SpeechController(AppConfig appConfig) {
	    try {
	   	      this.textToSpeechClient = TextToSpeechClient.create();
	    }
	    catch (IOException e) {
	      LoggerFactory.getLogger(Text2SpeechController.class)
	          .error("init Text2SpeechController", e);
	    }
	  }

	  @PreDestroy
	  public void destroy() {
	    if (this.textToSpeechClient != null) {
	      this.textToSpeechClient.close();
	    }
	  }

	  @GetMapping("voices")
	  public List<VoiceDto> getSupportedVoices() {
	    ListVoicesRequest request = ListVoicesRequest.getDefaultInstance();
	    ListVoicesResponse listreponse = this.textToSpeechClient.listVoices(request);
	    return listreponse.getVoicesList().stream()
	        .map(voice -> new VoiceDto(getSupportedLanguage(voice), voice.getName(),
	            voice.getSsmlGender().name()))
	        .collect(Collectors.toList());
	  }

	  @PostMapping("speak")
	  public ResponseEntity<InputStreamResource> speak(@RequestParam("language") String language,
														 @RequestParam("voice") String voice, @RequestParam("text") String text,
														 @RequestParam("pitch") double pitch,
														 @RequestParam("speakingRate") double speakingRate) throws IOException {

	    SynthesisInput input = SynthesisInput.newBuilder().setText(text).build();

	    VoiceSelectionParams voiceSelection = VoiceSelectionParams.newBuilder()
	        .setLanguageCode(language).setName(voice).build();

	    AudioConfig audioConfig = AudioConfig.newBuilder().setPitch(pitch)
	        .setSpeakingRate(speakingRate).setAudioEncoding(AudioEncoding.MP3).build();

	    SynthesizeSpeechResponse response = this.textToSpeechClient.synthesizeSpeech(input,
	        voiceSelection, audioConfig);

		  InputStreamResource resource =  new InputStreamResource(ByteSource.wrap(response.getAudioContent().toByteArray()).openStream());
		  return ResponseEntity.ok()
				  .contentType(MediaType.parseMediaType("application/octet-stream"))
				  .contentLength(response.getAudioContent().toByteArray().length)
				  .body(resource);

	  }

	  private static String getSupportedLanguage(Voice voice) {
	    List<ByteString> languageCodes = voice.getLanguageCodesList().asByteStringList();
	    for (ByteString languageCode : languageCodes) {
	      return languageCode.toStringUtf8();
	    }
	    return null;
	  }

	@RequestMapping("/_ah/health")
	public ResponseEntity<String> healthCheck() {
		return new ResponseEntity<>("Healthy", HttpStatus.OK);
	}
	  
}
