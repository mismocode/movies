package es.mismocode.movies.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "movieDiscarded")
@XmlAccessorType(XmlAccessType.FIELD)
public class MovieDiscarded implements Serializable {
	private static final long serialVersionUID = 6132786662268021538L;

	@XmlElement(name = "resourcePath")
	private String resourcePath;
	
	public MovieDiscarded() {}

	public MovieDiscarded(String resourcePath) {
		this.resourcePath = resourcePath;
	}

	public String getResourcePath() {
		return resourcePath;
	}

	public void setResourcePath(String resourcePath) {
		this.resourcePath = resourcePath;
	}
}
