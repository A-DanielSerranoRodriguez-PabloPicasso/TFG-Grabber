package models;

import dao.GLibrary;
import dao.GLibraryImp;
import grabberApp.javafx.fxmls.popups.Popup;

public abstract class AbstractPopupController {
	public Popup popup;

	public GLibrary<Library> gLibrary;

	public void setPopup(Popup popup) {
		this.popup = popup;
	}

	public GLibrary<Library> getGLibrary() {
		return GLibraryImp.gestor();
	}

}