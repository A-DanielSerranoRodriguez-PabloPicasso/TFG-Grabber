package grabberApp.javafx.fxmls.popups.error;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.AbstractPopupController;
import models.Video;
import models.javafx.CustomMenuItem;
import utils.Grabber;
import utils.Utils;
import utils.UtilsPopup;

public class ControllerError extends AbstractPopupController {

	private boolean letDownload;

	private Video video;

	@FXML
	private Label lblError;

	@FXML
	private Button btnCancel;

	@FXML
	private Button btnAccept;

	@FXML
	private ImageView imgWarning;

	public ControllerError() {
		popup = UtilsPopup.popup;
		gApp = Utils.gApp;
		letDownload = false;
	}

	public void initialize() {
		FileInputStream fis = null;
		File file = new File(gApp.getClass().getResource("/img/icon/warning.png").getPath());

		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		imgWarning.setImage(new Image(fis));

		switch (UtilsPopup.errType) {
		case VLC:
			lblError.setText("Es necesario instalar VLC para reproducir videos");
			break;

		case LIBRARY_EXISTS:
			lblError.setText("La biblioteca ya existe");
			break;

		case VIDEO_EXISTS:
			lblError.setText("El video ya existe");
			break;

		case LIBRARY_NAME_EMPTY:
			lblError.setText("Introduce un nombre para la biblioteca");
			break;

		case VIDEO_NAME_EMPTY:
			lblError.setText("Introduce un nombre para el video");
			break;

		case VIDEO_URL_EMPTY:
			lblError.setText("Introduce una URL");
			break;

		case VIDEO_URL_INVALID:
			lblError.setText("Introduce una URL válida");
			break;

		case VIDEO_LIBRARY_EMPTY:
			lblError.setText("Selecciona una biblioteca para el video");
			break;

		case VIDEO_SAME_NAME:
			lblError.setText("Ya existe un video con ese nombre en la biblioteca");
			break;

		case VIDEO_NOT_FOUND:
			lblError.setText("Video no encontrado en el equipo.\n¿Desde descargarlo?");
			letDownload = true;
			video = UtilsPopup.video[0];
			btnCancel.setVisible(true);
			break;

		default:
			break;
		}
	}

	@FXML
	private void handleAccept() {
		if (letDownload) {
			MenuButton mbDownloads = Utils.mbDownloads;
			HBox hBox = new HBox();
			Button btnRemove = new Button("X"), btnVer = new Button("Ver");
			CustomMenuItem cmi;

			hBox.getChildren().add(new Label());
			hBox.getChildren().add(new VBox());

			cmi = new CustomMenuItem(hBox);
			Grabber grabber = new Grabber(video.getUrl(), video.getLibrary().getPath(), video.getName(), cmi, false);

			mbDownloads.getItems().add(0, cmi);

			mbDownloads.setText(Integer.toString(Integer.parseInt(mbDownloads.getText()) + 1));
			hBox.getChildren().add(btnVer);
			hBox.getChildren().add(btnRemove);

			btnRemove.setOnAction(event -> {
				mbDownloads.getItems().remove(cmi);
				mbDownloads.setText(Integer.toString(Integer.parseInt(mbDownloads.getText()) - 1));
			});
			grabber.run();
		}
		popup.getStage().close();
	}

	@FXML
	private void handleCancel() {
		UtilsPopup.video = null;
		letDownload = false;
		handleAccept();
	}
}
