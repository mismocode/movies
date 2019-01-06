package es.mismocode.movies.parser;

import es.mismocode.movies.model.Movie;
import es.mismocode.movies.model.MovieLink;

public interface WebParser {

	public MovieLink getURLOfMovie(final String name);
	public Movie getMovie(final String url, Movie moviePrevious);
}
