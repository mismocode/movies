package es.mismocode.movies.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "country")
@XmlAccessorType(XmlAccessType.FIELD)
public class Country implements Serializable {
	private static final long serialVersionUID = 6132787662169021538L;

	@XmlAttribute(name = "id")
	private String id;
	
	@XmlElement(name = "name")
	private String name;
	
	@XmlElement(name = "iconPath")
	private String iconPath;
	
	@XmlElement(name = "extension")
	private String extension;
	
	public Country() {}
	
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
}
