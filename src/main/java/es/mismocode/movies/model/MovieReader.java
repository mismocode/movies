package es.mismocode.movies.model;

import java.io.Serializable;

public class MovieReader implements Serializable {
	private static final long serialVersionUID = 6132797662269021538L;
	
	private String filename;
	private String extension;
	private String absolutePath;
	
	public MovieReader(final String filename, final String extension, final String absolutePath) {
		this.filename = filename;
		this.extension = extension;
		this.absolutePath = absolutePath;
	}
	
	public String getFilename() {
		return filename;
	}
	
	public String getExtension() {
		return extension;
	}
	
	public String getAbsolutePath() {
		return absolutePath;
	}
}
