package es.mismocode.movies.model;

import java.io.Serializable;

public class Country implements Serializable {
	private static final long serialVersionUID = 6132787662169021538L;

	private String id;
	private String name;
	private String iconPath;
	private String extension;
	
	public Country(final String id, final String name, final String iconPath, final String extension) {
		this.id = id;
		this.name = name;
		this.iconPath = iconPath;
		this.extension = extension;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getIconPath() {
		return iconPath;
	}

	public String getExtension() {
		return extension;
	}

	@Override
	public String toString() {
		return "Country [id=" + id + ", name=" + name + ", iconPath=" + iconPath + ", extension=" + extension + "]";
	}
}
