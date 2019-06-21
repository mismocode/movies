package es.mismocode.movies.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "movieMetaData")
@XmlAccessorType(XmlAccessType.FIELD)
public class MovieMetaData implements Serializable {
	private static final long serialVersionUID = 6132776662269021538L;

	@XmlElement(name = "videoCodec")
	private String videoCodec;
	
	@XmlElement(name = "videoWidth")
	private int videoWidth;
	
	@XmlElement(name = "videoHeight")
	private int videoHeight;
	
	@XmlElement(name = "audioCodec")
	private String audioCodec;
	
	@XmlElement(name = "audioSampleRate")
	private String audioSampleRate;

	@XmlElement(name = "audioSampleRateOriginal")
	private int audioSampleRateOriginal;
	
	@XmlElement(name = "audioChannels")
	private String audioChannels;
	
	@XmlElement(name = "sizeInMB")
	private Integer size;
	
	public MovieMetaData() {}

	public int getAudioSampleRateOriginal() {
		return audioSampleRateOriginal;
	}

	public void setAudioSampleRateOriginal(int audioSampleRateOriginal) {
		this.audioSampleRateOriginal = audioSampleRateOriginal;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

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
