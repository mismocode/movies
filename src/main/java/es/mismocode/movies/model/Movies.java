package es.mismocode.movies.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "movies")
@XmlAccessorType(XmlAccessType.FIELD)
public class Movies implements Serializable {
	private static final long serialVersionUID = 6132766662269021538L;
	
	@XmlElement(name = "movie")
	private List<Movie> movies;
	
	public Movies() {}

	public List<Movie> getMovies() {
		return movies;
	}
	
	public void setMovies(List<Movie> movies) {
		this.movies = movies;
	}

	public void add(Movie movie) {
		if(this.movies == null) {
			this.movies = new ArrayList<Movie>();
		}
		this.movies.add(movie);
	}
}
