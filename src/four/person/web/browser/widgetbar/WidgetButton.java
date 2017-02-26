package four.person.web.browser.widgetbar;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class WidgetButton extends Button{
	private int row;
	private int col;
	private ImageView iv;
	private double size = 25.0;
	private boolean isBigger = false;
	
	public WidgetButton(String name, String url) {
		this.setPrefSize(size, size);
		this.setId(name);
		this.getStylesheets().add(getClass().getResource("button.css").toExternalForm());
		
		for(int i=0; i<WidgetBar.listRect.size(); ++i){
			WidgetRect targetRect = WidgetBar.listRect.get(i);
			if(!targetRect.getIsFill()){
				targetRect.setIsFill(true);
				this.setLayoutX(targetRect.getLayoutX());
				this.setLayoutY(targetRect.getLayoutY());
				this.setRow(targetRect.getRow());
				this.setCol(targetRect.getCol());
				break;
			}
		}
	}
	public boolean isBigger() {
		return isBigger;
	}
	public void setBigger(boolean isBigger) {
		this.isBigger = isBigger;
	}
	public int getRow(){
		return row;
	}
	public void setRow(int row){
		this.row = row;
	}
	public int getCol(){
		return col;
	}
	public void setCol(int col){
		this.col = col;
	}
	public ImageView getImage() {
		return iv;
	}
	public void setImage(ImageView image) {
		this.iv = image;
	}
	public double getSize() {
		return size;
	}
	public void setSize(double size) {
		this.size = size;
	}
}
