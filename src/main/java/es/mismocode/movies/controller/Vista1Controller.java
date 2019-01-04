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
package es.mismocode.movies.controller;

import java.io.File;
import java.util.EventObject;

import es.mismocode.movies.VistaNavigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 * Controller class for the first vista.
 */
public class Vista1Controller {
	
	private DirectoryChooser directoryChooser;
	
	@FXML
	private TextField resourcePathTextField;
	
	@FXML
	private Button resourcePathButton;

    /**
     * Event handler fired when the user requests a new vista.
     *
     * @param event the event that triggered the handler.
     */
    @FXML
    void nextPane(ActionEvent event) {
        VistaNavigator.loadVista(VistaNavigator.VISTA_2);
    }
    
    @FXML
    void onResourcePathChange() {
    	// TODO
    	System.out.println(this.resourcePathTextField.getText());
    }
    
    @FXML
    void onResourcePathSelect() {
    	// TODO
    	/*
    	File selectedDirectory = this.directoryChooser.showDialog((Stage)((Node)((EventObject) eventVariable).getSource()).getScene().getWindow());
    	if(selectedDirectory == null){
            labelSelectedDirectory.setText("No Directory selected");
        }else{
            labelSelectedDirectory.setText(selectedDirectory.getAbsolutePath());
        }
        */
    }

}
