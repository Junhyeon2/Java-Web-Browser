package four.person.web.browser.widgetbar;

import java.util.ArrayList;

import four.person.web.browser.capture.CaptureEditor;
import four.person.web.browser.main.AppMain;
import four.person.web.browser.settings.Settings;
import four.person.web.browser.weather.Weather;
import four.person.web.browser.webview.ForPersonWebview;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;

public class WidgetBarController{
	final double scrollMinPos = 0.0;
	final double scrollMaxPos = 100.0;
	double scrollCurPos = 0.0;
	WidgetBar widgetBar;
	ArrayList<WidgetButton> listBtn;
	WidgetRect prevRect = null;
	public WidgetButton clickedBtn = null;
	
	ContextMenu widgetBarMenu, btnMenu;
	public static MenuItem[] widgetBarMenuItem, btnSizeMenuItem;
	MenuItem btnDeleteItem;
	
	boolean isEdit = false;
	public static boolean isClickedResize = false;
	boolean isClickedPin = false;
	boolean isClickedWeather = false;
	public static boolean isCaptured = false;
	public static boolean isSettingView = false;
	public static boolean isSplit = false;
	
	public static Settings settings = new Settings();;
	Weather weatherClass = new Weather();;
		
	public WidgetBarController(WidgetBar widgetBar) {
		this.widgetBar = widgetBar;
		listBtn = widgetBar.listBtn;
		widgetBarMenu = widgetBar.widgetBarMenu;
		btnMenu = widgetBar.btnMenu;
		widgetBarMenuItem = widgetBar.widgetBarMenuItem;
		btnSizeMenuItem = widgetBar.btnSizeMenuItem;
		btnDeleteItem = widgetBar.btnDeleteMenuItem;

		for(int i=0; i<listBtn.size(); ++i){
			listBtn.get(i).setOnMouseClicked(event -> btnMouseClicked(event));
			listBtn.get(i).setOnMouseDragged(event -> btnMouseDragged(event));
			listBtn.get(i).setOnMouseReleased(event -> btnMouseReleased(event));
		}
        
		for(int i=0; i<widgetBarMenuItem.length; ++i){
			widgetBarMenuItem[i].setOnAction(event->widgetBarMenuHandler(event));
		}
		
		for(int i=0; i<btnSizeMenuItem.length; ++i){
			btnSizeMenuItem[i].setOnAction(event->btnSizeMenuHandler(event));
		}
		
		
		btnDeleteItem.setOnAction(event->btnDeleteHandler(event));
		
		this.widgetBar.setOnMouseClicked(event->WidgetBarMouseRightClicked(event));
		this.widgetBar.setOnScroll(event->WidgetBarHorizontalScroll(event));
		

	}
	private void btnDeleteHandler(ActionEvent event) {
		int row = clickedBtn.getRow();
		int col = clickedBtn.getCol();
		WidgetRect targetRect = null;
		for(int i=0; i<WidgetBar.listRect.size(); ++i){
			if(row == WidgetBar.listRect.get(i).getRow() && col == WidgetBar.listRect.get(i).getCol()){
				targetRect = WidgetBar.listRect.get(i);
			}
		}
		int idxTargetRect = Integer.parseInt(targetRect.getId());
		if(clickedBtn.isBigger()){
			WidgetRect[] nearRect = new WidgetRect[4];
			nearRect[0] = WidgetBar.listRect.get(idxTargetRect);
			nearRect[1] = WidgetBar.listRect.get(idxTargetRect+1);
			nearRect[2] = WidgetBar.listRect.get(idxTargetRect+widgetBar.numOfRect);
			nearRect[3] = WidgetBar.listRect.get(idxTargetRect+widgetBar.numOfRect+1);		
			nearRect[0].setIsFill(false);
			nearRect[1].setIsFill(false);
			nearRect[2].setIsFill(false);
			nearRect[3].setIsFill(false);
		}else{
			WidgetBar.listRect.get(idxTargetRect).setIsFill(false);
		}
		widgetBar.getChildren().remove(clickedBtn);
		widgetBar.listBtn.remove(clickedBtn);
	}

	private void btnSizeMenuHandler(ActionEvent event) {
		Object obj = event.getSource();
		if(obj.equals(btnSizeMenuItem[0])){
			//크게
			int row = clickedBtn.getRow();
			int col = clickedBtn.getCol();
			WidgetRect targetRect = null;
			
			for(int i=0; i<WidgetBar.listRect.size(); ++i){
				WidgetRect r = WidgetBar.listRect.get(i);
				if(row == r.getRow() && col == r.getCol()){
					targetRect = r;
				}
			}
			int idxTargetRect = Integer.parseInt(targetRect.getId());

			boolean isFill = false;
			WidgetRect[] nearRect = new WidgetRect[4];
			if(targetRect.getRow() == 0 && idxTargetRect != widgetBar.numOfRect-1){
				//targetRect의 row가 0 일 때
				nearRect[0] = WidgetBar.listRect.get(idxTargetRect);
				nearRect[1] = WidgetBar.listRect.get(idxTargetRect + 1);
				nearRect[2] = WidgetBar.listRect.get(idxTargetRect + widgetBar.numOfRect);
				nearRect[3] = WidgetBar.listRect.get(idxTargetRect + widgetBar.numOfRect + 1);
			}else if(targetRect.getRow() == 1 && idxTargetRect != widgetBar.numOfRect-1){
				nearRect[0] = WidgetBar.listRect.get(idxTargetRect + widgetBar.numOfRect);
				nearRect[1] = WidgetBar.listRect.get(idxTargetRect + widgetBar.numOfRect + 1);
				nearRect[2] = WidgetBar.listRect.get(idxTargetRect);
				nearRect[3] = WidgetBar.listRect.get(idxTargetRect + 1);
			}else if(targetRect.getRow() == 0 && idxTargetRect == widgetBar.numOfRect-1){
				nearRect[0] = WidgetBar.listRect.get(idxTargetRect - 2);
				nearRect[1] = WidgetBar.listRect.get(idxTargetRect - 1);
				nearRect[2] = WidgetBar.listRect.get(idxTargetRect + widgetBar.numOfRect - 2);
				nearRect[3] = WidgetBar.listRect.get(idxTargetRect + widgetBar.numOfRect - 1);
			}else if(targetRect.getRow() == 1 && idxTargetRect == widgetBar.numOfRect-1){
				nearRect[0] = WidgetBar.listRect.get(idxTargetRect + widgetBar.numOfRect - 2);
				nearRect[1] = WidgetBar.listRect.get(idxTargetRect + widgetBar.numOfRect - 1);
				nearRect[2] = WidgetBar.listRect.get(idxTargetRect - 2);
				nearRect[3] = WidgetBar.listRect.get(idxTargetRect - 1);
			}
			// 주변에 빈 공간이 있는 지 확인
			for (int i = 1; i < nearRect.length; ++i) {
				if (nearRect[i].getIsFill()) {
					isFill = true;
				}
			}
			if (!isFill) {
				clickedBtn.setBigger(true);
				clickedBtn.setSize(55.0);
				clickedBtn.setPrefSize(clickedBtn.getSize(), clickedBtn.getSize());
				nearRect[0].setIsFill(true);
				nearRect[1].setIsFill(true);
				nearRect[2].setIsFill(true);
				nearRect[3].setIsFill(true);
				if(idxTargetRect != widgetBar.numOfRect-1){
					clickedBtn.setLayoutX(WidgetBar.listRect.get(idxTargetRect).getLayoutX());
					clickedBtn.setLayoutY(WidgetBar.listRect.get(idxTargetRect).getLayoutY());
				}else{
					clickedBtn.setLayoutX(WidgetBar.listRect.get(idxTargetRect-1).getLayoutX());
					clickedBtn.setLayoutY(WidgetBar.listRect.get(idxTargetRect-1).getLayoutY());
				}
			} else {
				clickedBtn.setBigger(true);
				clickedBtn.setSize(55.0);
				clickedBtn.setPrefSize(clickedBtn.getSize(), clickedBtn.getSize());
				//빈 공간이 없을 때 빈 공간을 찾기
				for(int i=0; i<widgetBar.numOfRect-1; ++i){
					nearRect[0] = WidgetBar.listRect.get(i);
					nearRect[1] = WidgetBar.listRect.get(i + 1);
					nearRect[2] = WidgetBar.listRect.get(i + widgetBar.numOfRect);
					nearRect[3] = WidgetBar.listRect.get(i + widgetBar.numOfRect + 1);
					if(targetRect.equals(nearRect[0])){
						if (!nearRect[1].getIsFill() && !nearRect[2].getIsFill() && !nearRect[3].getIsFill()) {
							nearRect[1].setIsFill(true);
							nearRect[2].setIsFill(true);
							nearRect[3].setIsFill(true);
							
							clickedBtn.setLayoutX(WidgetBar.listRect.get(i).getLayoutX());
							clickedBtn.setLayoutY(WidgetBar.listRect.get(i).getLayoutY());
							clickedBtn.setCol(nearRect[0].getCol());
							clickedBtn.setRow(nearRect[0].getRow());
							break;
						}
					}else{
						if (!nearRect[0].getIsFill() && !nearRect[1].getIsFill() && !nearRect[2].getIsFill() && !nearRect[3].getIsFill()) {
							targetRect.setIsFill(false);
							nearRect[0].setIsFill(true);
							nearRect[1].setIsFill(true);
							nearRect[2].setIsFill(true);
							nearRect[3].setIsFill(true);
							
							clickedBtn.setLayoutX(WidgetBar.listRect.get(i).getLayoutX());
							clickedBtn.setLayoutY(WidgetBar.listRect.get(i).getLayoutY());
							clickedBtn.setCol(nearRect[0].getCol());
							clickedBtn.setRow(nearRect[0].getRow());
							break;
						}
					}
				}
			}
		}else if(obj.equals(btnSizeMenuItem[1])){
			//작게
			int row = clickedBtn.getRow();
			int col = clickedBtn.getCol();
			WidgetRect targetRect = null;
			
			for(int i=0; i<WidgetBar.listRect.size(); ++i){
				WidgetRect r = WidgetBar.listRect.get(i);
				if(row == r.getRow() && col == r.getCol()){
					targetRect = r;
				}
			}	
			int idxTargetRect = Integer.parseInt(targetRect.getId());

			WidgetRect[] nearRect = new WidgetRect[3];
			nearRect[0] = WidgetBar.listRect.get(idxTargetRect + 1);
			nearRect[1] = WidgetBar.listRect.get(idxTargetRect + widgetBar.numOfRect);
			nearRect[2] = WidgetBar.listRect.get(idxTargetRect + widgetBar.numOfRect + 1);
			
			for(int i=0; i<nearRect.length; ++i){
				nearRect[i].setIsFill(false);
			}
			
			clickedBtn.setBigger(false);
			clickedBtn.setSize(25.0);
			clickedBtn.setPrefSize(clickedBtn.getSize(), clickedBtn.getSize());
		}
	}

	private void WidgetBarHorizontalScroll(ScrollEvent event) {
		 if (event.getDeltaY() > 0){
			 if(scrollCurPos <= scrollMinPos)
				 widgetBar.root.topScroll.setHvalue(scrollMinPos);
			 else
				 widgetBar.root.topScroll.setHvalue(scrollCurPos = scrollCurPos-0.25);
		 }
         else{
        	 if(scrollCurPos >= scrollMaxPos)
				 widgetBar.root.topScroll.setHvalue(scrollMaxPos);
			 else
				 widgetBar.root.topScroll.setHvalue(scrollCurPos = scrollCurPos+0.25);
         }
	}

	private void btnMouseClicked(MouseEvent event) {
		clickedBtn = (WidgetButton)event.getSource();
		if(event.getButton() == MouseButton.PRIMARY && isEdit == false){
			switch (clickedBtn.getId()) {
			case "close":
				browserClose();
				break;
			case "resize":
				browserResize();
				break;
			case "minimize":
				browserMinimize();
				break;
			case "split":
				splitWebView();
				break;
			case "domain":
				showInputDomain();
				break;
			case "windows":
				showBrowingWindow();
				break;
			case "prev":
				goPrevWebView();
				break;
			case "next":
				goNextWebView();
				break;
			case "home":
				goHomeWebView();
				break;
			case "refresh":
				refresh();
				break;
			case "add":
				newTab();
				break;
			case "music":
				showMusic();
				break;
			case "weather":
				showWeather();
				break;
			case "pin":
				browserPin();
				break;
			case "capture":
				captureWebView();
				break;
			}
		}else if(event.getButton() == MouseButton.SECONDARY && isEdit == true){
			if(clickedBtn.isBigger()){
				//크면
				btnSizeMenuItem[0].setDisable(true);
				btnSizeMenuItem[1].setDisable(false);
			}else{
				//작으면
				btnSizeMenuItem[0].setDisable(false);
				btnSizeMenuItem[1].setDisable(true);
			}
			btnMenu.show(clickedBtn, event.getScreenX(), event.getScreenY());
		}
	}
	public void splitWebView() {
		isSplit = !isSplit;
		if(isSplit){
			AppMain.split.getItems().add(AppMain.centerRight);
		}else{
			AppMain.split.getItems().remove(AppMain.centerRight);
		}
	}
	public void showMusic() {
		
	}

	public void showWeather() {
		isClickedWeather = !isClickedWeather;
		if(isClickedWeather){
			AppMain.centerWrapper.getChildren().add(weatherClass);
		}else{
			AppMain.centerWrapper.getChildren().remove(weatherClass);
		}
		
	}

	public void captureWebView() {
		isCaptured = !isCaptured;
		WritableImage image = AppMain.centerWrapper.snapshot(new SnapshotParameters(), null);	
		CaptureEditor captureEditor = new CaptureEditor(image);
		
		for(int i=0; i<WidgetBar.listBtn.size(); ++i){
			if(WidgetBar.listBtn.get(i).getId().equals("capture")){
				WidgetBar.listBtn.get(i).setDisable(true);
			}
		}
		AppMain.centerWrapper.getChildren().add(captureEditor);
	}

	public void browserPin() {
		isClickedPin = !isClickedPin;
		if(isClickedPin){
			AppMain.stage.setAlwaysOnTop(true);
			for(int i=0; i<widgetBar.root.listOneWebView.size(); ++i){
				if(AppMain.curOneWebViewIdx == i){
					widgetBar.root.listOneWebView.get(AppMain.curOneWebViewIdx).setStyle("-fx-opacity: 0.4;");
					continue;
				}
				widgetBar.root.listOneWebView.get(i).setVisible(false);
			}
			for(int i=0; i<widgetBar.root.listTwoWebView.size(); ++i){
				if(AppMain.curTwoWebViewIdx == i){
					widgetBar.root.listTwoWebView.get(AppMain.curTwoWebViewIdx).setStyle("-fx-opacity: 0.4;");
					continue;
				}
				widgetBar.root.listTwoWebView.get(i).setVisible(false);
			}
		}else{
			AppMain.stage.setAlwaysOnTop(false);
			for(int i=0; i<widgetBar.root.listOneWebView.size(); ++i){
				if(AppMain.curOneWebViewIdx == i){
					widgetBar.root.listOneWebView.get(AppMain.curOneWebViewIdx).setStyle("-fx-opacity: 1;");
					continue;
				}
				widgetBar.root.listOneWebView.get(i).setVisible(true);
			}
			for(int i=0; i<widgetBar.root.listTwoWebView.size(); ++i){
				if(AppMain.curTwoWebViewIdx == i){
					widgetBar.root.listTwoWebView.get(AppMain.curTwoWebViewIdx).setStyle("-fx-opacity: 1;");
					continue;
				}
				widgetBar.root.listTwoWebView.get(i).setVisible(false);
			}
		}
	}

	public void newTab() {
		ForPersonWebview forOnePersonWebview = new ForPersonWebview(widgetBar.root.listOneWebView, 1);
		AppMain.centerLeft.getChildren().add(forOnePersonWebview);
		
		if(!isSplit)
			return;
		ForPersonWebview forTwoPersonWebview = new ForPersonWebview(widgetBar.root.listTwoWebView, 2);
		AppMain.centerRight.getChildren().add(forTwoPersonWebview);
	}

	public void refresh() {
		widgetBar.root.listOneWebView.get(AppMain.curOneWebViewIdx).webEngine.reload();
		if(!isSplit)
			return;
		widgetBar.root.listTwoWebView.get(AppMain.curTwoWebViewIdx).webEngine.reload();
	}

	public void goHomeWebView() {
		String url = AppMain.defaultDomain;
		if (!url.contains("http://")) {
			url = "http://"+url;
		}
		widgetBar.root.listOneWebView.get(AppMain.curOneWebViewIdx).webEngine.load(url);
		if(!isSplit)
			return;
		widgetBar.root.listTwoWebView.get(AppMain.curTwoWebViewIdx).webEngine.load(url);
	}

	public void goNextWebView() {
		int oneIdx = widgetBar.root.listOneWebView.get(AppMain.curOneWebViewIdx).history.getCurrentIndex();
		int oneHistorySize = widgetBar.root.listOneWebView.get(AppMain.curOneWebViewIdx).history.getMaxSize();
		if(oneIdx < oneHistorySize){
			widgetBar.root.listOneWebView.get(AppMain.curOneWebViewIdx).history.go(1);
		}
		if(!isSplit)
			return;
		int twoIdx = widgetBar.root.listTwoWebView.get(AppMain.curTwoWebViewIdx).history.getCurrentIndex();
		int twoHistorySize = widgetBar.root.listTwoWebView.get(AppMain.curTwoWebViewIdx).history.getMaxSize();
		if(twoIdx < twoHistorySize){
			widgetBar.root.listTwoWebView.get(AppMain.curTwoWebViewIdx).history.go(1);
		}
	}

	public void goPrevWebView() {
		int oneIdx = widgetBar.root.listOneWebView.get(AppMain.curOneWebViewIdx).history.getCurrentIndex();
		if(oneIdx > 0){
			widgetBar.root.listOneWebView.get(AppMain.curOneWebViewIdx).history.go(-1);
		}
		if(!isSplit)
			return;
		int twoIdx = widgetBar.root.listTwoWebView.get(AppMain.curTwoWebViewIdx).history.getCurrentIndex();
		if(twoIdx > 0){
			widgetBar.root.listTwoWebView.get(AppMain.curTwoWebViewIdx).history.go(-1);
		}
	}
	public static void hideBackOneWebView(){
		for(int i=0; i<AppMain.listOneWebView.size(); ++i){
			if(AppMain.curOneWebViewIdx == i)
				continue;
			AppMain.listOneWebView.get(i).setVisible(false);
		}
	}
	public static void showBackOneWebView(){
		for(int i=0; i<AppMain.listOneWebView.size(); ++i){
			AppMain.listOneWebView.get(i).setVisible(true);
		}
	}
	public static void hideBackTwoWebView(){
		for(int i=0; i<AppMain.listTwoWebView.size(); ++i){
			if(AppMain.curTwoWebViewIdx == i)
				continue;
			AppMain.listTwoWebView.get(i).setVisible(false);
		}
	}
	public static void showBackTwoWebView(){
		for(int i=0; i<AppMain.listTwoWebView.size(); ++i){
			AppMain.listTwoWebView.get(i).setVisible(true);
		}
	}
	public void showBrowingWindow() {
		showBackOneWebView();
		if(widgetBar.root.listOneWebView.get(AppMain.curOneWebViewIdx).getTop() != widgetBar.root.listOneWebView.get(AppMain.curOneWebViewIdx).topScroll){
			widgetBar.root.listOneWebView.get(AppMain.curOneWebViewIdx).getChildren().remove(widgetBar.root.listOneWebView.get(AppMain.curOneWebViewIdx).getTop());
			widgetBar.root.listOneWebView.get(AppMain.curOneWebViewIdx).webViewList();
			hideBackOneWebView();
			widgetBar.root.listOneWebView.get(AppMain.curOneWebViewIdx).setTop(widgetBar.root.listOneWebView.get(AppMain.curOneWebViewIdx).topScroll);
			widgetBar.root.listOneWebView.get(AppMain.curOneWebViewIdx).topScroll.requestFocus();
		}else if(widgetBar.root.listOneWebView.get(AppMain.curOneWebViewIdx).getTop() != null){
			widgetBar.root.listOneWebView.get(AppMain.curOneWebViewIdx).getChildren().remove(widgetBar.root.listOneWebView.get(AppMain.curOneWebViewIdx).getTop());
		}else{
			widgetBar.root.listOneWebView.get(AppMain.curOneWebViewIdx).webViewList();
			hideBackOneWebView();
			widgetBar.root.listOneWebView.get(AppMain.curOneWebViewIdx).setTop(widgetBar.root.listOneWebView.get(AppMain.curOneWebViewIdx).topScroll);
			widgetBar.root.listOneWebView.get(AppMain.curOneWebViewIdx).topScroll.requestFocus();
		}
		if(!isSplit)
			return;
		showBackTwoWebView();
		if(widgetBar.root.listTwoWebView.get(AppMain.curTwoWebViewIdx).getTop() != widgetBar.root.listTwoWebView.get(AppMain.curTwoWebViewIdx).topScroll){
			widgetBar.root.listTwoWebView.get(AppMain.curTwoWebViewIdx).getChildren().remove(widgetBar.root.listTwoWebView.get(AppMain.curTwoWebViewIdx).getTop());
			widgetBar.root.listTwoWebView.get(AppMain.curTwoWebViewIdx).webViewList();
			hideBackTwoWebView();
			widgetBar.root.listTwoWebView.get(AppMain.curTwoWebViewIdx).setTop(widgetBar.root.listTwoWebView.get(AppMain.curTwoWebViewIdx).topScroll);
			widgetBar.root.listTwoWebView.get(AppMain.curTwoWebViewIdx).topScroll.requestFocus();
		}else if(widgetBar.root.listTwoWebView.get(AppMain.curTwoWebViewIdx).getTop() != null){
			widgetBar.root.listTwoWebView.get(AppMain.curTwoWebViewIdx).getChildren().remove(widgetBar.root.listTwoWebView.get(AppMain.curTwoWebViewIdx).getTop());
		}else{
			widgetBar.root.listTwoWebView.get(AppMain.curTwoWebViewIdx).webViewList();
			hideBackTwoWebView();
			widgetBar.root.listTwoWebView.get(AppMain.curTwoWebViewIdx).setTop(widgetBar.root.listTwoWebView.get(AppMain.curTwoWebViewIdx).topScroll);
			widgetBar.root.listTwoWebView.get(AppMain.curTwoWebViewIdx).topScroll.requestFocus();
		}
	}

	public void showInputDomain(){
		showBackOneWebView();
		if(widgetBar.root.listOneWebView.get(AppMain.curOneWebViewIdx).getTop() != widgetBar.root.listOneWebView.get(AppMain.curOneWebViewIdx).topDomain){
			widgetBar.root.listOneWebView.get(AppMain.curOneWebViewIdx).getChildren().remove(widgetBar.root.listOneWebView.get(AppMain.curOneWebViewIdx).getTop());
			widgetBar.root.listOneWebView.get(AppMain.curOneWebViewIdx).setTop(widgetBar.root.listOneWebView.get(AppMain.curOneWebViewIdx).topDomain);
			hideBackOneWebView();
			widgetBar.root.listOneWebView.get(AppMain.curOneWebViewIdx).tfDomain.requestFocus();
		}else if(widgetBar.root.listOneWebView.get(AppMain.curOneWebViewIdx).getTop() != null){
			widgetBar.root.listOneWebView.get(AppMain.curOneWebViewIdx).getChildren().remove(widgetBar.root.listOneWebView.get(AppMain.curOneWebViewIdx).getTop());
			showBackOneWebView();
		}else {
			widgetBar.root.listOneWebView.get(AppMain.curOneWebViewIdx).setTop(widgetBar.root.listOneWebView.get(AppMain.curOneWebViewIdx).topDomain);
			widgetBar.root.listOneWebView.get(AppMain.curOneWebViewIdx).tfDomain.requestFocus();
			hideBackOneWebView();
		}
		if(!isSplit)
			return;
		showBackTwoWebView();
		if(widgetBar.root.listTwoWebView.get(AppMain.curTwoWebViewIdx).getTop() != widgetBar.root.listTwoWebView.get(AppMain.curTwoWebViewIdx).topDomain){
			widgetBar.root.listTwoWebView.get(AppMain.curTwoWebViewIdx).getChildren().remove(widgetBar.root.listTwoWebView.get(AppMain.curTwoWebViewIdx).getTop());
			widgetBar.root.listTwoWebView.get(AppMain.curTwoWebViewIdx).setTop(widgetBar.root.listTwoWebView.get(AppMain.curTwoWebViewIdx).topDomain);
			hideBackTwoWebView();
			widgetBar.root.listTwoWebView.get(AppMain.curTwoWebViewIdx).tfDomain.requestFocus();
		}else if(widgetBar.root.listTwoWebView.get(AppMain.curTwoWebViewIdx).getTop() != null){
			widgetBar.root.listTwoWebView.get(AppMain.curTwoWebViewIdx).getChildren().remove(widgetBar.root.listTwoWebView.get(AppMain.curTwoWebViewIdx).getTop());
			showBackTwoWebView();
		}else {
			widgetBar.root.listTwoWebView.get(AppMain.curTwoWebViewIdx).setTop(widgetBar.root.listTwoWebView.get(AppMain.curTwoWebViewIdx).topDomain);
			widgetBar.root.listTwoWebView.get(AppMain.curTwoWebViewIdx).tfDomain.requestFocus();
			hideBackTwoWebView();
		}
	}
	public void browserMinimize() {
		AppMain.stage.setIconified(true);
	}

	public void browserResize() {
		isClickedResize = !isClickedResize;
		WidgetButton btn = null;
		for(int i=0; i<WidgetBar.listBtn.size(); ++i){
			if(WidgetBar.listBtn.get(i).getId().equals("resize")){
				btn = WidgetBar.listBtn.get(i);
				break;
			}
		}
		if(isClickedResize){
			btn.setStyle("-fx-background-image: url('//res/icons/resize_max.png');");
			AppMain.stage.setWidth(AppMain.DEFAULT_WIDTH);
			AppMain.stage.setHeight(AppMain.DEFAULT_HEIGHT);
		}else{
			btn.setStyle("-fx-background-image: url('//res/icons/resize.png');");
			AppMain.DEFAULT_WIDTH = AppMain.stage.getWidth();
			AppMain.DEFAULT_HEIGHT = AppMain.stage.getHeight();
			AppMain.stage.setX(0);
			AppMain.stage.setY(0);
			AppMain.stage.setWidth(AppMain.width);
			AppMain.stage.setHeight(AppMain.height);
		}
	}

	public void browserClose() {
		Platform.exit();
	    System.exit(0);
	}

	private void WidgetBarMouseRightClicked(MouseEvent event) {
		if(event.getButton() == MouseButton.SECONDARY){
			widgetBarMenu.show(widgetBar, event.getScreenX(), event.getScreenY());
		}else if(event.getButton() == MouseButton.PRIMARY){
			widgetBarMenu.hide();
		}
	}

	private void btnMouseReleased(MouseEvent event) {
		if(isEdit == false)
			return;
		if(event.getButton() == MouseButton.SECONDARY)
			return;
		
		WidgetButton btn = (WidgetButton)event.getSource();
		if(!btn.isBigger()){
			double btnX = btn.getLayoutX();
			double btnY = btn.getLayoutY();
			double minDist = Double.MAX_VALUE;
			WidgetRect targetRect = null;
			WidgetButton targetBtn = null;

			for (int i = 0; i < WidgetBar.listRect.size(); ++i) {
				WidgetRect rect = WidgetBar.listRect.get(i);
				double rectX = rect.getLayoutX();
				double rectY = rect.getLayoutY();
				double dist = Math.sqrt(Math.pow(rectX - btnX, 2.0) + Math.pow(rectY - btnY, 2.0));
				if (minDist > dist) {
					minDist = dist;
					targetRect = rect;
				}
			}
			for (int i = 0; i < listBtn.size(); ++i) {
				if (listBtn.get(i).getRow() == targetRect.getRow() && listBtn.get(i).getCol() == targetRect.getCol()) {
					targetBtn = listBtn.get(i);
				}
			}
			if (prevRect == null) {
				return;
			}
			
			if (targetRect.getIsFill()) {
				btn.setLayoutX(targetRect.getLayoutX());
				btn.setLayoutY(targetRect.getLayoutY());
				btn.setRow(targetRect.getRow());
				btn.setCol(targetRect.getCol());
				targetBtn.setLayoutX(prevRect.getLayoutX());
				targetBtn.setLayoutY(prevRect.getLayoutY());
				targetBtn.setRow(prevRect.getRow());
				targetBtn.setCol(prevRect.getCol());
				prevRect = null;
			} else {
				btn.setLayoutX(targetRect.getLayoutX());
				btn.setLayoutY(targetRect.getLayoutY());
				btn.setRow(targetRect.getRow());
				btn.setCol(targetRect.getCol());
				targetRect.setIsFill(true);
				prevRect.setIsFill(false);
				prevRect = null;
			}
		}else{
			double btnX = btn.getLayoutX();
			double btnY = btn.getLayoutY();
			double minDist = Double.MAX_VALUE;
			WidgetRect targetRect = null;
			int idxPrevRect = Integer.parseInt(prevRect.getId());

			for (int i = 0; i < widgetBar.numOfRect; ++i) {
				WidgetRect rect = WidgetBar.listRect.get(i);
				double rectX = rect.getLayoutX();
				double rectY = rect.getLayoutY();
				double dist = Math.sqrt(Math.pow(rectX - btnX, 2.0) + Math.pow(rectY - btnY, 2.0));
				if (minDist > dist) {
					minDist = dist;
					targetRect = rect;
				}
			}
			WidgetRect[] nearRect = new WidgetRect[4];
			int idxTargetRect = Integer.parseInt(targetRect.getId());
			nearRect[0] = WidgetBar.listRect.get(idxTargetRect);
			nearRect[1] = WidgetBar.listRect.get(idxTargetRect+1);
			nearRect[2] = WidgetBar.listRect.get(idxTargetRect+widgetBar.numOfRect);
			nearRect[3] = WidgetBar.listRect.get(idxTargetRect+widgetBar.numOfRect+1);
			
			ArrayList<WidgetButton> nearBtn = new ArrayList<WidgetButton>();
			
			for (int i = 0; i < listBtn.size(); ++i) {
				if(listBtn.get(i).equals(btn))
					continue;
				for(int j=0; j<nearRect.length; ++j){
					if (listBtn.get(i).getRow() == nearRect[j].getRow() && listBtn.get(i).getCol() == nearRect[j].getCol()) {
						nearBtn.add(listBtn.get(i));
					}
				}
			}
			if(nearBtn.size() == 0){
				for(int i=0; i<nearRect.length; ++i){
					nearRect[i].setIsFill(true);
				}
				btn.setRow(nearRect[0].getRow());
				btn.setCol(nearRect[0].getCol());
				btn.setLayoutX(nearRect[0].getLayoutX());
				btn.setLayoutY(nearRect[0].getLayoutY());
				WidgetBar.listRect.get(idxPrevRect).setIsFill(false);
				WidgetBar.listRect.get(idxPrevRect+1).setIsFill(false);
				WidgetBar.listRect.get(idxPrevRect+widgetBar.numOfRect).setIsFill(false);
				WidgetBar.listRect.get(idxPrevRect+widgetBar.numOfRect+1).setIsFill(false);
				prevRect = null;
			}else{
				for(int i=0; i<nearRect.length; ++i){
					nearRect[i].setIsFill(true);
				}
				btn.setRow(nearRect[0].getRow());
				btn.setCol(nearRect[0].getCol());
				btn.setLayoutX(nearRect[0].getLayoutX());
				btn.setLayoutY(nearRect[0].getLayoutY());
				WidgetBar.listRect.get(idxPrevRect).setIsFill(false);
				WidgetBar.listRect.get(idxPrevRect+1).setIsFill(false);
				WidgetBar.listRect.get(idxPrevRect+widgetBar.numOfRect).setIsFill(false);
				WidgetBar.listRect.get(idxPrevRect+widgetBar.numOfRect+1).setIsFill(false);
				
				for(int i=0; i<nearBtn.size(); ++i){
					if(i == 0){
						if(idxPrevRect- idxTargetRect == 1){
							WidgetBar.listRect.get(idxPrevRect+1).setIsFill(true);
							nearBtn.get(i).setRow(WidgetBar.listRect.get(idxPrevRect+1).getRow());
							nearBtn.get(i).setCol(WidgetBar.listRect.get(idxPrevRect+1).getCol());
							nearBtn.get(i).setLayoutX(WidgetBar.listRect.get(idxPrevRect+1).getLayoutX());
							nearBtn.get(i).setLayoutY(WidgetBar.listRect.get(idxPrevRect+1).getLayoutY());
						}else{
							WidgetBar.listRect.get(idxPrevRect).setIsFill(true);
							nearBtn.get(i).setRow(WidgetBar.listRect.get(idxPrevRect).getRow());
							nearBtn.get(i).setCol(WidgetBar.listRect.get(idxPrevRect).getCol());
							nearBtn.get(i).setLayoutX(WidgetBar.listRect.get(idxPrevRect).getLayoutX());
							nearBtn.get(i).setLayoutY(WidgetBar.listRect.get(idxPrevRect).getLayoutY());
						}
					}else if(i == 1){
						WidgetBar.listRect.get(idxPrevRect+1).setIsFill(true);
						nearBtn.get(i).setRow(WidgetBar.listRect.get(idxPrevRect+1).getRow());
						nearBtn.get(i).setCol(WidgetBar.listRect.get(idxPrevRect+1).getCol());
						nearBtn.get(i).setLayoutX(WidgetBar.listRect.get(idxPrevRect+1).getLayoutX());
						nearBtn.get(i).setLayoutY(WidgetBar.listRect.get(idxPrevRect+1).getLayoutY());
					}else if(i == 2){
						WidgetBar.listRect.get(idxPrevRect+widgetBar.numOfRect).setIsFill(true);
						nearBtn.get(i).setRow(WidgetBar.listRect.get(idxPrevRect+widgetBar.numOfRect).getRow());
						nearBtn.get(i).setCol(WidgetBar.listRect.get(idxPrevRect+widgetBar.numOfRect).getCol());
						nearBtn.get(i).setLayoutX(WidgetBar.listRect.get(idxPrevRect+widgetBar.numOfRect).getLayoutX());
						nearBtn.get(i).setLayoutY(WidgetBar.listRect.get(idxPrevRect+widgetBar.numOfRect).getLayoutY());
					}else{
						WidgetBar.listRect.get(idxPrevRect+widgetBar.numOfRect+1).setIsFill(true);
						nearBtn.get(i).setRow(WidgetBar.listRect.get(idxPrevRect+widgetBar.numOfRect+1).getRow());
						nearBtn.get(i).setCol(WidgetBar.listRect.get(idxPrevRect+widgetBar.numOfRect+1).getCol());
						nearBtn.get(i).setLayoutX(WidgetBar.listRect.get(idxPrevRect+widgetBar.numOfRect+1).getLayoutX());
						nearBtn.get(i).setLayoutY(WidgetBar.listRect.get(idxPrevRect+widgetBar.numOfRect+1).getLayoutY());
					}
				}
				prevRect = null;
			}
		}
		
	}

	private void btnMouseDragged(MouseEvent event) {
		if(isEdit == false)
			return;
		if(event.getButton() == MouseButton.SECONDARY)
			return;
		
		WidgetButton btn = (WidgetButton)event.getSource();
		if (!btn.isBigger()) {
			int row = btn.getRow();
			int col = btn.getCol();
			if (prevRect == null) {
				for (int i = 0; i < WidgetBar.listRect.size(); ++i) {
					if (WidgetBar.listRect.get(i).getRow() == row && WidgetBar.listRect.get(i).getCol() == col) {
						prevRect = WidgetBar.listRect.get(i);
					}
				}
			}
			btn.toFront();
			if (event.getSceneY() + (btn.getHeight() / 2) >= widgetBar.height) {
				btn.setLayoutY(widgetBar.height - btn.getSize());
			} else {
				btn.setLayoutY(event.getSceneY() - (btn.getHeight() / 2));
			}
			btn.setLayoutX(event.getSceneX() - (btn.getWidth() / 2));
		}else{
			int row = btn.getRow();
			int col = btn.getCol();
			if (prevRect == null) {
				for (int i = 0; i < WidgetBar.listRect.size(); ++i) {
					if (WidgetBar.listRect.get(i).getRow() == row && WidgetBar.listRect.get(i).getCol() == col) {
						prevRect = WidgetBar.listRect.get(i);
					}
				}
			}
			btn.toFront();
			if (event.getSceneY() + (btn.getHeight() / 2) >= widgetBar.height) {
				btn.setLayoutY(widgetBar.height - btn.getSize());
			} else {
				btn.setLayoutY(event.getSceneY() - (btn.getHeight() / 2));
			}
			btn.setLayoutX(event.getSceneX() - (btn.getWidth() / 2));
		}
	}
	
	private void widgetBarMenuHandler(ActionEvent event) {
		Object obj = event.getSource();
		if(obj.equals(widgetBarMenuItem[0])){
			if(isEdit == false){
				isEdit = true;
				startEditWidgetBar();
			}else{
				isEdit = false;
				endEditWidgetBar();
			}
		}else if(obj.equals(widgetBarMenuItem[1])){
			isSettingView = !isSettingView;
			if(isSettingView){
				AppMain.centerWrapper.getChildren().add(settings);
				widgetBarMenuItem[1].setDisable(true);
			}
		}
	}
	private void endEditWidgetBar() {
		widgetBarMenuItem[0].setText("편집");
		for(int i=0; i<WidgetBar.listRect.size(); ++i){
			WidgetBar.listRect.get(i).setStyle("-fx-stroke-dash-array: 2 3 2 3; -fx-stroke-width: 0; -fx-stroke: black;"); 
			WidgetBar.listRect.get(i).setStroke(Color.TRANSPARENT);
		}
	}

	private void startEditWidgetBar(){
		widgetBarMenuItem[0].setText("편집 종료");
		for(int i=0; i<WidgetBar.listRect.size(); ++i){
			WidgetBar.listRect.get(i).setStyle("-fx-stroke-dash-array: 2 3 2 3; -fx-stroke-width: 0.8; -fx-stroke: black;"); 
		}
	}
}
