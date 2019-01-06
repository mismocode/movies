package es.mismocode.movies;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.lang3.StringUtils;

import es.mismocode.movies.model.DiscardedMovies;
import es.mismocode.movies.model.Movie;
import es.mismocode.movies.model.MovieDiscarded;
import es.mismocode.movies.model.MovieLink;
import es.mismocode.movies.model.MovieReader;
import es.mismocode.movies.model.Movies;
import es.mismocode.movies.parser.FilmAffinityParser;
import es.mismocode.movies.services.FileReader;
import es.mismocode.movies.services.MetaDataService;

public class MainExecute {

	private static Movies movies = new Movies();
	private static DiscardedMovies discardedMovies = new DiscardedMovies();
	
	private static String resourcePath;
	private static String destinePath;
	private static String singleURL;
	private static String moviesPath;
	
	public static void main(String[] args) {
		if(args != null && args.length > 0) {
			for(int i = 0; i < args.length; i++) {
				switch(args[i]) {
					case "-in":
						resourcePath = args[++i];
						break;
					case "-out":
						destinePath = args[++i];
						break;
					case "-s":
						singleURL = args[++i];
						break;
					case "-m":
						moviesPath = args[++i];
						break;
				}
			}
			
			if(StringUtils.isNotBlank(singleURL)) {
				fillSingleMovie();
			}
			
			if(StringUtils.isNotBlank(resourcePath) && StringUtils.isNotBlank(destinePath)) {
				fillPathMovies();
			}
		}
	}
	
	private static void fillSingleMovie() {
		// TODO
	}
	
	private static void fillPathMovies() {
		FilmAffinityParser filmAffinityParser = new FilmAffinityParser();
    	MetaDataService metaDataService = new MetaDataService();
    	FileReader fileReader = new FileReader();
    	
    	createFolders();
    	
    	LocalDateTime now = LocalDateTime.now();
    	System.out.println("INIT");
    	System.out.println("-------------------------------------------------------------");
    	System.out.println("Searching... - " + now.getHour() + ":" + now.getMinute() + ":" + now.getSecond() + "." + now.get(ChronoField.MILLI_OF_SECOND));
    	
		List<MovieReader> movieReaders = fileReader.getMovies(resourcePath);
		
		now = LocalDateTime.now();
    	System.out.println("Search finished! Total of movies: " + movieReaders.size() + " - " + now.getHour() + ":" + now.getMinute() + ":" + now.getSecond() + "." + now.get(ChronoField.MILLI_OF_SECOND));
    	System.out.println("-------------------------------------------------------------");
    	
		for(MovieReader movieReader : movieReaders) {
			if(StringUtils.isNotBlank(movieReader.getFilename()) && StringUtils.isNotBlank(movieReader.getExtension())) {
				
				now = LocalDateTime.now();
				System.out.println("Finding in Google... - " + movieReader.getFilename() + " - " + now.getHour() + ":" + now.getMinute() + ":" + now.getSecond() + "." + now.get(ChronoField.MILLI_OF_SECOND));
				
				MovieLink movieLink = filmAffinityParser.getURLOfMovie(movieReader.getFilename().toLowerCase().replace("." + movieReader.getExtension().toLowerCase(), ""));
				
				if(StringUtils.isNotBlank(movieLink.getLink())) {
					
					now = LocalDateTime.now();
					System.out.println("Founed: " + movieLink.getFilenameFound() + " - Processing... - " + movieLink.getLink() + " - " + now.getHour() + ":" + now.getMinute() + ":" + now.getSecond() + "." + now.get(ChronoField.MILLI_OF_SECOND));
					
					Movie movie = filmAffinityParser.getMovie(movieLink.getLink(), null);
					if(movie != null) {
						
						now = LocalDateTime.now();
						System.out.println("Processed! - " + now.getHour() + ":" + now.getMinute() + ":" + now.getSecond() + "." + now.get(ChronoField.MILLI_OF_SECOND));
						
						System.out.println("Saving images...");
						movie.saveImages(destinePath + "\\icons\\", destinePath + "\\images\\");
						movie.setMetaData(metaDataService.getMetaData(movieReader.getAbsolutePath()));
						movies.add(movie);
						System.out.println("Saved!");
						
					} else {
						if(StringUtils.isNotBlank(movieReader.getAbsolutePath())) {
							discardedMovies.add(new MovieDiscarded(movieReader.getAbsolutePath()));
						}
					}
				} else {
					if(StringUtils.isNotBlank(movieReader.getAbsolutePath())) {
						discardedMovies.add(new MovieDiscarded(movieReader.getAbsolutePath()));
					}
				}
			} else {
				if(StringUtils.isNotBlank(movieReader.getAbsolutePath())) {
					discardedMovies.add(new MovieDiscarded(movieReader.getAbsolutePath()));
				}
			}
			System.out.println("-------------------------------------------------------------");
		}
		
		if(movies.getMovies() != null && !movies.getMovies().isEmpty()) {
			jaxbObjectToXML("\\movies.xml", Movies.class, movies);
		}
		
		if(discardedMovies.getDiscardedMovies() != null && !discardedMovies.getDiscardedMovies().isEmpty()) {
			jaxbObjectToXML("\\discarded.xml", DiscardedMovies.class, discardedMovies);
		}
		
		System.out.println("END");
	}
	
	private static void createFolders() {
		try {
    		Path path = Paths.get(destinePath + "\\icons");
			Files.createDirectories(path);

    		path = Paths.get(destinePath + "\\images");
			Files.createDirectories(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void jaxbObjectToXML(String filename, Class tClass, Object anything) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(tClass);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            jaxbMarshaller.marshal(anything, new File(destinePath + filename));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
