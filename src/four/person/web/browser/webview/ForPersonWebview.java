package four.person.web.browser.webview;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import four.person.web.browser.main.AppMain;
import four.person.web.browser.widgetbar.WidgetBarController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Dialog;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.PopupFeatures;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

public class ForPersonWebview extends BorderPane {
	final double scrollMinPos = 0.0;
	final double scrollMaxPos = 100.0;
	double zoom = 1;
	double zoomValue = 0.1;
	double scrollCurPos = 0.0;
	public BorderPane root;
	public VBox top;
	public HBox topDomain;
	public HBox topBrowsing;
	public WebView center;
	public WebEngine webEngine;
	public TextField tfDomain;
	public boolean isFocused;
	public ArrayList<ImageView> viewList = new ArrayList<ImageView>();
	public ArrayList<ForPersonWebview> list;
	public ScrollPane topScroll;
	public WebHistory history;
	public int webviewIdx;
	ContextMenu browingViewMenu;
	MenuItem browingViewMenuItem;
	ImageView clickedIv;
	int clickedIdx;
	
	public ForPersonWebview(ArrayList<ForPersonWebview> list, int webviewIdx) {
		this.list = list;
		this.webviewIdx = webviewIdx;
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("for_person_webview.fxml"));
		fxmlLoader.setRoot(this);
		this.getStylesheets().add(getClass().getResource("for_person_webview.css").toExternalForm());
		try {
			root = fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		top = (VBox)root.getChildren().get(0);
		topDomain = (HBox)top.getChildren().get(0);
		topBrowsing = (HBox)top.getChildren().get(1);
		center = (WebView)root.getChildren().get(1);
		webEngine = center.getEngine();
		history = webEngine.getHistory();
		root.getChildren().remove(topDomain);
		root.getChildren().remove(topBrowsing);
		root.getChildren().remove(top);
		tfDomain = (TextField)topDomain.getChildren().get(0);
		
		center.setId(Integer.toString(list.size()));

		list.add(this);
		
		topScroll = new ScrollPane(topBrowsing);
		topScroll.setFitToWidth(false);
		topScroll.setFitToHeight(true);
		topScroll.setVbarPolicy(ScrollBarPolicy.NEVER);
		topScroll.getStyleClass().add("edge-to-edge");
		
		browingViewMenu = new ContextMenu();
		browingViewMenuItem = new MenuItem("삭제");
		browingViewMenu.getItems().add(browingViewMenuItem);
		
		webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {
			public void changed(ObservableValue ov, State oldState, State newState) {
				if(webviewIdx == 1){
					AppMain.curOneWebViewIdx = Integer.parseInt(center.getId());
					if (newState == State.SUCCEEDED) {
						ObservableList<WebHistory.Entry> entryList = history.getEntries();
						AppMain.listHistory.add(entryList.get(history.getCurrentIndex()).getUrl());
						AppMain.curOneWebViewIdx = Integer.parseInt(center.getId());
						tfDomain.setText(webEngine.getLocation());
						center.requestFocus();
					}
				}else{
					AppMain.curTwoWebViewIdx = Integer.parseInt(center.getId());
					if (newState == State.SUCCEEDED) {
						ObservableList<WebHistory.Entry> entryList = history.getEntries();
						AppMain.listHistory.add(entryList.get(history.getCurrentIndex()).getUrl());
						AppMain.curTwoWebViewIdx = Integer.parseInt(center.getId());
						tfDomain.setText(webEngine.getLocation());
						center.requestFocus();
					}
				}
			}
		});
		webEngine.setOnAlert(new EventHandler<WebEvent<String>>() {
			@Override
			public void handle(WebEvent<String> event) {
				Dialog<Void> alert = new Dialog<>();
				alert.initModality(Modality.APPLICATION_MODAL);
				alert.initStyle(StageStyle.UNDECORATED);
				alert.initStyle(StageStyle.TRANSPARENT);
				alert.getDialogPane().setId("alert");
				alert.getDialogPane().getStylesheets().add(getClass().getResource("for_person_webview.css").toExternalForm());
				alert.getDialogPane().setContentText(event.getData());
				alert.getDialogPane().getButtonTypes().add(new ButtonType("확인"));
				alert.showAndWait();
			}
		});
		webEngine.setConfirmHandler(new Callback<String, Boolean>() {
			@Override
			public Boolean call(String param) {
				Dialog<ButtonType> alert = new Dialog<ButtonType>();
				alert.initModality(Modality.APPLICATION_MODAL);
				alert.initStyle(StageStyle.UNDECORATED);
				alert.initStyle(StageStyle.TRANSPARENT);
				alert.getDialogPane().setId("alert");
				alert.getDialogPane().getStylesheets().add(getClass().getResource("for_person_webview.css").toExternalForm());
				alert.getDialogPane().setContentText(param);
				alert.getDialogPane().getButtonTypes().add(ButtonType.NO);
				alert.getDialogPane().getButtonTypes().add(ButtonType.YES);
				Optional<ButtonType> result = alert.showAndWait();
				
				if(result.get() == ButtonType.NO){
					return false;
				}else{
					return true;
				}
			}
		});
		webEngine.setCreatePopupHandler(new Callback<PopupFeatures, WebEngine>() {
			@Override
			public WebEngine call(PopupFeatures param) {
				if(param.hasStatus()){
					ForPersonWebview forPersonWebview = new ForPersonWebview(list, webviewIdx);
					AppMain.centerWrapper.getChildren().add(forPersonWebview);
					return forPersonWebview.webEngine;
				}else{
					Stage stage = new Stage(StageStyle.UTILITY);
					WebView popupWebView = new WebView();
					stage.setScene(new Scene(popupWebView, 400, 500));
					stage.sizeToScene();
					stage.show();
					return popupWebView.getEngine();
				}
			}
		});
		
		webEngine.setOnResized(new EventHandler<WebEvent<Rectangle2D>>() {
			@Override
			public void handle(WebEvent<Rectangle2D> event) {
				System.out.println("resize");				
			}
		});
		center.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(webviewIdx == 1)
					AppMain.curOneWebViewIdx = Integer.parseInt(center.getId());
				else
					AppMain.curTwoWebViewIdx = Integer.parseInt(center.getId());
			}
		});
		String url = AppMain.defaultDomain;
		if (!url.contains("http://")) {
			url = "http://"+url;
		}
		center.setOnScroll(new EventHandler<ScrollEvent>() {
			@Override
			public void handle(ScrollEvent event) {
				if(event.isControlDown()){
					double deltaY = event.getDeltaY();
					
					if(deltaY < 0){
						zoom = zoom - zoomValue;
						center.setZoom(zoom);
					}else{
						zoom = zoom + zoomValue;
						center.setZoom(zoom);
					}
				}
			}
		});
		webEngine.load(url);
		
		topScroll.setOnScroll(event->browsingHorizontalScroll(event));
		browingViewMenuItem.setOnAction(event->deleteWebView(event));
	}
	public void deleteWebView(ActionEvent event) {
		if(webviewIdx == 1){

		}else{

		}
	}
	public void webViewList(){
		for(int i=0; i<viewList.size(); ++i){
			topBrowsing.getChildren().remove(viewList.get(i));
		}
		viewList.removeAll(viewList);
		for(int i=0; i<list.size(); ++i){
			ImageView iv = new ImageView();
			iv.setId("iv_img");
			WritableImage snapshotImg = list.get(i).snapshot(new SnapshotParameters(), null);
			iv.setImage(snapshotImg);
			iv.setFitWidth(160);
			iv.setFitHeight(110);
			iv.setOnMouseClicked(event->clickedBrowingImg(event));
			viewList.add(iv);
			topBrowsing.getChildren().add(iv);
		}
	}

	private void clickedBrowingImg(MouseEvent event) {
		if(event.getButton() == MouseButton.PRIMARY){
			for(int i=0; i<list.size(); ++i){
				list.get(i).setVisible(true);
			}
			if(webviewIdx == 1){
				clickedIv = (ImageView)event.getSource();
				clickedIdx = viewList.indexOf(clickedIv);
				list.get(AppMain.curOneWebViewIdx).getChildren().remove(list.get(AppMain.curOneWebViewIdx).getTop());
				AppMain.curOneWebViewIdx = clickedIdx;
				list.get(clickedIdx).toFront();
			}else{
				clickedIv = (ImageView)event.getSource();
				clickedIdx = viewList.indexOf(clickedIv);
				list.get(AppMain.curTwoWebViewIdx).getChildren().remove(list.get(AppMain.curTwoWebViewIdx).getTop());
				AppMain.curTwoWebViewIdx = clickedIdx;
				list.get(clickedIdx).toFront();
			}
		}else if(event.getButton() == MouseButton.SECONDARY){
			browingViewMenu.show(topBrowsing, event.getScreenX(), event.getScreenY());
			clickedIv = (ImageView) event.getSource();
			clickedIdx = viewList.indexOf(clickedIv);
		}
	}
	
	private void browsingHorizontalScroll(ScrollEvent event) {
		 if (event.getDeltaY() > 0){
			 if(scrollCurPos <= scrollMinPos)
				 topScroll.setHvalue(scrollMinPos);
			 else
				 topScroll.setHvalue(scrollCurPos = scrollCurPos-0.25);
		 }
        else{
       	 if(scrollCurPos >= scrollMaxPos)
				 topScroll.setHvalue(scrollMaxPos);
			 else
				 topScroll.setHvalue(scrollCurPos = scrollCurPos+0.25);
        }
	}
}
