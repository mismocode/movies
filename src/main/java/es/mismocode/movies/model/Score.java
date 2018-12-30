package es.mismocode.movies.model;

import java.io.Serializable;

public class Score implements Serializable {
	private static final long serialVersionUID = 6132786662269021538L;

	private double score;
	private Web web;
	
	public Score(final double score, final Web web) {
		this.score = score;
		this.web = web;
	}

	public double getScore() {
		return score;
	}

	public Web getWeb() {
		return web;
	}

	@Override
	public String toString() {
		return "Score [score=" + score + ", web=" + web + "]";
	}
}
