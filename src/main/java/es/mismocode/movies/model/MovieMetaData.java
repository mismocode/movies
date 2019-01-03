package es.mismocode.movies.model;

import java.io.Serializable;

public class MovieMetaData implements Serializable {
	private static final long serialVersionUID = 6132776662269021538L;

	private String videoCodec;
	private int videoWidth;
	private int videoHeight;
	private String audioCodec;
	private String audioSampleRate;
	private String audioChannels;
	
	public String getVideoCodec() {
		return videoCodec;
	}
	public int getVideoWidth() {
		return videoWidth;
	}
	public int getVideoHeight() {
		return videoHeight;
	}
	public String getAudioCodec() {
		return audioCodec;
	}
	public String getAudioSampleRate() {
		return audioSampleRate;
	}
	public String getAudioChannels() {
		return audioChannels;
	}
	public void setVideoCodec(String videoCodec) {
		this.videoCodec = videoCodec;
	}
	public void setVideoWidth(int videoWidth) {
		this.videoWidth = videoWidth;
	}
	public void setVideoHeight(int videoHeight) {
		this.videoHeight = videoHeight;
	}
	public void setAudioCodec(String audioCodec) {
		this.audioCodec = audioCodec;
	}
	public void setAudioSampleRate(String audioSampleRate) {
		this.audioSampleRate = audioSampleRate;
	}
	public void setAudioChannels(String audioChannels) {
		this.audioChannels = audioChannels;
	}
}
