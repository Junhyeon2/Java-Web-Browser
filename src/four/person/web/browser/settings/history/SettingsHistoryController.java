package four.person.web.browser.settings.history;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import four.person.web.browser.main.AppMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class SettingsHistoryController implements Initializable{
	@FXML VBox vboxHistory;
	@FXML Button btnDeleteAll;
	@FXML Button btnDeleteSelected;
	ArrayList<CheckBox> listChbox = new ArrayList<CheckBox>();
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		for(int i=0; i<AppMain.listHistory.size(); ++i){
			CheckBox chBox = new CheckBox(AppMain.listHistory.get(i));
			chBox.setId("content_title");
			chBox.setMaxWidth(AppMain.width-600.0);
			chBox.setPrefHeight(30.0);
			chBox.setClip(new Rectangle(chBox.getMaxWidth(), 30.0));
			listChbox.add(chBox);
			vboxHistory.getChildren().add(chBox);
		}
		btnDeleteAll.setOnAction(event->deleteAllHistory(event));
		btnDeleteSelected.setOnAction(event->deleteSelectedHistory(event));
	}

	private void deleteSelectedHistory(ActionEvent event) {
		for(int i=0; i<AppMain.listHistory.size(); ++i){
			if(listChbox.get(i).isSelected()){
				vboxHistory.getChildren().remove(listChbox.get(i));
				listChbox.remove(i);
				AppMain.listHistory.remove(i);
			}
		}
	}

	private void deleteAllHistory(ActionEvent event) {
		for(int i=0; i<AppMain.listHistory.size(); ++i){
			vboxHistory.getChildren().remove(listChbox.get(i));
		}
		listChbox.removeAll(listChbox);
		AppMain.listHistory.removeAll(AppMain.listHistory);
	}
}
