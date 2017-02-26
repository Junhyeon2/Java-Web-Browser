package four.person.web.browser.settings;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import four.person.web.browser.main.AppMain;
import four.person.web.browser.widgetbar.WidgetBar;
import four.person.web.browser.widgetbar.WidgetBarController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class SettingsController implements Initializable{
	//settings.fxml
	@FXML BorderPane settingsRoot;
	@FXML Button btnBasic;
	@FXML Button btnWidgetBar;
	@FXML Button btnKeyboard;
	@FXML Button btnHistory;
	@FXML Button btnInfo;
	@FXML Button btnConfirm;
	
	//VBox center;
	ScrollPane center;
	public SettingsController() {
		
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnBasic.setOnMouseClicked(event->showBasicSetting(event));
		btnWidgetBar.setOnMouseClicked(event->showWidgetBarSetting(event));
		btnKeyboard.setOnMouseClicked(event->showKeyboardSetting(event));
		btnHistory.setOnMouseClicked(event->showHistorySetting(event));
		btnInfo.setOnMouseClicked(event->showInfoSetting(event));
		btnConfirm.setOnAction(event->exitEventHandler(event));
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("basic/settings_basic.fxml"));
		try {
			center = fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		btnBasic.setStyle("-fx-text-fill: #000000");
		btnWidgetBar.setStyle("-fx-text-fill: #A0A0A0");
		btnKeyboard.setStyle("-fx-text-fill: #A0A0A0");
		btnHistory.setStyle("-fx-text-fill: #A0A0A0");
		btnInfo.setStyle("-fx-text-fill: #A0A0A0");
		settingsRoot.setCenter(center);
	}
	private void showBasicSetting(MouseEvent event) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("basic/settings_basic.fxml"));
		try {
			center = fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		btnBasic.setStyle("-fx-text-fill: #000000");
		btnWidgetBar.setStyle("-fx-text-fill: #A0A0A0");
		btnKeyboard.setStyle("-fx-text-fill: #A0A0A0");
		btnHistory.setStyle("-fx-text-fill: #A0A0A0");
		btnInfo.setStyle("-fx-text-fill: #A0A0A0");
		settingsRoot.setCenter(center);
	}
	private void showWidgetBarSetting(MouseEvent event) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("widgetbar/settings_widgetbar.fxml"));
		try {
			center = fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		btnBasic.setStyle("-fx-text-fill: #A0A0A0");
		btnWidgetBar.setStyle("-fx-text-fill: #000000");
		btnKeyboard.setStyle("-fx-text-fill: #A0A0A0");
		btnHistory.setStyle("-fx-text-fill: #A0A0A0");
		btnInfo.setStyle("-fx-text-fill: #A0A0A0");
		settingsRoot.setCenter(center);		
	}
	private void showKeyboardSetting(MouseEvent event) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("keyboard/settings_keyboard.fxml"));
		try {
			center = fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		btnBasic.setStyle("-fx-text-fill: #A0A0A0");
		btnWidgetBar.setStyle("-fx-text-fill: #A0A0A0");
		btnKeyboard.setStyle("-fx-text-fill: #000000");
		btnHistory.setStyle("-fx-text-fill: #A0A0A0");
		btnInfo.setStyle("-fx-text-fill: #A0A0A0");
		settingsRoot.setCenter(center);
	}
	private void showHistorySetting(MouseEvent event) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("history/settings_history.fxml"));
		try {
			center = fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		btnBasic.setStyle("-fx-text-fill: #A0A0A0");
		btnWidgetBar.setStyle("-fx-text-fill: #A0A0A0");
		btnKeyboard.setStyle("-fx-text-fill: #A0A0A0");
		btnHistory.setStyle("-fx-text-fill: #000000");
		btnInfo.setStyle("-fx-text-fill: #A0A0A0");
		settingsRoot.setCenter(center);		
	}
	private void showInfoSetting(MouseEvent event) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("info/settings_info.fxml"));
		try {
			center = fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		btnBasic.setStyle("-fx-text-fill: #A0A0A0");
		btnWidgetBar.setStyle("-fx-text-fill: #A0A0A0");
		btnKeyboard.setStyle("-fx-text-fill: #A0A0A0");
		btnHistory.setStyle("-fx-text-fill: #A0A0A0");
		btnInfo.setStyle("-fx-text-fill: #000000");
		settingsRoot.setCenter(center);		
	}
	private void exitEventHandler(ActionEvent event) {
		// 데이터 저장
		
		// 화면 없애기
		WidgetBarController.isSettingView = !WidgetBarController.isSettingView;
		AppMain.centerWrapper.getChildren().remove(settingsRoot);
		WidgetBarController.widgetBarMenuItem[1].setDisable(false);
	}
}
