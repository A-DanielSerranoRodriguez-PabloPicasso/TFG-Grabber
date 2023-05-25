package models.javafx;

import java.util.List;

import dao.GVideo;
import dao.GVideoImp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import models.Library;
import models.Video;
import utils.Utils;

public class CardVideo extends AnchorPane {

	private Video video;
	private HBox hbContent, hbEdit, hbDelete;
	private Label lblName, lblPath, lblDate;
	private Button btnEdit, btnDelete, btnAcceptEdit, btnCancelEdit, btnAcceptDelete, btnCancelDelete;
	private TextField txfName;
	private ChoiceBox<String> choiceLibrary;

	public CardVideo(Video video) {
		this.video = video;

		hbContent = new HBox(10);
		hbContent.setPadding(new Insets(20));
		hbContent.setAlignment(Pos.CENTER);

		lblName = new Label(video.getName());
		lblPath = new Label(video.getLibrary().getName());
		lblDate = new Label(Long.toString(video.getDateCreated()));
		btnEdit = new Button("Editar");
		btnDelete = new Button("Eliminar");

		hbContent.getChildren().add(lblName);
		hbContent.getChildren().add(lblPath);
		hbContent.getChildren().add(lblDate);
		hbContent.getChildren().add(btnEdit);
		hbContent.getChildren().add(btnDelete);

		hbEdit = new HBox(10);
		hbEdit.setPadding(new Insets(20));
		hbEdit.setAlignment(Pos.CENTER);

		txfName = new TextField(video.getName());
		choiceLibrary = new ChoiceBox<>();
		btnAcceptEdit = new Button("Confirmar cambios");
		btnCancelEdit = new Button("Cancelar");

		hbEdit.getChildren().add(txfName);
		hbEdit.getChildren().add(choiceLibrary);
		hbEdit.getChildren().add(btnAcceptEdit);
		hbEdit.getChildren().add(btnCancelEdit);

		hbDelete = new HBox(10);
		hbDelete.setPadding(new Insets(20));
		hbDelete.setAlignment(Pos.CENTER);

		btnAcceptDelete = new Button("¿Seguro?");
		btnCancelDelete = new Button("Cancelar");

		hbDelete.getChildren().add(btnAcceptDelete);
		hbDelete.getChildren().add(btnCancelDelete);

		buttonFunctions();

		ObservableList<String> olLibraries = FXCollections.observableArrayList();

		List<Library> libraries = Utils.libraries;

		for (Library library : libraries) {
			olLibraries.add(library.getName());
		}

		choiceLibrary.setItems(olLibraries);

		choiceLibrary.getSelectionModel().select(video.getLibrary().getName());

		this.getChildren().add(hbContent);
		this.setVisible(true);
	}

	private void buttonFunctions() {
		btnEdit.setOnAction(event -> {
			this.getChildren().remove(hbContent);
			hbEdit.getChildren().add(2, lblDate);
			this.getChildren().add(hbEdit);
		});

		btnDelete.setOnAction(event -> {
			this.getChildren().remove(hbContent);
			hbDelete.getChildren().add(0, lblName);
			hbDelete.getChildren().add(1, lblPath);
			hbDelete.getChildren().add(2, lblDate);
			this.getChildren().add(hbDelete);
		});

		btnAcceptEdit.setOnAction(event -> {
			this.getChildren().remove(hbEdit);
			GVideo<Video> gVideo = GVideoImp.getGestor();
			Library library = null;
			List<Library> libraries = Utils.libraries;

			/*
			 * TODO
			 * 
			 * Cambiar nombre de archivo
			 */
//			video.setName(txfName.getText());
//			for (int i = 0; i < libraries.size() && library == null; i++) {
//				Library lib = libraries.get(i);
//
//				library = choiceLibrary.getSelectionModel().getSelectedItem().equals(lib.getName()) ? lib : null;
//			}
//			if (!video.getLibrary().equals(library)) {
//				video.moveVideo(video.getLibrary().getPath() + "/" + oldFileName);
//			}
//			video.setLibrary(library);

			video.setName(txfName.getText());
			while (!video.getVideo().getName().equals(video.getFileName())) {
			}
			for (int i = 0; i < libraries.size() && library == null; i++) {
				Library lib = libraries.get(i);
				library = choiceLibrary.getSelectionModel().getSelectedItem().equals(lib.getName()) ? lib : null;
			}

			if (!video.getLibrary().equals(library)) {
				video.moveVideo(library);
				video.setLibrary(library);
			}

			gVideo.update(video);
			lblName.setText(video.getName());
			lblPath.setText(video.getLibrary().getName());
			hbContent.getChildren().add(2, lblDate);
			this.getChildren().add(hbContent);
		});

		btnCancelEdit.setOnAction(event -> {
			this.getChildren().remove(hbEdit);
			hbContent.getChildren().add(2, lblDate);
			this.getChildren().add(hbContent);
		});

		btnAcceptDelete.setOnAction(event -> {
			this.getChildren().remove(hbDelete);
			GVideo<Video> gVideo = GVideoImp.getGestor();
			gVideo.delete(video);
			Utils.controllerLandPage.fillRecentVideos();
			this.getChildren().add(hbContent);
		});

		btnCancelDelete.setOnAction(event -> {
			this.getChildren().remove(hbDelete);
			hbContent.getChildren().add(0, lblName);
			hbContent.getChildren().add(1, lblPath);
			hbContent.getChildren().add(2, lblDate);
			this.getChildren().add(hbContent);
		});
	}

}
