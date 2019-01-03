package es.mismocode.movies.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class Movie implements Serializable {
	private static final long serialVersionUID = 6132787662269021538L;
	
	private String url;
	private String imagePath;
	private String title;
	private String originalTitle;
	private int year;
	private List<Country> countries;
	private int minutes;
	private List<String> directors;
	private List<String> screenwriters;
	private List<String> music;
	private List<String> cast;
	private List<String> producers;
	private List<String> genres;
	private String oficialWeb;
	private String synopsis;
	private List<Score> scores;
	private MovieMetaData metaData;
	
	@Override
	public String toString() {
		return "Movie [url=" + url + ", imagePath=" + imagePath + ", title=" + title + ", originalTitle="
				+ originalTitle + ", year=" + year + ", countries=" + countries + ", minutes=" + minutes
				+ ", directors=" + directors + ", screenwriters=" + screenwriters + ", music=" + music + ", cast="
				+ cast + ", producers=" + producers + ", genres=" + genres + ", oficialWeb=" + oficialWeb
				+ ", synopsis=" + synopsis + ", scores=" + scores + ", metaData=" + metaData + "]";
	}

	public String getUrl() {
		return url;
	}

	public String getImagePath() {
		return imagePath;
	}

	public String getTitle() {
		return title;
	}

	public String getOriginalTitle() {
		return originalTitle;
	}

	public int getYear() {
		return year;
	}

	public List<Country> getCountries() {
		return countries;
	}

	public int getMinutes() {
		return minutes;
	}

	public List<String> getDirectors() {
		return directors;
	}

	public List<String> getScreenwriters() {
		return screenwriters;
	}

	public List<String> getMusic() {
		return music;
	}

	public List<String> getCast() {
		return cast;
	}

	public List<String> getProducers() {
		return producers;
	}

	public List<String> getGenres() {
		return genres;
	}

	public String getOficialWeb() {
		return oficialWeb;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public List<Score> getScores() {
		return scores;
	}

	public MovieMetaData getMetaData() {
		return metaData;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setOriginalTitle(String originalTitle) {
		this.originalTitle = originalTitle;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public void setCountries(List<Country> countries) {
		this.countries = countries;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public void setDirectors(List<String> directors) {
		this.directors = directors;
	}

	public void setScreenwriters(List<String> screenwriters) {
		this.screenwriters = screenwriters;
	}

	public void setMusic(List<String> music) {
		this.music = music;
	}

	public void setCast(List<String> cast) {
		this.cast = cast;
	}

	public void setProducers(List<String> producers) {
		this.producers = producers;
	}

	public void setGenres(List<String> genres) {
		this.genres = genres;
	}

	public void setOficialWeb(String oficialWeb) {
		this.oficialWeb = oficialWeb;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public void setScores(List<Score> scores) {
		this.scores = scores;
	}

	public void setMetaData(MovieMetaData metaData) {
		this.metaData = metaData;
	}

	public void saveImages(final String pathIconCountries, final String pathImage) {
		if(pathIconCountries != null && this.getCountries() != null) {
			for(Country country: this.getCountries()) {
				if(country.getIconPath() != null) {
					try(InputStream in = new URL(country.getIconPath()).openStream()){
						Files.copy(in, Paths.get(pathIconCountries + country.getId() + "." + country.getExtension()), StandardCopyOption.REPLACE_EXISTING);
					} catch (final IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		if(pathImage != null && this.getImagePath() != null) {
			try(InputStream in = new URL(this.getImagePath()).openStream()){
				Files.copy(in, Paths.get(pathImage + this.getImagePath().substring(this.getImagePath().lastIndexOf("/"))), StandardCopyOption.REPLACE_EXISTING);
			} catch (final IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
