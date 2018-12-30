package es.mismocode.movies.parser;

import es.mismocode.movies.model.Movie;

public interface WebParser {

	public String getURLOfMovie(final String name);
	public Movie getMovie(final String url, Movie moviePrevious);
}
