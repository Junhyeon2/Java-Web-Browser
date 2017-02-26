package four.person.web.browser.webview;

import java.net.URL;
import java.util.ResourceBundle;

import four.person.web.browser.main.AppMain;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class ForPersonWebviewController implements Initializable{
	@FXML BorderPane webViewRoot;
	@FXML HBox hboxDomain;
	@FXML TextField tfDomain;
	@FXML WebView webView;
	@FXML HBox hboxBrowsing;
	WebEngine webEngine;
	
	public ForPersonWebviewController() {
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		webEngine = webView.getEngine();
		BackgroundFill backgroundFill = new BackgroundFill(AppMain.widgetBarBackground, CornerRadii.EMPTY, Insets.EMPTY);
		Background background = new Background(backgroundFill);
		tfDomain.setPrefWidth(AppMain.width);
		hboxDomain.setBackground(background);
		hboxBrowsing.setBackground(background);
		tfDomain.setOnKeyPressed(event->loadDomain(event));
	}
	private void loadDomain(KeyEvent event) {
		if(event.getCode() == KeyCode.ENTER){
			String url = tfDomain.getText();
			if (url.contains("http://")) {
				webEngine.load(tfDomain.getText());
			} else {
				webEngine.load("http://" + tfDomain.getText());
			}
			String domain = webEngine.getLocation();
			tfDomain.setText(domain);
		}
	}
}
