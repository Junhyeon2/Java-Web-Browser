package four.person.web.browser.main;

import java.util.ArrayList;

import com.sun.rowset.providers.RIOptimisticProvider;

import four.person.web.browser.settings.keyboard.SettingsKeyboardController;
import four.person.web.browser.webview.ForPersonWebview;
import four.person.web.browser.widgetbar.WidgetBar;
import four.person.web.browser.widgetbar.WidgetBarController;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AppMain extends Application {
	public double X;
	public double Y;
	public static AppMain appMain;
	public static Stage stage;
	public static Scene scene;
	public static double DEFAULT_WIDTH = 700;
	public static double DEFAULT_HEIGHT = 700;
	public static BorderPane root = new BorderPane();
	public static StackPane centerWrapper = new StackPane();
	public static StackPane centerLeft = new StackPane();
	public static StackPane centerRight = new StackPane();
	public static SplitPane split = new SplitPane();
	public static ForPersonWebview webView;
	public static double width;
	public static double height;
	public static ScrollPane topScroll;
	public WidgetBar widgetBar;
	public static int WidgetHeight = 1;
	public double x;
	public double y;
	public static Color widgetBarBackground = Color.WHITE;
	public static String defaultDomain = "naver.com";
	public static ArrayList<ForPersonWebview> listOneWebView = new ArrayList<ForPersonWebview>();
	public static ArrayList<ForPersonWebview> listTwoWebView = new ArrayList<ForPersonWebview>();
	public static ArrayList<String> listHistory = new ArrayList<String>();
	public static int curOneWebViewIdx;
	public static int curTwoWebViewIdx;
	public static String DefaultColor = "-fx-background-color: rgba(255, 255, 255, 0.7);";
	private String shortcutkeyValue;; //단축키관련
	public ForPersonWebview leftWebView;
	public ForPersonWebview rightWebView;
	@Override
	public void start(Stage primaryStage) throws Exception {
		appMain = this;
		stage = primaryStage;
		
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();	
		
		x = primaryScreenBounds.getMinX();
		y = primaryScreenBounds.getMinY();
		width = primaryScreenBounds.getWidth();
		height = primaryScreenBounds.getHeight();
		primaryStage.setMaxWidth(width);
		primaryStage.setMaxHeight(height);
		
		widgetBar = new WidgetBar(this);
		topScroll = new ScrollPane(widgetBar);
		topScroll.setFitToWidth(false);
		topScroll.setFitToHeight(true);
		topScroll.setVbarPolicy(ScrollBarPolicy.NEVER);
		topScroll.getStyleClass().add("edge-to-edge");
		topScroll.getStylesheets().add(getClass().getResource("scroll.css").toString());
		topScroll.setStyle(DefaultColor);
		
		//WebView webView = new WebView();
		//WebEngine webEngine = webView.getEngine();
		//webView = new ForPersonWebview();
		leftWebView = new ForPersonWebview(listOneWebView, 1);
		rightWebView = new ForPersonWebview(listTwoWebView, 2);
		centerWrapper.setStyle("-fx-background-color: rgba(255, 255, 255, 0);");
		centerLeft.setStyle("-fx-background-color: rgba(255, 255, 255, 0);");
		centerRight.setStyle("-fx-background-color: rgba(255, 255, 255, 0);");
		centerWrapper.getChildren().add(split);
		split.getStylesheets().add(getClass().getResource("scroll.css").toString());
		split.setStyle(DefaultColor);
		split.getItems().add(centerLeft);
		centerLeft.getChildren().add(leftWebView);
		centerRight.getChildren().add(rightWebView);

		root.setStyle("-fx-background-color: rgba(255, 255, 255, 0);");
		root.setTop(topScroll);
		root.setCenter(centerWrapper);
		
		scene = new Scene(root);
		scene.setFill(Color.TRANSPARENT);
		
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		primaryStage.setX(x);
		primaryStage.setY(y);
		primaryStage.setWidth(width);
		primaryStage.setHeight(height);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		ResizeHelper.addResizeListener(split);
		
		primaryStage.widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				root.setMinWidth(newValue.doubleValue());				
			}
		});
		primaryStage.heightProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				root.setMinHeight(newValue.doubleValue());
			}
		});
		topScroll.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(event.getButton().equals(MouseButton.PRIMARY)){
		            if(event.getClickCount() == 2){
		            	widgetBar.widgetBarController.browserResize();
		            }
		        }
			}
		});

		topScroll.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				X = primaryStage.getX() - event.getScreenX();
			    Y = primaryStage.getY() - event.getScreenY();
			}
		});
		widgetBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(WidgetBarController.isClickedResize){
					primaryStage.setX(event.getScreenX() + X);
					primaryStage.setY(event.getScreenY() + Y);
				}
			}
		});
		scene.setOnKeyPressed(event->customKeyEvent(event));
	}
	private void customKeyEvent(KeyEvent event) {

		if (event.getCode().isLetterKey() || event.getCode().isDigitKey() || event.getCode().isFunctionKey()) {
			String shortcut = event.getCode().getName();
			if (event.isAltDown()) {
				shortcut = "Alt + " + shortcut;
			}
			if (event.isControlDown()) {
				shortcut = "Ctrl + " + shortcut;
			}
			if (event.isShiftDown()) {
				shortcut = "Shift + " + shortcut;
			}

			shortcutkeyValue = shortcut.toString();
			String resultkeyValue = SettingsKeyboardController.keytable.get(shortcutkeyValue);
			// map에서 실행될것
			if(!WidgetBarController.isSettingView){
				switch(resultkeyValue){
				case "종료":
					widgetBar.widgetBarController.browserClose();
					break;
				case "크기 조절":
					widgetBar.widgetBarController.browserResize();
					break;	
				case "최소화":
					widgetBar.widgetBarController.browserMinimize();
					break;
				case "화면 분할":
					break;
				case "새 탭 추가":
					widgetBar.widgetBarController.newTab();
					break;
				case "주소 입력 창":
					widgetBar.widgetBarController.showInputDomain();
					break;
				case "홈":
					widgetBar.widgetBarController.goHomeWebView();
					break;
				case "뮤직 플레이어":
					widgetBar.widgetBarController.showMusic();
					break;
				case "앞으로 가기":
					widgetBar.widgetBarController.goNextWebView();
					break;
				case "뒤로 가기":
					widgetBar.widgetBarController.goPrevWebView();
					break;
				case "새로고침":
					widgetBar.widgetBarController.refresh();
					break;
				case "브라우저를 최상위에 고정":
					widgetBar.widgetBarController.browserPin();
					break;
				case "화면 캡처 및 편집":
					widgetBar.widgetBarController.captureWebView();
					break;
				case "탭 이동":
					widgetBar.widgetBarController.showBrowingWindow();
					break;
				case "날씨":
					widgetBar.widgetBarController.showWeather();;
					break;
				
				}
			}
			System.out.println(resultkeyValue);
			

		} else
			System.out.println("단축키가없습니다.");
	}
	public static void main(String[] args) {
		launch(args);
	}
}
