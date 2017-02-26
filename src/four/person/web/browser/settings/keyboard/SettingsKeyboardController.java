package four.person.web.browser.settings.keyboard;

import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;

public class SettingsKeyboardController implements Initializable {
	@FXML private Label tf_close;
	@FXML private Label resize;
	@FXML private Label tf_minimize;
	@FXML private Label tf_split;
	@FXML private Label tf_add;
	@FXML private Label tf_domain;
	@FXML private Label tf_home;
	@FXML private Label tf_music;
	@FXML private Label tf_next;
	@FXML private Label tf_prev;
	@FXML private Label tf_refresh;
	@FXML private Label tf_weather;
	@FXML private Label tf_pin;
	@FXML private Label tf_capture;
	@FXML private Label tf_windows;
	@FXML private HBox Hbox_close;
	@FXML private ComboBox<String> KeySelectBox;
	@FXML private TextField tf_setting;
	@FXML private Button bt_setting;
	@FXML private Button bt_reset;
	@FXML private VBox vbox_content;

	private FXCollections observableArrayList;

	private KeyEvent shortcutKeyEvent;
	private List<String> haskeyvalue = new ArrayList<String>();

	String result;
	String shortcutkyeValue;
	String shortcut;
	int countvalue = 0;
	public static String tfname = "";
	public static Hashtable<String, String> keytable = new Hashtable<>();
	public static Hashtable<String, String> resettable = new Hashtable<>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		KeySelectBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> selected, String oldkey, String newkey) {
			
				if (newkey != null) {
					tf_setting.setDisable(false);
					bt_setting.setDisable(false);
					bt_reset.setDisable(false);
					tf_setting.setText("");
					tfname = newkey;
					if (haskeyvalue.isEmpty()) {
						System.out.println("�������");
						tf_setting.requestFocus();
						tf_keysetting(tf_setting);
					} else {

						for (int i = 0; i < haskeyvalue.size(); i++) {
							if (newkey == haskeyvalue.get(i)) {
								tf_setting.setText("");
								tf_setting.setDisable(true);
							
								System.out.println("�̹� ��������");
								bt_setting.setDisable(true);
								countvalue = i;
							}
						}

					}
				}
			}
		});

		Button_OK(bt_setting, tf_setting);
		Button_RESET(bt_reset, tf_setting);
	}

	// textfield�� Ű �޾ƿ���
	public void tf_keysetting(TextField textField) {
		textField.setOnKeyPressed(Keyevent -> tf_setkey(textField, Keyevent));
	}

	public void Button_OK(Button button, TextField textField) {
		button.setOnAction(event -> bt_setok(textField));

	}

	public void Button_RESET(Button button, TextField textField) {

		button.setOnAction(event -> bt_reset(textField));
	}

	private void tf_setkey(TextField textField, KeyEvent event) {
		if (event.getCode() == KeyCode.TAB) {

		} else {
			// Clear the previous text
			textField.setText("");
			// Process only desired key types
			if (event.getCode().isLetterKey() || event.getCode().isDigitKey() || event.getCode().isFunctionKey()) {
				shortcut = event.getCode().getName();
				if (event.isAltDown()) {
					shortcut = "Alt + " + shortcut;
				}
				if (event.isControlDown()) {
					shortcut = "Ctrl + " + shortcut;
				}
				if (event.isShiftDown()) {
					shortcut = "Shift + " + shortcut;
				}
				textField.setText(shortcut);

				System.out.println(shortcut.toString());

			} else {
				shortcutKeyEvent = null;
			}
		}
	}

	private void bt_reset(TextField textField) {
		keytable.remove(resettable.get(tfname).toString());
		for (int i = 0; i < haskeyvalue.size(); i++) {
			if (tfname == haskeyvalue.get(i)) {
				haskeyvalue.remove(i);
				bt_reset.setDisable(true);
				bt_setting.setDisable(false);
			}
		}
		textField.setText("");
		if (tfname.equalsIgnoreCase("����")) {
			tf_close.setText("");

		} else if (tfname.equalsIgnoreCase("�ּ� �Է� â")) {
			tf_domain.setText("");
		} else if (tfname.equalsIgnoreCase("�ڷ� ����")) {
			tf_prev.setText("");
		} else if (tfname.equalsIgnoreCase("������ ����")) {
			tf_next.setText("");
		} else if (tfname.equalsIgnoreCase("�� �� �߰�")) {
			tf_add.setText("");
		} else if (tfname.equalsIgnoreCase("����")) {
			tf_close.setText("");
		} else if (tfname.equalsIgnoreCase("ũ�� ����")) {
			resize.setText("");
		} else if (tfname.equalsIgnoreCase("�ּ�ȭ")) {
			tf_minimize.setText("");
		} else if (tfname.equalsIgnoreCase("ȭ�� ĸ�� �� ����")) {
			tf_capture.setText("");
		} else if (tfname.equalsIgnoreCase("���� �÷��̾�")) {
			tf_music.setText("");
		} else if (tfname.equalsIgnoreCase("�������� �ֻ����� ����")) {
			tf_pin.setText("");
		} else if (tfname.equalsIgnoreCase("ȭ�� ����")) {
			tf_split.setText("");
		} else if (tfname.equalsIgnoreCase("�� �̵�")) {
			tf_windows.setText("");
		} else if (tfname.equalsIgnoreCase("����")) {
			tf_weather.setText("");
		} else if (tfname.equalsIgnoreCase("���ΰ�ħ")) {
			tf_refresh.setText("");
		} else if (tfname.equalsIgnoreCase("Ȩ")) {
			tf_home.setText("");
		}

		textField.setDisable(false);

	}

	private void bt_setok(TextField textField) {
	
	
		if(keytable.containsKey(textField.getText())){
			Dialog<ButtonType> dialog = new Dialog<ButtonType>();
			dialog.initModality(Modality.APPLICATION_MODAL);
			dialog.getDialogPane().setContentText("�̹� ����Ű�� �����Ǿ� �ֽ��ϴ�.");
			dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
			
			dialog.showAndWait();
			
		}
		
		else{
		System.out.println(tfname + "tfname");
		System.out.println(shortcut + "shortcut");
		textField.setDisable(true);
		if (tfname.equalsIgnoreCase("�ּ� �Է� â")) {
			tf_domain.setText(tf_setting.getText());
		} else if (tfname.equalsIgnoreCase("�ڷ� ����")) {
			tf_next.setText(tf_setting.getText());
		} else if (tfname.equalsIgnoreCase("������ ����")) {
			tf_prev.setText(tf_setting.getText());
		} else if (tfname.equalsIgnoreCase("�� �� �߰�")) {
			tf_add.setText(tf_setting.getText());
		} else if (tfname.equalsIgnoreCase("����")) {
			tf_close.setText(tf_setting.getText());
		} else if (tfname.equalsIgnoreCase("ũ�� ����")) {
			resize.setText(tf_setting.getText());
		} else if (tfname.equalsIgnoreCase("�ּ�ȭ")) {
			tf_minimize.setText(tf_setting.getText());
		} else if (tfname.equalsIgnoreCase("ȭ�� ĸ�� �� ����")) {
			tf_capture.setText(tf_setting.getText());
		} else if (tfname.equalsIgnoreCase("���� �÷��̾�")) {
			tf_music.setText(tf_setting.getText());
		} else if (tfname.equalsIgnoreCase("�������� �ֻ����� ����")) {
			tf_pin.setText(tf_setting.getText());
		} else if (tfname.equalsIgnoreCase("ȭ�� ����")) {
			tf_split.setText(tf_setting.getText());
		} else if (tfname.equalsIgnoreCase("�� �̵�")) {
			tf_windows.setText(tf_setting.getText());
		} else if (tfname.equalsIgnoreCase("����")) {
			tf_weather.setText(tf_setting.getText());
		} else if (tfname.equalsIgnoreCase("���ΰ�ħ")) {
			tf_refresh.setText(tf_setting.getText());
		} else if (tfname.equalsIgnoreCase("Ȩ")) {
			tf_home.setText(tf_setting.getText());
		}
		resettable.put(tfname, shortcut);
		keytable.put(shortcut, tfname);
		haskeyvalue.add(countvalue, tfname);
		
		textField.setText("");
		bt_setting.setDisable(true);
		bt_reset.setDisable(false);
		}
	}
}
