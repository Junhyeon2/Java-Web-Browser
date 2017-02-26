package four.person.web.browser.widgetbar;

import java.util.ArrayList;

import four.person.web.browser.main.AppMain;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class WidgetBar extends AnchorPane{
	public static WidgetBar widgetBar;
	public final static double rectSize = 25.0;
	public final static double padding = 10.0;
	public final static double btnPadding = 5.0;
	public final static String ext = ".png";
	public static ArrayList<WidgetRect> listRect = new ArrayList<WidgetRect>();
	public static ArrayList<WidgetButton> listBtn = new ArrayList<WidgetButton>();
	public static int numOfRect;
	double height;
	double width;
	double alignCenter;
	AppMain root;

	
	String[] btnName = {
			"close",
			"resize",
			"minimize",
			"split",
			"add",
			"domain",
			"home",
			"music",
			"prev",
			"next",
			"refresh",
			"weather",
			"pin",
			"capture",
			"windows"
	};
	
	ContextMenu widgetBarMenu, btnMenu;
	Menu btnSizeMenu;
	MenuItem[] widgetBarMenuItem, btnSizeMenuItem;
	MenuItem btnDeleteMenuItem;
	String[] widgetBarMenuName = {"편집", "설정"};
	String[] btnSizeMenuName = {"크게", "작게"};
	
		
	public WidgetBarController widgetBarController;
			
	public WidgetBar(AppMain root) {
		widgetBar = this;
        this.root = root;
        this.width = root.width - 2*padding;
        if(AppMain.WidgetHeight == 1){
        	this.height = AppMain.WidgetHeight*rectSize + padding*2;
        }else{
        	this.height = AppMain.WidgetHeight*rectSize + btnPadding + padding*2;
        }
		
        this.numOfRect = (int)Math.floor(width/(rectSize+btnPadding));
        this.alignCenter  = (root.width - (rectSize+btnPadding)*numOfRect)/2;
		
		this.widgetBarMenu = new ContextMenu();
		this.widgetBarMenu.setPrefWidth(200.0);
		this.widgetBarMenuItem = new MenuItem[widgetBarMenuName.length];
		
		this.btnMenu = new ContextMenu();
		this.btnSizeMenu = new Menu("크기 변경");
		this.btnDeleteMenuItem = new MenuItem("삭제");
		this.btnSizeMenuItem = new MenuItem[btnSizeMenuName.length];
		
		this.setPrefHeight(height);
		this.setPrefWidth(root.width-2.0);

		for(int i=0; i<AppMain.WidgetHeight; ++i){
			for(int j=0; j<numOfRect; ++j){
				WidgetRect r = new WidgetRect(i, j);
				r.setLayoutX(j*rectSize+alignCenter+j*btnPadding);
				r.setLayoutY(i*rectSize+padding+i*btnPadding);
				r.setRow(i);
				r.setCol(j);
				r.setWidth(rectSize);
				r.setHeight(rectSize);
				r.setFill(Color.TRANSPARENT);
				r.setId(Integer.toString(j));
				this.getChildren().add(r);
				WidgetBar.listRect.add(r);
			}
		}
		
		for(int i=0; i<btnName.length; ++i){
			WidgetButton btn = new WidgetButton(btnName[i], btnName[i] + ext);
			this.getChildren().add(btn);
			listBtn.add(btn);
		}
		
		for(int i=0; i<widgetBarMenuName.length; ++i){
			widgetBarMenuItem[i] = new MenuItem(widgetBarMenuName[i]);
			widgetBarMenu.getItems().add(widgetBarMenuItem[i]);
		}
		
		btnMenu.getItems().addAll(btnSizeMenu, btnDeleteMenuItem);
				
		for(int i=0; i<btnSizeMenuName.length; ++i){
			btnSizeMenuItem[i] = new MenuItem(btnSizeMenuName[i]);
			btnSizeMenu.getItems().add(btnSizeMenuItem[i]);
		}

		widgetBarController = new WidgetBarController(this);
	}

	public void changeWidgetBarHeight() {
		
		for(int i=0; i<WidgetBar.listBtn.size(); ++i){
			this.getChildren().remove(WidgetBar.listBtn.get(i));
		}
		
		if (AppMain.WidgetHeight == 1) {
			this.height = AppMain.WidgetHeight * rectSize + padding * 2;
			for(int i=0; i<WidgetBar.listBtn.size(); ++i){
				WidgetBar.listBtn.get(i).setSize(25.0);
				WidgetBar.listBtn.get(i).setPrefWidth(25.0);
				WidgetBar.listBtn.get(i).setPrefHeight(25.0);
				WidgetBar.listBtn.get(i).setBigger(false);
				//row의 위치를 바꿔야한다.
				if(WidgetBar.listBtn.get(i).getRow() == 1){
					int col = WidgetBar.listBtn.get(i).getCol();
					if(WidgetBar.listRect.get(col).getIsFill()){
						for(int j=0; j<WidgetBar.listRect.size(); ++j){
							WidgetRect targetRect = WidgetBar.listRect.get(j);
							if(!targetRect.getIsFill()){
								targetRect.setIsFill(true);
								WidgetBar.listBtn.get(i).setLayoutX(targetRect.getLayoutX());
								WidgetBar.listBtn.get(i).setLayoutY(targetRect.getLayoutY());
								WidgetBar.listBtn.get(i).setRow(targetRect.getRow());
								WidgetBar.listBtn.get(i).setCol(targetRect.getCol());
								break;
							}
						}
					}else{
						WidgetBar.listBtn.get(i).setLayoutX(WidgetBar.listRect.get(col).getLayoutX());
						WidgetBar.listBtn.get(i).setLayoutY(WidgetBar.listRect.get(col).getLayoutY());
						WidgetBar.listBtn.get(i).setRow(WidgetBar.listRect.get(col).getRow());
					}
				}
			}
		} else {
			this.height = AppMain.WidgetHeight * rectSize + btnPadding + padding * 2;
		}
		
		for(int i=0; i<WidgetBar.listRect.size(); ++i){
			this.getChildren().remove(WidgetBar.listRect.get(i));
		}
		WidgetBar.listRect.removeAll(WidgetBar.listRect);
		
		this.setPrefHeight(height);
		for(int i=0; i<AppMain.WidgetHeight; ++i){
			for(int j=0; j<numOfRect; ++j){
				WidgetRect r = new WidgetRect(i, j);
				r.setLayoutX(j*rectSize+alignCenter+j*btnPadding);
				r.setLayoutY(i*rectSize+padding+i*btnPadding);
				r.setRow(i);
				r.setCol(j);
				r.setWidth(rectSize);
				r.setHeight(rectSize);
				r.setFill(Color.TRANSPARENT);
				r.setId(Integer.toString(j));
				this.getChildren().add(r);
				WidgetBar.listRect.add(r);
			}
		}
		
		for(int i=0; i<WidgetBar.listBtn.size(); ++i){
			this.getChildren().add(WidgetBar.listBtn.get(i));
			int row = WidgetBar.listBtn.get(i).getRow();
			int col = WidgetBar.listBtn.get(i).getCol();
			for(int j=0; j<WidgetBar.listRect.size(); ++j){
				if(row == WidgetBar.listRect.get(j).getRow() && col == WidgetBar.listRect.get(j).getCol()){
					WidgetBar.listRect.get(j).setIsFill(true);
					break;
				}
			}
		}
	}
}
