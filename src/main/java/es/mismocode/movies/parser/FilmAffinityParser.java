package es.mismocode.movies.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import es.mismocode.movies.model.Country;
import es.mismocode.movies.model.Movie;

public class FilmAffinityParser implements WebParser {
	
	private static final String BASE_URL_PATH = "https://www.filmaffinity.com";
	private static final String SEARCHER_URL_PATH = BASE_URL_PATH + "/es/advsearch2.php?q=";

	@Override
	public List<String> getUrlsOfMovie(final String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Movie getMovie(final String url) {
		try {
			final Document doc = Jsoup.connect(url).get();
			if(doc != null && doc.body() != null) {
				Movie movie = new Movie();
				movie.setUrl(url);
				movie.setImagePath(this.getImagePath(doc));
				movie.setTitle(this.getTitle(doc));
				movie.setOriginalTitle(this.getOriginalTitle(doc));
				movie.setYear(this.getYear(doc));
				movie.setMinutes(this.getMinutes(doc));
				movie.setCountries(this.getCountries(doc));
				// TODO
				return movie;
			}
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private String getImagePath(final Document doc) {
		final Element imagePath = doc.selectFirst("body div#movie-main-image-container a");
		if(imagePath != null && imagePath.hasAttr("href")) {
			return imagePath.attr("href");
		}
		return null;
	}
	
	private String getTitle(final Document doc) {
		final Element title = doc.selectFirst("body h1#main-title span");
		if(title != null && title.hasText()) {
			return title.text();
		}
		return null;
	}
	
	private String getOriginalTitle(final Document doc) {
		final Element originalTitle = doc.selectFirst("body dl.movie-info dd");
		if(originalTitle != null && originalTitle.hasText()) {
			return originalTitle.text();
		}
		return null;
	}
	
	private int getYear(final Document doc) {
		final Element year = doc.selectFirst("body dl.movie-info dd[itemprop=\"datePublished\"]");
		if(year != null && year.hasText()) {
			return Integer.valueOf(year.text());
		}
		return 0;
	}
	
	private int getMinutes(final Document doc) {
		final Element minutes = doc.selectFirst("body dl.movie-info dd[itemprop=\"duration\"]");
		if(minutes != null && minutes.hasText()) {
			return Integer.valueOf(minutes.text().replace(" min.", ""));
		}
		return 0;
	}
	
	private List<Country> getCountries(final Document doc) {
		final Element country = doc.selectFirst("body dl.movie-info dd img");
		if(country != null && country.hasAttr("src") && country.hasAttr("title")) {
			List<Country> countries = new ArrayList<Country>();
			final String sourceImg = country.attr("src");
			countries.add(new Country(sourceImg.substring(sourceImg.indexOf(".jpg") - 2, sourceImg.indexOf(".jpg")), country.attr("title"), BASE_URL_PATH + sourceImg, "jpg"));
			return countries;
		}
		return null;
	}
}
