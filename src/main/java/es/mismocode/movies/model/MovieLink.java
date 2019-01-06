package es.mismocode.movies.model;

import java.io.Serializable;

public class MovieLink implements Serializable {
	private static final long serialVersionUID = 6132797652269021538L;
	
	private String filename;
	private String filenameFound;
	private String link;
	
	public MovieLink(String filename, String filenameFound, String link) {
		this.filename = filename;
		this.filenameFound = filenameFound;
		this.link = link;
	}

	public String getFilename() {
		return filename;
	}

	public String getFilenameFound() {
		return filenameFound;
	}

	public String getLink() {
		return link;
	}
}
