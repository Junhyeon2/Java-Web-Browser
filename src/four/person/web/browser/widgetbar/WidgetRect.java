package four.person.web.browser.widgetbar;

import javafx.scene.shape.Rectangle;

public class WidgetRect extends Rectangle{
	private int row;
	private int col;
	private boolean isFill;
	public WidgetRect(int row, int col) {
		this.row = row;
		this.col = col;
		isFill = false;
	}
	public boolean getIsFill(){
		return isFill;
	}
	public void setIsFill(boolean isFill){
		this.isFill = isFill; 
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}
}
