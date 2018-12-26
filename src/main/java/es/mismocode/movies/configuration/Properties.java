package es.mismocode.movies.configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Properties {

	private final String appConfigPath = Thread.currentThread().getContextClassLoader().getResource("./es/mismocode/movies/").getPath() + "application.properties";
	private java.util.Properties appProps;
	
	public Properties() {
		try {
			this.appProps = new java.util.Properties();
			this.appProps.load(new FileInputStream(this.appConfigPath));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<String> getAllowedExtensions(){
		List<String> result = new ArrayList<String>();
		int i = 0;
		while(this.appProps.getProperty("file.extensionAllowed." + i) != null) {
			result.add(this.appProps.getProperty("file.extensionAllowed." + i));
			i++;
		}
		return result;
	}
	
	public String getResourcePath() {
		return this.appProps.getProperty("file.resourcePath");
	}
}
