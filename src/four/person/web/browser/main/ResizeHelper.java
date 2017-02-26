package four.person.web.browser.main;

import four.person.web.browser.widgetbar.WidgetBarController;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;

//created by Alexander Berg
public class ResizeHelper {

    public static void addResizeListener(SplitPane splitPane) {
        ResizeListener resizeListener = new ResizeListener(splitPane);
        splitPane.addEventHandler(MouseEvent.MOUSE_MOVED, resizeListener);
        splitPane.addEventHandler(MouseEvent.MOUSE_PRESSED, resizeListener);
        splitPane.addEventHandler(MouseEvent.MOUSE_DRAGGED, resizeListener);
        splitPane.addEventHandler(MouseEvent.MOUSE_EXITED, resizeListener);
        splitPane.addEventHandler(MouseEvent.MOUSE_EXITED_TARGET, resizeListener);
        ObservableList<Node> children = splitPane.getChildrenUnmodifiable();
        for (Node child : children) {
            addListenerDeeply(child, resizeListener);
        }
    }

    public static void addListenerDeeply(Node node, EventHandler<MouseEvent> listener) {
        node.addEventHandler(MouseEvent.MOUSE_MOVED, listener);
        node.addEventHandler(MouseEvent.MOUSE_PRESSED, listener);
        node.addEventHandler(MouseEvent.MOUSE_DRAGGED, listener);
        node.addEventHandler(MouseEvent.MOUSE_EXITED, listener);
        node.addEventHandler(MouseEvent.MOUSE_EXITED_TARGET, listener);
        if (node instanceof Parent) {
            Parent parent = (Parent) node;
            ObservableList<Node> children = parent.getChildrenUnmodifiable();
            for (Node child : children) {
                addListenerDeeply(child, listener);
            }
        }
    }

    static class ResizeListener implements EventHandler<MouseEvent> {
        private SplitPane splitPane;
        private Cursor cursorEvent = Cursor.DEFAULT;
        private int border = 6;
        private double startX = 0;
        private double startY = 0;

        public ResizeListener(SplitPane splitPane) {
            this.splitPane = splitPane;
        }

        @Override
        public void handle(MouseEvent mouseEvent) {
            EventType<? extends MouseEvent> mouseEventType = mouseEvent.getEventType();
 
            double mouseEventX = mouseEvent.getSceneX(), 
                   mouseEventY = mouseEvent.getSceneY(),
                   sceneWidth = splitPane.getWidth(),
                   sceneHeight = splitPane.getHeight();
            if(!WidgetBarController.isClickedResize)
            	return;

            if (MouseEvent.MOUSE_MOVED.equals(mouseEventType) == true) {
                if (mouseEventX < border && mouseEventY < border) {
                    cursorEvent = Cursor.NW_RESIZE;
                } else if (mouseEventX < border && mouseEventY > sceneHeight - border) {
                    cursorEvent = Cursor.SW_RESIZE;
                } else if (mouseEventX > sceneWidth - border && mouseEventY < border) {
                    cursorEvent = Cursor.NE_RESIZE;
                } else if (mouseEventX > sceneWidth - border && mouseEventY > sceneHeight - border) {
                    cursorEvent = Cursor.SE_RESIZE;
                } else if (mouseEventX < border) {
                    cursorEvent = Cursor.W_RESIZE;
                } else if (mouseEventX > sceneWidth - border) {
                    cursorEvent = Cursor.E_RESIZE;
                } else if (mouseEventY < border) {
                    cursorEvent = Cursor.N_RESIZE;
                } else if (mouseEventY > sceneHeight - border) {
                    cursorEvent = Cursor.S_RESIZE;
                } else {
                    cursorEvent = Cursor.DEFAULT;
                }
                AppMain.scene.setCursor(cursorEvent);
            } else if(MouseEvent.MOUSE_EXITED.equals(mouseEventType) || MouseEvent.MOUSE_EXITED_TARGET.equals(mouseEventType)){
            	AppMain.scene.setCursor(Cursor.DEFAULT);
            } else if (MouseEvent.MOUSE_PRESSED.equals(mouseEventType) == true) {
                startX = AppMain.stage.getWidth() - mouseEventX;
                startY = AppMain.stage.getHeight() - mouseEventY;
            } else if (MouseEvent.MOUSE_DRAGGED.equals(mouseEventType) == true) {
                if (Cursor.DEFAULT.equals(cursorEvent) == false) {
                    if (Cursor.W_RESIZE.equals(cursorEvent) == false && Cursor.E_RESIZE.equals(cursorEvent) == false) {
                        double minHeight = AppMain.stage.getMinHeight() > (border*2) ? AppMain.stage.getMinHeight() : (border*2);
                        if (Cursor.NW_RESIZE.equals(cursorEvent) == true || Cursor.N_RESIZE.equals(cursorEvent) == true || Cursor.NE_RESIZE.equals(cursorEvent) == true) {
                            if (AppMain.stage.getHeight() > minHeight || mouseEventY < 0) {
                            	if(AppMain.stage.getHeight() > AppMain.stage.getMaxHeight()){
                            		AppMain.stage.setHeight(AppMain.stage.getMaxHeight());
                            	}else{
                            		AppMain.stage.setHeight(AppMain.stage.getY() - mouseEvent.getScreenY() + AppMain.stage.getHeight());
                            		AppMain.stage.setY(mouseEvent.getScreenY());
                            	}
                            }
                        } else {
                            if (AppMain.stage.getHeight() > minHeight || mouseEventY + startY - AppMain.stage.getHeight() > 0) {
                            	AppMain.stage.setHeight(mouseEventY + startY);
                            }
                        }
                    }

                    if (Cursor.N_RESIZE.equals(cursorEvent) == false && Cursor.S_RESIZE.equals(cursorEvent) == false) {
                        double minWidth = AppMain.stage.getMinWidth() > (border*2) ? AppMain.stage.getMinWidth() : (border*2);
                        if (Cursor.NW_RESIZE.equals(cursorEvent) == true || Cursor.W_RESIZE.equals(cursorEvent) == true || Cursor.SW_RESIZE.equals(cursorEvent) == true) {
                            if (AppMain.stage.getWidth() > minWidth || mouseEventX < 0) {
                            	if(AppMain.stage.getWidth() > AppMain.stage.getMaxWidth()){
                            		AppMain.stage.setWidth(AppMain.stage.getMaxWidth());
                            	}else{
                            		AppMain.stage.setWidth(AppMain.stage.getX() - mouseEvent.getScreenX() + AppMain.stage.getWidth());
                            		AppMain.stage.setX(mouseEvent.getScreenX());
                            	}
                            }
                        } else {
                            if (AppMain.stage.getWidth() > minWidth || mouseEventX + startX - AppMain.stage.getWidth() > 0) {
                            	AppMain.stage.setWidth(mouseEventX + startX);
                            }
                        }
                    }
                }

            }
        }
    }
}