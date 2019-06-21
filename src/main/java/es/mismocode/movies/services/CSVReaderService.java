package es.mismocode.movies.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import com.opencsv.CSVReader;

import es.mismocode.movies.model.Country;
import es.mismocode.movies.model.Movie;
import es.mismocode.movies.model.MovieMetaData;
import es.mismocode.movies.model.Movies;
import es.mismocode.movies.model.Score;
import es.mismocode.movies.model.Web;

public class CSVReaderService {
	
	private static final String LIST_STRING_JOINNER = ", ";
	
	private final String filePath;
	
	public CSVReaderService(final String filePath) {
		this.filePath = filePath;
	}
	
	public Movies read() {
		try {
			Movies movies = new Movies();
			CSVReader csvReader = new CSVReader(Files.newBufferedReader(Paths.get(this.filePath)), ';');
			
			String[] nextRecord;
			csvReader.skip(1);
			while ((nextRecord = csvReader.readNext()) != null) {
				Movie movie = this.parseMovie(nextRecord);
				if(movie != null) {
					movies.add(movie);
				}
			}
			csvReader.close();
			return movies;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Movies();
	}
	
	private Movie parseMovie(final String[] line) {
		Movie result = new Movie();
		
		result.setScores(Arrays.asList(new Score[] {this.parseScore(line[9])}));
		result.setOriginalTitle(!line[10].equals("") ? line[10] : null);
		result.setTitle(!line[11].equals("") ? line[11] : null);
		result.setDirectors(this.parseList(line[13]));
		result.setProducers(this.parseList(line[14]));
		result.setScreenwriters(this.parseList(line[15]));
		result.setMusic(this.parseList(line[16]));
		result.setCast(this.parseList(line[17]));
		result.setCountries(this.parseCountries(line[18]));
		result.setYear(!line[19].equals("") ? Integer.valueOf(line[19]) : 0);
		result.setMinutes(!line[20].equals("") ? Integer.valueOf(line[20]) : 0);
		result.setUrl(!line[23].equals("") ? line[23] : null);
		result.setSynopsis(!line[24].equals("") ? line[24] : null);
		result.setMetaData(this.parseMetaData(line));
		result.setImagePath(!line[41].equals("") ? line[41] : null);
		result.setGenres(this.parseList(line[40]));
		
		return result;
	}
	
	private MovieMetaData parseMetaData(final String[] line) {
		if(!line[27].equals("")) {
			MovieMetaData result = new MovieMetaData();
			result.setVideoCodec(!line[27].equals("") ? line[27] : null);
			result.setVideoWidth(!line[31].equals("") ? Integer.valueOf(line[31].split("x")[0]) : 0);
			result.setVideoHeight(!line[31].equals("") ? Integer.valueOf(line[31].split("x")[1]) : 0);
			result.setSize(!line[35].equals("") ? Integer.valueOf(line[35]) : null);
			result.setAudioSampleRateOriginal(!line[30].equals("") ? Integer.valueOf(line[30]) : 0);
			result.setAudioCodec(!line[29].equals("") ? line[29] : null);
			result.setAudioChannels(!line[39].equals("") ? line[39] : null);
			return result;
		}
		return null;
	}
	
	private Score parseScore(final String score) {
		if(!score.equals("")) {
			return new Score(Double.valueOf(score), Web.FILM_AFFINITY);
		}
		return null;
	}
	
	private List<Country> parseCountries(final String country) {
		if(!country.equals("")) {
			return Arrays.asList(new Country[] {new Country(null, country, null, null)});
		}
		return null;
	}
	
	private List<String> parseList(final String list) {
		if(!list.equals("")) {
			if(list.contains(LIST_STRING_JOINNER)) {
				return Arrays.asList(list.split(LIST_STRING_JOINNER));
			} else {
				return Arrays.asList(new String[] {list});
			}
		}
		return null;
	}
}
