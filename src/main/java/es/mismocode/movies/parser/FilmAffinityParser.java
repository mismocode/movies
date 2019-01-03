package es.mismocode.movies.parser;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.similarity.CosineDistance;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import es.mismocode.movies.model.Country;
import es.mismocode.movies.model.Movie;
import es.mismocode.movies.model.Score;
import es.mismocode.movies.model.Web;

public class FilmAffinityParser implements WebParser {
	
	private static final String GOOGLE_BASE_URL_PATH = "http://www.google.com/search?q=";
	private static final String BASE_URL_PATH = "https://www.filmaffinity.com";
	
	private CosineDistance cosineDistance = new CosineDistance();

	@Override
	public String getURLOfMovie(final String name) {
		try {
			if(StringUtils.isNotBlank(name)) {	
				final Elements links = Jsoup.connect(GOOGLE_BASE_URL_PATH + URLEncoder.encode(name + " host:www.filmaffinity.com/es/film", "UTF-8"))
		        		//.header("Accept-Encoding", "gzip, deflate")
		        	    //.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101 Firefox/23.0")
						.userAgent("ExampleBot 1.0 (+http://example.com/bot)")
		        	    //.maxBodySize(0)
		        	    //.timeout(600000)
		        	    .get().select(".g>.r>a");
				if(!links.isEmpty()) {
					Element link = links.first();
					if(link != null && link.hasText()) {
						String googleTitleMovie = link.text();
						if((googleTitleMovie.contains("- FilmAffinity") && cosineDistance.apply(name + "- FilmAffinity", googleTitleMovie) < 0.6) ||
								(!googleTitleMovie.contains("- FilmAffinity") && cosineDistance.apply(name, googleTitleMovie) < 0.7)) {
							String result = link.absUrl("href");
							result = URLDecoder.decode(result.substring(result.indexOf('=') + 1, result.indexOf('&')), "UTF-8");
							if(result.startsWith("http") && result.contains("www.filmaffinity.com/es/film")) {
								return result;
							}
						}
					}
				}
			}
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Movie getMovie(final String url, Movie moviePrevious) {
		try {
			final Document doc = Jsoup.connect(url).get();
			if(doc != null && doc.body() != null) {
				if(moviePrevious != null) {
					Score score = this.addScore(doc);
					if(score != null) {
						moviePrevious.getScores().add(score);
					}
					return moviePrevious;
				} else {
					Movie movie = new Movie();
					movie.setUrl(url);
					movie.setImagePath(this.getImagePath(doc));
					movie.setTitle(this.getTitle(doc));
					movie.setOriginalTitle(this.getOriginalTitle(doc));
					movie.setYear(this.getYear(doc));
					movie.setMinutes(this.getMinutes(doc));
					movie.setCountries(this.getCountries(doc));
					movie.setDirectors(this.getDirectors(doc));
					movie.setScreenwriters(this.getScreenwriters(doc));
					movie.setMusic(this.getMusic(doc));
					movie.setCast(this.getCast(doc));
					movie.setProducers(this.getProducers(doc));
					movie.setOficialWeb(this.getOficialWeb(doc));
					movie.setGenres(this.getGenres(doc));
					movie.setSynopsis(this.getSynopsis(doc));
					List<Score> scores = new ArrayList<Score>();
					Score score = this.addScore(doc);
					if(score != null) {
						scores.add(score);
					}
					movie.setScores(scores);
					return movie;
				}
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
	
	private List<String> getDirectors(final Document doc) {
		if(!doc.select("body dl.movie-info dd.directors").isEmpty()) {
			final Elements directors = doc.select("body dl.movie-info dd.directors span[itemprop=\"name\"]");
			if(!directors.isEmpty()) {
				return directors.eachText();
			}
		}
		return null;
	}
	
	private List<String> getScreenwriters(final Document doc) {
		if(doc.select("body dl.movie-info dt").eachText().contains("Guion")) {
			final Element sub = doc.selectFirst("body dl.movie-info dd div.credits");
			final Elements screenwriters = sub.select("span.nb span");
			if(!screenwriters.isEmpty()) {
				return screenwriters.eachText();
			}
		}
		return null;
	}
	
	private List<String> getMusic(final Document doc) {
		if(doc.select("body dl.movie-info dt").eachText().contains("Música")) {
			final Elements sub = doc.select("body dl.movie-info dd div.credits");
			final Elements music = sub.get(1).select("span.nb span");
			if(!music.isEmpty()) {
				return music.eachText();
			}
		}
		return null;
	}
	
	private List<String> getCast(final Document doc) {
		if(doc.select("body dl.movie-info dt").eachText().contains("Reparto")) {
			final Elements cast = doc.select("body dl.movie-info dd span[itemprop=\"actor\"] a span");
			if(!cast.isEmpty()) {
				return cast.eachText();
			}
		}
		return null;
	}
	
	private List<String> getProducers(final Document doc) {
		if(doc.select("body dl.movie-info dt").eachText().contains("Productora")) {
			final Elements sub = doc.select("body dl.movie-info dd div.credits");
			Elements producers = null;
			if(doc.select("body dl.movie-info dt").eachText().contains("Fotografía")) {
				producers = sub.get(3).select("span.nb span");
			} else {
				producers = sub.get(2).select("span.nb span");
			}
			if(!producers.isEmpty() && producers.hasText()) {
				List<String> result = new ArrayList<String>();
				if(producers.text().contains(" / ")) {
					return Arrays.asList(producers.text().split(" / "));
				} else {
					result.add(producers.text());
				}
				return result;
			}
		}
		return null;
	}
	
	private String getOficialWeb(final Document doc) {
		final Element oficialWeb = doc.selectFirst("body dl.movie-info dd.web-url a");
		if(oficialWeb != null && oficialWeb.hasText()) {
			return oficialWeb.text();
		}
		return null;
	}
	
	private List<String> getGenres(final Document doc) {
		if(doc.select("body dl.movie-info dt").eachText().contains("Género")) {
			final Elements genres = doc.select("body dl.movie-info dd span[itemprop=\"genre\"] a");
			if(!genres.isEmpty()) {
				return genres.eachText();
			}
		}
		return null;
	}
	
	private String getSynopsis(final Document doc) {
		if(doc.select("body dl.movie-info dt").eachText().contains("Sinopsis")) {
			final Element synopsis = doc.selectFirst("body dl.movie-info dd[itemprop=\"description\"]");
			if(synopsis != null && synopsis.hasText()) {
				return synopsis.text().replace(" (FILMAFFINITY)", "");
			}
		}
		return null;
	}
	
	private Score addScore(final Document doc) {
		final Element score = doc.selectFirst("body div#movie-rat-avg");
		if(score != null && score.hasText() && !score.text().contains("-")) {
			return new Score(Double.valueOf(score.text().replace(",", ".")), Web.FILM_AFFINITY);
		}
		return null;
	}
}
