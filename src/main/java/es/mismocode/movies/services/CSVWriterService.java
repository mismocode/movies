package es.mismocode.movies.services;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.opencsv.CSVWriter;
import com.opencsv.ICSVWriter;

import es.mismocode.movies.model.Country;
import es.mismocode.movies.model.Movie;
import es.mismocode.movies.model.Movies;

public class CSVWriterService {
	
	private static final String DEVICE_NAME = "My Passport";
	private static final String DEVICE_TYPE = "HDD";
	private static final String LIST_STRING_JOINNER = ", ";
	private static final String DEFAULT_LANGUAGE = "Castellano";
	private static final String DEFAULT_NUMBER_OF_DICS = "1";
	private static final String DEFAULT_IMAGE_TYPE = "1 - Picture stored in catalog";
	private static final String HEADERS = "Checked;ColorTag;MediaLabel;MediaType;Source;Date;Borrower;DateWatched;UserRating;Rating;OriginalTitle;TranslatedTitle;FormattedTitle;Director;Producer;Writer;Composer;Actors;Country;Year;Length;Category;Certification;URL;Description;Comments;FilePath;VideoFormat;VideoBitrate;AudioFormat;AudioBitrate;Resolution;Framerate;Languages;Subtitles;Size;Disks;PictureStatus;NbExtras;audioChannels;categories;Picture\r\n";
	
	private final Movies movies;
	private final String filePath;
	
	public CSVWriterService(final String filePath, final Movies movies) {
		this.filePath = filePath;
		this.movies = movies;
	}
	
	public void write() {
		try {
			List<String[]> records = new ArrayList<String[]>();
			File file = new File(this.filePath);
			FileWriter fileWriter = null;
			if (file.exists()){
				fileWriter = new FileWriter(file, true);
			} else {
				file.createNewFile();
				fileWriter = new FileWriter(file);
				fileWriter.append(HEADERS);
			}
			CSVWriter writer = new CSVWriter(
					fileWriter, 
					';', 
					ICSVWriter.DEFAULT_QUOTE_CHARACTER, 
					ICSVWriter.DEFAULT_ESCAPE_CHARACTER, 
					"\r\n");
			for(Movie movie: movies.getMovies()) {
				records.add(this.parseMovie(movie));
			}
			writer.writeAll(records);
			writer.close();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}
	
	private String[] parseMovie(final Movie movie) {
		LocalDateTime now = LocalDateTime.now();
		return new String[] {
				"True",
				"0",
				DEVICE_NAME,
				DEVICE_TYPE,
				"",
				String.valueOf(now.getYear()) + "-" + String.valueOf(now.getMonthValue()) + "-" + String.valueOf(now.getDayOfMonth()),
				"",
				"",
				"",
				movie.getScores() != null && !movie.getScores().isEmpty() ? String.valueOf(movie.getScores().get(0).getScore()) : "",
				this.parseString(movie.getOriginalTitle()),
				this.parseString(movie.getTitle()),
				this.parseString(movie.getTitle()) + " (" + this.parseString(movie.getOriginalTitle()) + ")",
				this.parseListString(movie.getDirectors()),
				this.parseListString(movie.getProducers()),
				this.parseListString(movie.getScreenwriters()),
				this.parseListString(movie.getMusic()),
				this.parseListString(movie.getCast()),
				this.parseCountries(movie.getCountries()),
				String.valueOf(movie.getYear()),
				String.valueOf(movie.getMinutes()),
				"",
				"",
				this.parseString(movie.getUrl()),
				this.parseString(movie.getSynopsis()),
				"",
				"",
				movie.getMetaData() != null ? this.parseString(movie.getMetaData().getVideoCodec()) : "",
				"",
				movie.getMetaData() != null ? this.parseString(movie.getMetaData().getAudioCodec()) : "",
				movie.getMetaData() != null ? String.valueOf(movie.getMetaData().getAudioSampleRateOriginal()) : "",
				movie.getMetaData() != null ? (String.valueOf(movie.getMetaData().getVideoWidth()) + "x" + String.valueOf(movie.getMetaData().getVideoHeight())) : "",
				"",
				DEFAULT_LANGUAGE,
				"",
				movie.getMetaData() != null ? String.valueOf(movie.getMetaData().getSize()) : "",
				DEFAULT_NUMBER_OF_DICS,
				DEFAULT_IMAGE_TYPE,
				"0",
				movie.getMetaData() != null ? this.parseString(movie.getMetaData().getAudioChannels()) : "",
				this.parseListString(movie.getGenres()),
				movie.getImagePath() != null && !movie.getImagePath().equals("") ? (movie.getImagePath().substring(movie.getImagePath().lastIndexOf("/")).replace("/", "")) : "" 
		};
	}
	
	private String parseString(final String string) {
		if(string != null && !string.equals("")) {
			return string;
		}
		return "";
	}
	
	private String parseListString(final List<String> list) {
		if(list != null && !list.isEmpty()) {
			return StringUtils.join(list, LIST_STRING_JOINNER);
		}
		return "";
	}
	
	private String parseCountries(final List<Country> list) {
		if(list != null && !list.isEmpty()) {
			return StringUtils.join(list.stream().map(country -> country.getName()).collect(Collectors.toList()), LIST_STRING_JOINNER);
		}
		return "";
	}
}
