package es.mismocode.movies.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "discardedMovies")
@XmlAccessorType(XmlAccessType.FIELD)
public class DiscardedMovies implements Serializable {
	private static final long serialVersionUID = 6132776662269021538L;
	
	@XmlElement(name = "movieDiscarded")
	private List<MovieDiscarded> discardedMovies;
	
	public DiscardedMovies() {}

	public List<MovieDiscarded> getDiscardedMovies() {
		return discardedMovies;
	}
	
	public void setDiscardedMovies(List<MovieDiscarded> discardedMovies) {
		this.discardedMovies = discardedMovies;
	}

	public void add(MovieDiscarded movieDiscarded) {
		if(this.discardedMovies == null) {
			this.discardedMovies = new ArrayList<MovieDiscarded>();
		}
		this.discardedMovies.add(movieDiscarded);
	}
}
