package es.mismocode.movies.parser;

import java.util.List;

import es.mismocode.movies.model.Movie;

public interface WebParser {

	public List<String> getUrlsOfMovie(final String name);
	public Movie getMovie(final String url);
}
