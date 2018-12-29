package es.mismocode.movies.services;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import es.mismocode.movies.configuration.Properties;
import es.mismocode.movies.model.MovieReader;

public class FileReader {
	
	private Properties props;

	public FileReader() {
		this.props = new Properties();
	}
	
	public List<MovieReader> getMovies(){
		return this.getFilesOfFolder(new File(this.props.getResourcePath()));
	}
	
	private List<MovieReader> getFilesOfFolder(final File folder){
		if(folder != null && folder.exists() && folder.isDirectory()) {
			final File[] files = folder.listFiles();
			if(files != null && files.length > 0) {
				List<MovieReader> result = new ArrayList<MovieReader>();
				for (final File file : files) {
					if(file != null && file.exists() && file.isFile() && this.isAValidExtension(file.getName())){
						result.add(new MovieReader(
								file.getName(),
								FilenameUtils.getExtension(file.getName()).toLowerCase(),
								file.getAbsolutePath())
								);
					}
					if(file != null && file.exists() && file.isDirectory()) {
						result.addAll(this.getFilesOfFolder(file));
					}
				}
				return result;
			} else {
				return new ArrayList<MovieReader>();
			}
		}
		return new ArrayList<MovieReader>();
	}
	
	private boolean isAValidExtension(final String filename) {
	    final String fileExtension = FilenameUtils.getExtension(filename);
	    if(StringUtils.isNotBlank(fileExtension)){
	    	return this.props.getAllowedExtensions().stream()
	    			.anyMatch(extension -> extension.toLowerCase().equals(fileExtension.toLowerCase()));
	    }
	    return false;
	}
}
