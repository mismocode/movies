/*
    Maven-JavaFX-Package-Example
    Copyright (C) 2017-2018 Luca Bognolo

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program. If not, see <http://www.gnu.org/licenses/>.

 */
package es.mismocode.movies;

import es.mismocode.movies.configuration.Properties;
import es.mismocode.movies.controller.MainController;
import es.mismocode.movies.model.Movie;
import es.mismocode.movies.model.MovieReader;
import es.mismocode.movies.parser.FilmAffinityParser;
import es.mismocode.movies.services.FileReader;
import es.mismocode.movies.services.MetaDataService;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.IStream;
import com.xuggle.xuggler.IStreamCoder;
import com.xuggle.xuggler.ICodec;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Main application class.
 */
public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Vista Viewer");

        stage.setScene(createScene(loadMainPane()));

        stage.show();
    }

    /**
     * Loads the main fxml layout. Sets up the vista switching VistaNavigator.
     * Loads the first vista into the fxml layout.
     *
     * @return the loaded pane.
     *
     * @throws IOException if the pane could not be loaded.
     */
    private Pane loadMainPane() throws IOException {
        FXMLLoader loader = new FXMLLoader();

        Pane mainPane = (Pane) loader.load(ClassLoader.getSystemResourceAsStream(VistaNavigator.MAIN));

        MainController mainController = loader.getController();

        VistaNavigator.setMainController(mainController);
        VistaNavigator.loadVista(VistaNavigator.VISTA_1);

        return mainPane;
    }

    /**
     * Creates the main application scene.
     *
     * @param mainPane the main application layout.
     *
     * @return the created scene.
     */
    private Scene createScene(Pane mainPane) {
        Scene scene = new Scene(mainPane);
        scene.getStylesheets().setAll(ClassLoader.getSystemResource("es/mismocode/movies/style/vista.css").toExternalForm());

        return scene;
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    	test();
        //launch(args);
    }
    
    private static void test() {
    	FilmAffinityParser filmAffinityParser = new FilmAffinityParser();
    	MetaDataService metaDataService = new MetaDataService();
    	Properties properties = new Properties();
    	
		FileReader fileReader = new FileReader();
		List<MovieReader> movieReaders = fileReader.getMovies(properties.getResourcePath());
		for(MovieReader movieReader : movieReaders) {
			if(StringUtils.isNotBlank(movieReader.getFilename()) && StringUtils.isNotBlank(movieReader.getExtension())) {
				String movieLink = filmAffinityParser.getURLOfMovie(movieReader.getFilename().toLowerCase().replace("." + movieReader.getExtension().toLowerCase(), ""));
				if(movieLink != null) {
					Movie movie = filmAffinityParser.getMovie(movieLink, null);
					System.out.println(movieLink);
					if(movie != null) {
						movie.saveImages(properties.getSavePathIconCountries(), properties.getSavePathImages());
						movie.setMetaData(metaDataService.getMetaData(movieReader.getAbsolutePath()));
						System.out.println(movie);
					}
				}
			}
		}
    }
    
    private static void test2() {
    	// TODO
    }
}
