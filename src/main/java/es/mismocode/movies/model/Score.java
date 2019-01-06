package es.mismocode.movies.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "score")
@XmlAccessorType(XmlAccessType.FIELD)
public class Score implements Serializable {
	private static final long serialVersionUID = 6132786662269021538L;
	
	@XmlElement(name = "web")
	private Web web;

	@XmlElement(name = "score")
	private double score;
	
	public Score() {}
	
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
}
