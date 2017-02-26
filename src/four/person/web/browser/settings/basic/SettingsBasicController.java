package four.person.web.browser.settings.basic;

import java.net.URL;
import java.util.ResourceBundle;

import four.person.web.browser.main.AppMain;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

public class SettingsBasicController implements Initializable{
	//settings_basic.fxml
	@FXML RadioButton radBtnDefault, radBtnCustom;
	@FXML TextField tfDomain;
	ToggleGroup tgBasic;
	public SettingsBasicController() {
		tgBasic = new ToggleGroup();
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		radBtnDefault.setToggleGroup(tgBasic);
		radBtnCustom.setToggleGroup(tgBasic);
		tgBasic.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				if(newValue.equals(radBtnDefault)){
					tfDomain.setDisable(true);
				}else if(newValue.equals(radBtnCustom)){
					tfDomain.setDisable(false);
					tfDomain.requestFocus();
				}
			}
		});
		tfDomain.setText(AppMain.defaultDomain);
		
		if(tfDomain.getText() == null){
			tgBasic.selectToggle(radBtnDefault);
		}else{
			tgBasic.selectToggle(radBtnCustom);
		}
		
		tfDomain.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				AppMain.defaultDomain = newValue;
			}
		});
	}

}
