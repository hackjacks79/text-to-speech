package com.tools.texttospeech.model;

public class TextToSpeechRequest {
	private String language;
	private String voice;
	private String text;
	private double pitch;
	private double speakingRate;
	
	public TextToSpeechRequest(String language, String voice, String text, double pitch, double speakingRate) {
		super();
		this.language = language;
		this.voice = voice;
		this.text = text;
		this.pitch = pitch;
		this.speakingRate = speakingRate;
	}
	public TextToSpeechRequest() {
		super();
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getVoice() {
		return voice;
	}
	public void setVoice(String voice) {
		this.voice = voice;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public double getPitch() {
		return pitch;
	}
	public void setPitch(double pitch) {
		this.pitch = pitch;
	}
	public double getSpeakingRate() {
		return speakingRate;
	}
	public void setSpeakingRate(double speakingRate) {
		this.speakingRate = speakingRate;
	}
	
	
}
