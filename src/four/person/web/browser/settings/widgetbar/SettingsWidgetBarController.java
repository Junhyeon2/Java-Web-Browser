package four.person.web.browser.settings.widgetbar;

import java.net.URL;
import java.util.ResourceBundle;

import four.person.web.browser.main.AppMain;
import four.person.web.browser.widgetbar.WidgetBar;
import four.person.web.browser.widgetbar.WidgetBarController;
import four.person.web.browser.widgetbar.WidgetButton;
import four.person.web.browser.widgetbar.WidgetRect;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;

public class SettingsWidgetBarController implements Initializable{
	@FXML RadioButton radBtnMinHeigth, radBtnMaxHeigth;
	@FXML Button btnApply;
	@FXML ColorPicker colorPicker;
	@FXML CheckBox close, resize, minimize, split, add, domain, home, music, prev, next, refresh, weather, pin, capture, windows;
	ToggleGroup tgWidgetBarHeight;
	int widgetHeight;
	boolean isClose, isResize, isMinimize, isSplit, isAdd, isDomain, isHome, isMusic, isPrev, isNext, isRefresh, isWeather, isPin, isCapture, isWindows;
	public SettingsWidgetBarController() {
		tgWidgetBarHeight = new ToggleGroup();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		radBtnMinHeigth.setToggleGroup(tgWidgetBarHeight);
		radBtnMinHeigth.setUserData(1);
		radBtnMaxHeigth.setToggleGroup(tgWidgetBarHeight);
		radBtnMaxHeigth.setUserData(2);
		
		tgWidgetBarHeight.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				widgetHeight = Integer.parseInt(newValue.getUserData().toString());
			}
		});
		btnApply.setOnAction(event->changeWidgetBarHeight(event));
		
		if(AppMain.WidgetHeight == 1){
			tgWidgetBarHeight.selectToggle(radBtnMinHeigth);
		}else{
			tgWidgetBarHeight.selectToggle(radBtnMaxHeigth);
		}
		colorPicker.setOnAction(event->WidgetBarColorChange(event));
		colorPicker.setValue(Color.WHITE);
		
		close.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(newValue == true){
					WidgetButton btn = null;
					for(int i=0; i<WidgetBar.listBtn.size(); ++i){
						if(WidgetBar.listBtn.get(i).getId().equals("close")){
							btn = WidgetBar.listBtn.get(i);
							break;
						}
					}
					if(WidgetBar.widgetBar.getChildren().contains(btn))
						return;
					else{
						for(int i=0; i<WidgetBar.listRect.size(); ++i){
							WidgetRect targetRect = WidgetBar.listRect.get(i);
							if(!targetRect.getIsFill()){
								targetRect.setIsFill(true);
								btn.setLayoutX(targetRect.getLayoutX());
								btn.setLayoutY(targetRect.getLayoutY());
								btn.setRow(targetRect.getRow());
								btn.setCol(targetRect.getCol());
								break;
							}
						}
						WidgetBar.widgetBar.getChildren().add(btn);
					}
				}else{
					WidgetButton btn = null;
					for(int i=0; i<WidgetBar.listBtn.size(); ++i){
						if(WidgetBar.listBtn.get(i).getId().equals("close")){
							btn = WidgetBar.listBtn.get(i);
							break;
						}
					}
					int row = btn.getRow();
					int col = btn.getCol();
					WidgetRect targetRect = null;
					for(int i=0; i<WidgetBar.listRect.size(); ++i){
						if(row == WidgetBar.listRect.get(i).getRow() && col == WidgetBar.listRect.get(i).getCol()){
							targetRect = WidgetBar.listRect.get(i);
						}
					}
					int idxTargetRect = Integer.parseInt(targetRect.getId());
					if(btn.isBigger()){
						WidgetRect[] nearRect = new WidgetRect[4];
						nearRect[0] = WidgetBar.listRect.get(idxTargetRect);
						nearRect[1] = WidgetBar.listRect.get(idxTargetRect+1);
						nearRect[2] = WidgetBar.listRect.get(idxTargetRect+WidgetBar.numOfRect);
						nearRect[3] = WidgetBar.listRect.get(idxTargetRect+WidgetBar.numOfRect+1);		
						nearRect[0].setIsFill(false);
						nearRect[1].setIsFill(false);
						nearRect[2].setIsFill(false);
						nearRect[3].setIsFill(false);
					}else{
						WidgetBar.listRect.get(idxTargetRect).setIsFill(false);
					}
					btn.setRow(0);
					btn.setCol(0);
					WidgetBar.widgetBar.getChildren().remove(btn);
				}
			}
		});
		resize.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(newValue == true){
					WidgetButton btn = null;
					for(int i=0; i<WidgetBar.listBtn.size(); ++i){
						if(WidgetBar.listBtn.get(i).getId().equals("resize")){
							btn = WidgetBar.listBtn.get(i);
							break;
						}
					}
					if(WidgetBar.widgetBar.getChildren().contains(btn))
						return;
					else{
						for(int i=0; i<WidgetBar.listRect.size(); ++i){
							WidgetRect targetRect = WidgetBar.listRect.get(i);
							if(!targetRect.getIsFill()){
								targetRect.setIsFill(true);
								btn.setLayoutX(targetRect.getLayoutX());
								btn.setLayoutY(targetRect.getLayoutY());
								btn.setRow(targetRect.getRow());
								btn.setCol(targetRect.getCol());
								break;
							}
						}
						WidgetBar.widgetBar.getChildren().add(btn);
					}
				}else{
					WidgetButton btn = null;
					for(int i=0; i<WidgetBar.listBtn.size(); ++i){
						if(WidgetBar.listBtn.get(i).getId().equals("resize")){
							btn = WidgetBar.listBtn.get(i);
							break;
						}
					}
					int row = btn.getRow();
					int col = btn.getCol();
					WidgetRect targetRect = null;
					for(int i=0; i<WidgetBar.listRect.size(); ++i){
						if(row == WidgetBar.listRect.get(i).getRow() && col == WidgetBar.listRect.get(i).getCol()){
							targetRect = WidgetBar.listRect.get(i);
						}
					}
					int idxTargetRect = Integer.parseInt(targetRect.getId());
					if(btn.isBigger()){
						WidgetRect[] nearRect = new WidgetRect[4];
						nearRect[0] = WidgetBar.listRect.get(idxTargetRect);
						nearRect[1] = WidgetBar.listRect.get(idxTargetRect+1);
						nearRect[2] = WidgetBar.listRect.get(idxTargetRect+WidgetBar.numOfRect);
						nearRect[3] = WidgetBar.listRect.get(idxTargetRect+WidgetBar.numOfRect+1);		
						nearRect[0].setIsFill(false);
						nearRect[1].setIsFill(false);
						nearRect[2].setIsFill(false);
						nearRect[3].setIsFill(false);
					}else{
						WidgetBar.listRect.get(idxTargetRect).setIsFill(false);
					}
					btn.setRow(0);
					btn.setCol(0);
					WidgetBar.widgetBar.getChildren().remove(btn);
				}
			}
		});
		minimize.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(newValue == true){
					WidgetButton btn = null;
					for(int i=0; i<WidgetBar.listBtn.size(); ++i){
						if(WidgetBar.listBtn.get(i).getId().equals("minimize")){
							btn = WidgetBar.listBtn.get(i);
							break;
						}
					}
					if(WidgetBar.widgetBar.getChildren().contains(btn))
						return;
					else{
						for(int i=0; i<WidgetBar.listRect.size(); ++i){
							WidgetRect targetRect = WidgetBar.listRect.get(i);
							if(!targetRect.getIsFill()){
								targetRect.setIsFill(true);
								btn.setLayoutX(targetRect.getLayoutX());
								btn.setLayoutY(targetRect.getLayoutY());
								btn.setRow(targetRect.getRow());
								btn.setCol(targetRect.getCol());
								break;
							}
						}
						WidgetBar.widgetBar.getChildren().add(btn);
					}
				}else{
					WidgetButton btn = null;
					for(int i=0; i<WidgetBar.listBtn.size(); ++i){
						if(WidgetBar.listBtn.get(i).getId().equals("minimize")){
							btn = WidgetBar.listBtn.get(i);
							break;
						}
					}
					int row = btn.getRow();
					int col = btn.getCol();
					WidgetRect targetRect = null;
					for(int i=0; i<WidgetBar.listRect.size(); ++i){
						if(row == WidgetBar.listRect.get(i).getRow() && col == WidgetBar.listRect.get(i).getCol()){
							targetRect = WidgetBar.listRect.get(i);
						}
					}
					int idxTargetRect = Integer.parseInt(targetRect.getId());
					if(btn.isBigger()){
						WidgetRect[] nearRect = new WidgetRect[4];
						nearRect[0] = WidgetBar.listRect.get(idxTargetRect);
						nearRect[1] = WidgetBar.listRect.get(idxTargetRect+1);
						nearRect[2] = WidgetBar.listRect.get(idxTargetRect+WidgetBar.numOfRect);
						nearRect[3] = WidgetBar.listRect.get(idxTargetRect+WidgetBar.numOfRect+1);		
						nearRect[0].setIsFill(false);
						nearRect[1].setIsFill(false);
						nearRect[2].setIsFill(false);
						nearRect[3].setIsFill(false);
					}else{
						WidgetBar.listRect.get(idxTargetRect).setIsFill(false);
					}
					btn.setRow(0);
					btn.setCol(0);
					WidgetBar.widgetBar.getChildren().remove(btn);
				}
			}
		});
		split.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(newValue == true){
					WidgetButton btn = null;
					for(int i=0; i<WidgetBar.listBtn.size(); ++i){
						if(WidgetBar.listBtn.get(i).getId().equals("split")){
							btn = WidgetBar.listBtn.get(i);
							break;
						}
					}
					if(WidgetBar.widgetBar.getChildren().contains(btn))
						return;
					else{
						for(int i=0; i<WidgetBar.listRect.size(); ++i){
							WidgetRect targetRect = WidgetBar.listRect.get(i);
							if(!targetRect.getIsFill()){
								targetRect.setIsFill(true);
								btn.setLayoutX(targetRect.getLayoutX());
								btn.setLayoutY(targetRect.getLayoutY());
								btn.setRow(targetRect.getRow());
								btn.setCol(targetRect.getCol());
								break;
							}
						}
						WidgetBar.widgetBar.getChildren().add(btn);
					}
				}else{
					WidgetButton btn = null;
					for(int i=0; i<WidgetBar.listBtn.size(); ++i){
						if(WidgetBar.listBtn.get(i).getId().equals("split")){
							btn = WidgetBar.listBtn.get(i);
							break;
						}
					}
					int row = btn.getRow();
					int col = btn.getCol();
					WidgetRect targetRect = null;
					for(int i=0; i<WidgetBar.listRect.size(); ++i){
						if(row == WidgetBar.listRect.get(i).getRow() && col == WidgetBar.listRect.get(i).getCol()){
							targetRect = WidgetBar.listRect.get(i);
						}
					}
					int idxTargetRect = Integer.parseInt(targetRect.getId());
					if(btn.isBigger()){
						WidgetRect[] nearRect = new WidgetRect[4];
						nearRect[0] = WidgetBar.listRect.get(idxTargetRect);
						nearRect[1] = WidgetBar.listRect.get(idxTargetRect+1);
						nearRect[2] = WidgetBar.listRect.get(idxTargetRect+WidgetBar.numOfRect);
						nearRect[3] = WidgetBar.listRect.get(idxTargetRect+WidgetBar.numOfRect+1);		
						nearRect[0].setIsFill(false);
						nearRect[1].setIsFill(false);
						nearRect[2].setIsFill(false);
						nearRect[3].setIsFill(false);
					}else{
						WidgetBar.listRect.get(idxTargetRect).setIsFill(false);
					}
					btn.setRow(0);
					btn.setCol(0);
					WidgetBar.widgetBar.getChildren().remove(btn);
				}
			}
		});
		add.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(newValue == true){
					WidgetButton btn = null;
					for(int i=0; i<WidgetBar.listBtn.size(); ++i){
						if(WidgetBar.listBtn.get(i).getId().equals("add")){
							btn = WidgetBar.listBtn.get(i);
							break;
						}
					}
					if(WidgetBar.widgetBar.getChildren().contains(btn))
						return;
					else{
						for(int i=0; i<WidgetBar.listRect.size(); ++i){
							WidgetRect targetRect = WidgetBar.listRect.get(i);
							if(!targetRect.getIsFill()){
								targetRect.setIsFill(true);
								btn.setLayoutX(targetRect.getLayoutX());
								btn.setLayoutY(targetRect.getLayoutY());
								btn.setRow(targetRect.getRow());
								btn.setCol(targetRect.getCol());
								break;
							}
						}
						WidgetBar.widgetBar.getChildren().add(btn);
					}
				}else{
					WidgetButton btn = null;
					for(int i=0; i<WidgetBar.listBtn.size(); ++i){
						if(WidgetBar.listBtn.get(i).getId().equals("add")){
							btn = WidgetBar.listBtn.get(i);
							break;
						}
					}
					int row = btn.getRow();
					int col = btn.getCol();
					WidgetRect targetRect = null;
					for(int i=0; i<WidgetBar.listRect.size(); ++i){
						if(row == WidgetBar.listRect.get(i).getRow() && col == WidgetBar.listRect.get(i).getCol()){
							targetRect = WidgetBar.listRect.get(i);
						}
					}
					int idxTargetRect = Integer.parseInt(targetRect.getId());
					if(btn.isBigger()){
						WidgetRect[] nearRect = new WidgetRect[4];
						nearRect[0] = WidgetBar.listRect.get(idxTargetRect);
						nearRect[1] = WidgetBar.listRect.get(idxTargetRect+1);
						nearRect[2] = WidgetBar.listRect.get(idxTargetRect+WidgetBar.numOfRect);
						nearRect[3] = WidgetBar.listRect.get(idxTargetRect+WidgetBar.numOfRect+1);		
						nearRect[0].setIsFill(false);
						nearRect[1].setIsFill(false);
						nearRect[2].setIsFill(false);
						nearRect[3].setIsFill(false);
					}else{
						WidgetBar.listRect.get(idxTargetRect).setIsFill(false);
					}
					btn.setRow(0);
					btn.setCol(0);
					WidgetBar.widgetBar.getChildren().remove(btn);
				}
			}
		});
		domain.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(newValue == true){
					WidgetButton btn = null;
					for(int i=0; i<WidgetBar.listBtn.size(); ++i){
						if(WidgetBar.listBtn.get(i).getId().equals("domain")){
							btn = WidgetBar.listBtn.get(i);
							break;
						}
					}
					if(WidgetBar.widgetBar.getChildren().contains(btn))
						return;
					else{
						for(int i=0; i<WidgetBar.listRect.size(); ++i){
							WidgetRect targetRect = WidgetBar.listRect.get(i);
							if(!targetRect.getIsFill()){
								targetRect.setIsFill(true);
								btn.setLayoutX(targetRect.getLayoutX());
								btn.setLayoutY(targetRect.getLayoutY());
								btn.setRow(targetRect.getRow());
								btn.setCol(targetRect.getCol());
								break;
							}
						}
						WidgetBar.widgetBar.getChildren().add(btn);
					}
				}else{
					WidgetButton btn = null;
					for(int i=0; i<WidgetBar.listBtn.size(); ++i){
						if(WidgetBar.listBtn.get(i).getId().equals("domain")){
							btn = WidgetBar.listBtn.get(i);
							break;
						}
					}
					int row = btn.getRow();
					int col = btn.getCol();
					WidgetRect targetRect = null;
					for(int i=0; i<WidgetBar.listRect.size(); ++i){
						if(row == WidgetBar.listRect.get(i).getRow() && col == WidgetBar.listRect.get(i).getCol()){
							targetRect = WidgetBar.listRect.get(i);
						}
					}
					int idxTargetRect = Integer.parseInt(targetRect.getId());
					if(btn.isBigger()){
						WidgetRect[] nearRect = new WidgetRect[4];
						nearRect[0] = WidgetBar.listRect.get(idxTargetRect);
						nearRect[1] = WidgetBar.listRect.get(idxTargetRect+1);
						nearRect[2] = WidgetBar.listRect.get(idxTargetRect+WidgetBar.numOfRect);
						nearRect[3] = WidgetBar.listRect.get(idxTargetRect+WidgetBar.numOfRect+1);		
						nearRect[0].setIsFill(false);
						nearRect[1].setIsFill(false);
						nearRect[2].setIsFill(false);
						nearRect[3].setIsFill(false);
					}else{
						WidgetBar.listRect.get(idxTargetRect).setIsFill(false);
					}
					btn.setRow(0);
					btn.setCol(0);
					WidgetBar.widgetBar.getChildren().remove(btn);
				}
			}
		});
		home.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(newValue == true){
					WidgetButton btn = null;
					for(int i=0; i<WidgetBar.listBtn.size(); ++i){
						if(WidgetBar.listBtn.get(i).getId().equals("home")){
							btn = WidgetBar.listBtn.get(i);
							break;
						}
					}
					if(WidgetBar.widgetBar.getChildren().contains(btn))
						return;
					else{
						for(int i=0; i<WidgetBar.listRect.size(); ++i){
							WidgetRect targetRect = WidgetBar.listRect.get(i);
							if(!targetRect.getIsFill()){
								targetRect.setIsFill(true);
								btn.setLayoutX(targetRect.getLayoutX());
								btn.setLayoutY(targetRect.getLayoutY());
								btn.setRow(targetRect.getRow());
								btn.setCol(targetRect.getCol());
								break;
							}
						}
						WidgetBar.widgetBar.getChildren().add(btn);
					}
				}else{
					WidgetButton btn = null;
					for(int i=0; i<WidgetBar.listBtn.size(); ++i){
						if(WidgetBar.listBtn.get(i).getId().equals("home")){
							btn = WidgetBar.listBtn.get(i);
							break;
						}
					}
					int row = btn.getRow();
					int col = btn.getCol();
					WidgetRect targetRect = null;
					for(int i=0; i<WidgetBar.listRect.size(); ++i){
						if(row == WidgetBar.listRect.get(i).getRow() && col == WidgetBar.listRect.get(i).getCol()){
							targetRect = WidgetBar.listRect.get(i);
						}
					}
					int idxTargetRect = Integer.parseInt(targetRect.getId());
					if(btn.isBigger()){
						WidgetRect[] nearRect = new WidgetRect[4];
						nearRect[0] = WidgetBar.listRect.get(idxTargetRect);
						nearRect[1] = WidgetBar.listRect.get(idxTargetRect+1);
						nearRect[2] = WidgetBar.listRect.get(idxTargetRect+WidgetBar.numOfRect);
						nearRect[3] = WidgetBar.listRect.get(idxTargetRect+WidgetBar.numOfRect+1);		
						nearRect[0].setIsFill(false);
						nearRect[1].setIsFill(false);
						nearRect[2].setIsFill(false);
						nearRect[3].setIsFill(false);
					}else{
						WidgetBar.listRect.get(idxTargetRect).setIsFill(false);
					}
					btn.setRow(0);
					btn.setCol(0);
					WidgetBar.widgetBar.getChildren().remove(btn);
				}
			}
		});
		music.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(newValue == true){
					WidgetButton btn = null;
					for(int i=0; i<WidgetBar.listBtn.size(); ++i){
						if(WidgetBar.listBtn.get(i).getId().equals("music")){
							btn = WidgetBar.listBtn.get(i);
							break;
						}
					}
					if(WidgetBar.widgetBar.getChildren().contains(btn))
						return;
					else{
						for(int i=0; i<WidgetBar.listRect.size(); ++i){
							WidgetRect targetRect = WidgetBar.listRect.get(i);
							if(!targetRect.getIsFill()){
								targetRect.setIsFill(true);
								btn.setLayoutX(targetRect.getLayoutX());
								btn.setLayoutY(targetRect.getLayoutY());
								btn.setRow(targetRect.getRow());
								btn.setCol(targetRect.getCol());
								break;
							}
						}
						WidgetBar.widgetBar.getChildren().add(btn);
					}
				}else{
					WidgetButton btn = null;
					for(int i=0; i<WidgetBar.listBtn.size(); ++i){
						if(WidgetBar.listBtn.get(i).getId().equals("music")){
							btn = WidgetBar.listBtn.get(i);
							break;
						}
					}
					int row = btn.getRow();
					int col = btn.getCol();
					WidgetRect targetRect = null;
					for(int i=0; i<WidgetBar.listRect.size(); ++i){
						if(row == WidgetBar.listRect.get(i).getRow() && col == WidgetBar.listRect.get(i).getCol()){
							targetRect = WidgetBar.listRect.get(i);
						}
					}
					int idxTargetRect = Integer.parseInt(targetRect.getId());
					if(btn.isBigger()){
						WidgetRect[] nearRect = new WidgetRect[4];
						nearRect[0] = WidgetBar.listRect.get(idxTargetRect);
						nearRect[1] = WidgetBar.listRect.get(idxTargetRect+1);
						nearRect[2] = WidgetBar.listRect.get(idxTargetRect+WidgetBar.numOfRect);
						nearRect[3] = WidgetBar.listRect.get(idxTargetRect+WidgetBar.numOfRect+1);		
						nearRect[0].setIsFill(false);
						nearRect[1].setIsFill(false);
						nearRect[2].setIsFill(false);
						nearRect[3].setIsFill(false);
					}else{
						WidgetBar.listRect.get(idxTargetRect).setIsFill(false);
					}
					btn.setRow(0);
					btn.setCol(0);
					WidgetBar.widgetBar.getChildren().remove(btn);
				}
			}
		});
		prev.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(newValue == true){
					WidgetButton btn = null;
					for(int i=0; i<WidgetBar.listBtn.size(); ++i){
						if(WidgetBar.listBtn.get(i).getId().equals("prev")){
							btn = WidgetBar.listBtn.get(i);
							break;
						}
					}
					if(WidgetBar.widgetBar.getChildren().contains(btn))
						return;
					else{
						for(int i=0; i<WidgetBar.listRect.size(); ++i){
							WidgetRect targetRect = WidgetBar.listRect.get(i);
							if(!targetRect.getIsFill()){
								targetRect.setIsFill(true);
								btn.setLayoutX(targetRect.getLayoutX());
								btn.setLayoutY(targetRect.getLayoutY());
								btn.setRow(targetRect.getRow());
								btn.setCol(targetRect.getCol());
								break;
							}
						}
						WidgetBar.widgetBar.getChildren().add(btn);
					}
				}else{
					WidgetButton btn = null;
					for(int i=0; i<WidgetBar.listBtn.size(); ++i){
						if(WidgetBar.listBtn.get(i).getId().equals("prev")){
							btn = WidgetBar.listBtn.get(i);
							break;
						}
					}
					int row = btn.getRow();
					int col = btn.getCol();
					WidgetRect targetRect = null;
					for(int i=0; i<WidgetBar.listRect.size(); ++i){
						if(row == WidgetBar.listRect.get(i).getRow() && col == WidgetBar.listRect.get(i).getCol()){
							targetRect = WidgetBar.listRect.get(i);
						}
					}
					int idxTargetRect = Integer.parseInt(targetRect.getId());
					if(btn.isBigger()){
						WidgetRect[] nearRect = new WidgetRect[4];
						nearRect[0] = WidgetBar.listRect.get(idxTargetRect);
						nearRect[1] = WidgetBar.listRect.get(idxTargetRect+1);
						nearRect[2] = WidgetBar.listRect.get(idxTargetRect+WidgetBar.numOfRect);
						nearRect[3] = WidgetBar.listRect.get(idxTargetRect+WidgetBar.numOfRect+1);		
						nearRect[0].setIsFill(false);
						nearRect[1].setIsFill(false);
						nearRect[2].setIsFill(false);
						nearRect[3].setIsFill(false);
					}else{
						WidgetBar.listRect.get(idxTargetRect).setIsFill(false);
					}
					btn.setRow(0);
					btn.setCol(0);
					WidgetBar.widgetBar.getChildren().remove(btn);
				}
			}
		});
		next.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(newValue == true){
					WidgetButton btn = null;
					for(int i=0; i<WidgetBar.listBtn.size(); ++i){
						if(WidgetBar.listBtn.get(i).getId().equals("next")){
							btn = WidgetBar.listBtn.get(i);
							break;
						}
					}
					if(WidgetBar.widgetBar.getChildren().contains(btn))
						return;
					else{
						for(int i=0; i<WidgetBar.listRect.size(); ++i){
							WidgetRect targetRect = WidgetBar.listRect.get(i);
							if(!targetRect.getIsFill()){
								targetRect.setIsFill(true);
								btn.setLayoutX(targetRect.getLayoutX());
								btn.setLayoutY(targetRect.getLayoutY());
								btn.setRow(targetRect.getRow());
								btn.setCol(targetRect.getCol());
								break;
							}
						}
						WidgetBar.widgetBar.getChildren().add(btn);
					}
				}else{
					WidgetButton btn = null;
					for(int i=0; i<WidgetBar.listBtn.size(); ++i){
						if(WidgetBar.listBtn.get(i).getId().equals("next")){
							btn = WidgetBar.listBtn.get(i);
							break;
						}
					}
					int row = btn.getRow();
					int col = btn.getCol();
					WidgetRect targetRect = null;
					for(int i=0; i<WidgetBar.listRect.size(); ++i){
						if(row == WidgetBar.listRect.get(i).getRow() && col == WidgetBar.listRect.get(i).getCol()){
							targetRect = WidgetBar.listRect.get(i);
						}
					}
					int idxTargetRect = Integer.parseInt(targetRect.getId());
					if(btn.isBigger()){
						WidgetRect[] nearRect = new WidgetRect[4];
						nearRect[0] = WidgetBar.listRect.get(idxTargetRect);
						nearRect[1] = WidgetBar.listRect.get(idxTargetRect+1);
						nearRect[2] = WidgetBar.listRect.get(idxTargetRect+WidgetBar.numOfRect);
						nearRect[3] = WidgetBar.listRect.get(idxTargetRect+WidgetBar.numOfRect+1);		
						nearRect[0].setIsFill(false);
						nearRect[1].setIsFill(false);
						nearRect[2].setIsFill(false);
						nearRect[3].setIsFill(false);
					}else{
						WidgetBar.listRect.get(idxTargetRect).setIsFill(false);
					}
					btn.setRow(0);
					btn.setCol(0);
					WidgetBar.widgetBar.getChildren().remove(btn);
				}
			}
		});
		refresh.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(newValue == true){
					WidgetButton btn = null;
					for(int i=0; i<WidgetBar.listBtn.size(); ++i){
						if(WidgetBar.listBtn.get(i).getId().equals("refresh")){
							btn = WidgetBar.listBtn.get(i);
							break;
						}
					}
					if(WidgetBar.widgetBar.getChildren().contains(btn))
						return;
					else{
						for(int i=0; i<WidgetBar.listRect.size(); ++i){
							WidgetRect targetRect = WidgetBar.listRect.get(i);
							if(!targetRect.getIsFill()){
								targetRect.setIsFill(true);
								btn.setLayoutX(targetRect.getLayoutX());
								btn.setLayoutY(targetRect.getLayoutY());
								btn.setRow(targetRect.getRow());
								btn.setCol(targetRect.getCol());
								break;
							}
						}
						WidgetBar.widgetBar.getChildren().add(btn);
					}
				}else{
					WidgetButton btn = null;
					for(int i=0; i<WidgetBar.listBtn.size(); ++i){
						if(WidgetBar.listBtn.get(i).getId().equals("refresh")){
							btn = WidgetBar.listBtn.get(i);
							break;
						}
					}
					int row = btn.getRow();
					int col = btn.getCol();
					WidgetRect targetRect = null;
					for(int i=0; i<WidgetBar.listRect.size(); ++i){
						if(row == WidgetBar.listRect.get(i).getRow() && col == WidgetBar.listRect.get(i).getCol()){
							targetRect = WidgetBar.listRect.get(i);
						}
					}
					int idxTargetRect = Integer.parseInt(targetRect.getId());
					if(btn.isBigger()){
						WidgetRect[] nearRect = new WidgetRect[4];
						nearRect[0] = WidgetBar.listRect.get(idxTargetRect);
						nearRect[1] = WidgetBar.listRect.get(idxTargetRect+1);
						nearRect[2] = WidgetBar.listRect.get(idxTargetRect+WidgetBar.numOfRect);
						nearRect[3] = WidgetBar.listRect.get(idxTargetRect+WidgetBar.numOfRect+1);		
						nearRect[0].setIsFill(false);
						nearRect[1].setIsFill(false);
						nearRect[2].setIsFill(false);
						nearRect[3].setIsFill(false);
					}else{
						WidgetBar.listRect.get(idxTargetRect).setIsFill(false);
					}
					btn.setRow(0);
					btn.setCol(0);
					WidgetBar.widgetBar.getChildren().remove(btn);
				}
			}
		});
		weather.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(newValue == true){
					WidgetButton btn = null;
					for(int i=0; i<WidgetBar.listBtn.size(); ++i){
						if(WidgetBar.listBtn.get(i).getId().equals("weather")){
							btn = WidgetBar.listBtn.get(i);
							break;
						}
					}
					if(WidgetBar.widgetBar.getChildren().contains(btn))
						return;
					else{
						for(int i=0; i<WidgetBar.listRect.size(); ++i){
							WidgetRect targetRect = WidgetBar.listRect.get(i);
							if(!targetRect.getIsFill()){
								targetRect.setIsFill(true);
								btn.setLayoutX(targetRect.getLayoutX());
								btn.setLayoutY(targetRect.getLayoutY());
								btn.setRow(targetRect.getRow());
								btn.setCol(targetRect.getCol());
								break;
							}
						}
						WidgetBar.widgetBar.getChildren().add(btn);
					}
				}else{
					WidgetButton btn = null;
					for(int i=0; i<WidgetBar.listBtn.size(); ++i){
						if(WidgetBar.listBtn.get(i).getId().equals("weather")){
							btn = WidgetBar.listBtn.get(i);
							break;
						}
					}
					int row = btn.getRow();
					int col = btn.getCol();
					WidgetRect targetRect = null;
					for(int i=0; i<WidgetBar.listRect.size(); ++i){
						if(row == WidgetBar.listRect.get(i).getRow() && col == WidgetBar.listRect.get(i).getCol()){
							targetRect = WidgetBar.listRect.get(i);
						}
					}
					int idxTargetRect = Integer.parseInt(targetRect.getId());
					if(btn.isBigger()){
						WidgetRect[] nearRect = new WidgetRect[4];
						nearRect[0] = WidgetBar.listRect.get(idxTargetRect);
						nearRect[1] = WidgetBar.listRect.get(idxTargetRect+1);
						nearRect[2] = WidgetBar.listRect.get(idxTargetRect+WidgetBar.numOfRect);
						nearRect[3] = WidgetBar.listRect.get(idxTargetRect+WidgetBar.numOfRect+1);		
						nearRect[0].setIsFill(false);
						nearRect[1].setIsFill(false);
						nearRect[2].setIsFill(false);
						nearRect[3].setIsFill(false);
					}else{
						WidgetBar.listRect.get(idxTargetRect).setIsFill(false);
					}
					btn.setRow(0);
					btn.setCol(0);
					WidgetBar.widgetBar.getChildren().remove(btn);
				}
			}
		});
		pin.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(newValue == true){
					WidgetButton btn = null;
					for(int i=0; i<WidgetBar.listBtn.size(); ++i){
						if(WidgetBar.listBtn.get(i).getId().equals("pin")){
							btn = WidgetBar.listBtn.get(i);
							break;
						}
					}
					if(WidgetBar.widgetBar.getChildren().contains(btn))
						return;
					else{
						for(int i=0; i<WidgetBar.listRect.size(); ++i){
							WidgetRect targetRect = WidgetBar.listRect.get(i);
							if(!targetRect.getIsFill()){
								targetRect.setIsFill(true);
								btn.setLayoutX(targetRect.getLayoutX());
								btn.setLayoutY(targetRect.getLayoutY());
								btn.setRow(targetRect.getRow());
								btn.setCol(targetRect.getCol());
								break;
							}
						}
						WidgetBar.widgetBar.getChildren().add(btn);
					}
				}else{
					WidgetButton btn = null;
					for(int i=0; i<WidgetBar.listBtn.size(); ++i){
						if(WidgetBar.listBtn.get(i).getId().equals("pin")){
							btn = WidgetBar.listBtn.get(i);
							break;
						}
					}
					int row = btn.getRow();
					int col = btn.getCol();
					WidgetRect targetRect = null;
					for(int i=0; i<WidgetBar.listRect.size(); ++i){
						if(row == WidgetBar.listRect.get(i).getRow() && col == WidgetBar.listRect.get(i).getCol()){
							targetRect = WidgetBar.listRect.get(i);
						}
					}
					int idxTargetRect = Integer.parseInt(targetRect.getId());
					if(btn.isBigger()){
						WidgetRect[] nearRect = new WidgetRect[4];
						nearRect[0] = WidgetBar.listRect.get(idxTargetRect);
						nearRect[1] = WidgetBar.listRect.get(idxTargetRect+1);
						nearRect[2] = WidgetBar.listRect.get(idxTargetRect+WidgetBar.numOfRect);
						nearRect[3] = WidgetBar.listRect.get(idxTargetRect+WidgetBar.numOfRect+1);		
						nearRect[0].setIsFill(false);
						nearRect[1].setIsFill(false);
						nearRect[2].setIsFill(false);
						nearRect[3].setIsFill(false);
					}else{
						WidgetBar.listRect.get(idxTargetRect).setIsFill(false);
					}
					btn.setRow(0);
					btn.setCol(0);
					WidgetBar.widgetBar.getChildren().remove(btn);
				}
			}
		});
		capture.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(newValue == true){
					WidgetButton btn = null;
					for(int i=0; i<WidgetBar.listBtn.size(); ++i){
						if(WidgetBar.listBtn.get(i).getId().equals("capture")){
							btn = WidgetBar.listBtn.get(i);
							break;
						}
					}
					if(WidgetBar.widgetBar.getChildren().contains(btn))
						return;
					else{
						for(int i=0; i<WidgetBar.listRect.size(); ++i){
							WidgetRect targetRect = WidgetBar.listRect.get(i);
							if(!targetRect.getIsFill()){
								targetRect.setIsFill(true);
								btn.setLayoutX(targetRect.getLayoutX());
								btn.setLayoutY(targetRect.getLayoutY());
								btn.setRow(targetRect.getRow());
								btn.setCol(targetRect.getCol());
								break;
							}
						}
						WidgetBar.widgetBar.getChildren().add(btn);
					}
				}else{
					WidgetButton btn = null;
					for(int i=0; i<WidgetBar.listBtn.size(); ++i){
						if(WidgetBar.listBtn.get(i).getId().equals("capture")){
							btn = WidgetBar.listBtn.get(i);
							break;
						}
					}
					int row = btn.getRow();
					int col = btn.getCol();
					WidgetRect targetRect = null;
					for(int i=0; i<WidgetBar.listRect.size(); ++i){
						if(row == WidgetBar.listRect.get(i).getRow() && col == WidgetBar.listRect.get(i).getCol()){
							targetRect = WidgetBar.listRect.get(i);
						}
					}
					int idxTargetRect = Integer.parseInt(targetRect.getId());
					if(btn.isBigger()){
						WidgetRect[] nearRect = new WidgetRect[4];
						nearRect[0] = WidgetBar.listRect.get(idxTargetRect);
						nearRect[1] = WidgetBar.listRect.get(idxTargetRect+1);
						nearRect[2] = WidgetBar.listRect.get(idxTargetRect+WidgetBar.numOfRect);
						nearRect[3] = WidgetBar.listRect.get(idxTargetRect+WidgetBar.numOfRect+1);		
						nearRect[0].setIsFill(false);
						nearRect[1].setIsFill(false);
						nearRect[2].setIsFill(false);
						nearRect[3].setIsFill(false);
					}else{
						WidgetBar.listRect.get(idxTargetRect).setIsFill(false);
					}
					btn.setRow(0);
					btn.setCol(0);
					WidgetBar.widgetBar.getChildren().remove(btn);
				}
			}
		});
		windows.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(newValue == true){
					WidgetButton btn = null;
					for(int i=0; i<WidgetBar.listBtn.size(); ++i){
						if(WidgetBar.listBtn.get(i).getId().equals("windows")){
							btn = WidgetBar.listBtn.get(i);
							break;
						}
					}
					if(WidgetBar.widgetBar.getChildren().contains(btn))
						return;
					else{
						for(int i=0; i<WidgetBar.listRect.size(); ++i){
							WidgetRect targetRect = WidgetBar.listRect.get(i);
							if(!targetRect.getIsFill()){
								targetRect.setIsFill(true);
								btn.setLayoutX(targetRect.getLayoutX());
								btn.setLayoutY(targetRect.getLayoutY());
								btn.setRow(targetRect.getRow());
								btn.setCol(targetRect.getCol());
								break;
							}
						}
						WidgetBar.widgetBar.getChildren().add(btn);
					}
				}else{
					WidgetButton btn = null;
					for(int i=0; i<WidgetBar.listBtn.size(); ++i){
						if(WidgetBar.listBtn.get(i).getId().equals("windows")){
							btn = WidgetBar.listBtn.get(i);
							break;
						}
					}
					int row = btn.getRow();
					int col = btn.getCol();
					WidgetRect targetRect = null;
					for(int i=0; i<WidgetBar.listRect.size(); ++i){
						if(row == WidgetBar.listRect.get(i).getRow() && col == WidgetBar.listRect.get(i).getCol()){
							targetRect = WidgetBar.listRect.get(i);
						}
					}
					int idxTargetRect = Integer.parseInt(targetRect.getId());
					if(btn.isBigger()){
						WidgetRect[] nearRect = new WidgetRect[4];
						nearRect[0] = WidgetBar.listRect.get(idxTargetRect);
						nearRect[1] = WidgetBar.listRect.get(idxTargetRect+1);
						nearRect[2] = WidgetBar.listRect.get(idxTargetRect+WidgetBar.numOfRect);
						nearRect[3] = WidgetBar.listRect.get(idxTargetRect+WidgetBar.numOfRect+1);		
						nearRect[0].setIsFill(false);
						nearRect[1].setIsFill(false);
						nearRect[2].setIsFill(false);
						nearRect[3].setIsFill(false);
					}else{
						WidgetBar.listRect.get(idxTargetRect).setIsFill(false);
					}
					btn.setRow(0);
					btn.setCol(0);
					WidgetBar.widgetBar.getChildren().remove(btn);
				}
			}
		});
		
		for(int i=0; i<WidgetBar.listBtn.size(); ++i){
			if(WidgetBar.listBtn.get(i).getId().equals("close")){
				close.setSelected(true);
			}else if(WidgetBar.listBtn.get(i).getId().equals("resize")){
				resize.setSelected(true);
			}else if(WidgetBar.listBtn.get(i).getId().equals("minimize")){
				minimize.setSelected(true);
			}else if(WidgetBar.listBtn.get(i).getId().equals("split")){
				split.setSelected(true);
			}else if(WidgetBar.listBtn.get(i).getId().equals("add")){
				add.setSelected(true);
			}else if(WidgetBar.listBtn.get(i).getId().equals("domain")){
				domain.setSelected(true);
			}else if(WidgetBar.listBtn.get(i).getId().equals("home")){
				home.setSelected(true);
			}else if(WidgetBar.listBtn.get(i).getId().equals("music")){
				music.setSelected(true);
			}else if(WidgetBar.listBtn.get(i).getId().equals("prev")){
				prev.setSelected(true);
			}else if(WidgetBar.listBtn.get(i).getId().equals("next")){
				next.setSelected(true);
			}else if(WidgetBar.listBtn.get(i).getId().equals("refresh")){
				refresh.setSelected(true);
			}else if(WidgetBar.listBtn.get(i).getId().equals("weather")){
				weather.setSelected(true);
			}else if(WidgetBar.listBtn.get(i).getId().equals("pin")){
				pin.setSelected(true);
			}else if(WidgetBar.listBtn.get(i).getId().equals("capture")){
				capture.setSelected(true);
			}else if(WidgetBar.listBtn.get(i).getId().equals("windows")){
				windows.setSelected(true);
			}
		}
	}
	
	private void WidgetBarColorChange(ActionEvent event) {
		Color color = colorPicker.getValue();
		StringBuffer rgba = new StringBuffer();
		rgba.append("-fx-background-color: ");
		rgba.append("rgba("+color.getRed()*255);
		rgba.append(", "+color.getGreen()*255);
		rgba.append(", "+color.getBlue()*255);
		rgba.append(", "+color.getOpacity()+");");
		AppMain.topScroll.setStyle(rgba.toString());
		AppMain.DefaultColor = rgba.toString();
		AppMain.split.setStyle(rgba.toString());
	}

	private void changeWidgetBarHeight(ActionEvent event) {
		AppMain.WidgetHeight = this.widgetHeight;
		WidgetBar.widgetBar.changeWidgetBarHeight();
	}
}
