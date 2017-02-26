package four.person.web.browser.settings;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

public class Settings extends BorderPane{
	public Settings() {
		//데이터 불러오기
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("settings.fxml"));
		fxmlLoader.setRoot(this);
		this.getStylesheets().add(getClass().getResource("settings.css").toExternalForm());
		try {
			fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
}
