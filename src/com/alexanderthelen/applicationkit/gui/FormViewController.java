package com.alexanderthelen.applicationkit.gui;

import java.net.URL;
import java.util.ArrayList;

import com.alexanderthelen.applicationkit.database.Data;

import javafx.fxml.FXML;
import javafx.scene.control.Control;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.ToggleButton;

/**
 * Abstrakte Basisklasse aller Formulare.
 *
 * Unterklassen, die von dieser Klasse erben, müssen {@link #getInputControls()}
 * und {@link #getInputData()} implementieren. Außerdem muss
 * {@link #acceptInput()} implementiert werden. Die Methode
 * {@link #resetInput()} stellt schon eine Standard-Implementierung bereit, kann
 * aber nach Belieben überschrieben werden.
 *
 * @author Alexander Thelen
 * @version 1.0
 */
public abstract class FormViewController extends ViewController {
	/**
	 * Erstellt eine {@code FormViewController}-Instanz mit einem Namen (für
	 * Unterklassen).
	 *
	 * @param name
	 *            Name des FormViewControllers.
	 * @param urlOfView
	 *            URL des Views zur fxml-Datei.
	 */
	protected FormViewController(String name, URL urlOfView) {
		super(name, urlOfView);
	}

	/**
	 * Gibt eine Liste aller Eingabefelder zurück.
	 *
	 * Diese Liste müssen Unterklassen explizit anlegen.
	 *
	 * @return Liste aller Eingabefelder.
	 */
	public abstract ArrayList<Control> getInputControls();

	/**
	 * Gibt alle Daten der Eingabefelder zurück.
	 *
	 * Diese Datenliste müssen Unterklassen explizit anlegen.
	 *
	 * @return Liste aller Daten der Eingabefelder.
	 */
	public abstract Data getInputData();

	/**
	 * Setzt alle Eingabefelder zurück.
	 */
	@FXML
	public void resetInput() {
		for (Control control : getInputControls()) {
			if (control instanceof TextInputControl) {
				((TextInputControl) control).setText(null);
			} else if (control instanceof ToggleButton) {
				((ToggleButton) control).setSelected(true);
			}
		}
		getInputControls().get(0).requestFocus();
	}

	/**
	 * Wird ausgeführt, wenn die Eingabe bestätigt wird.
	 *
	 * Unterklassen sind für die Prüfung der Eingaben verantwortlich.
	 */
	@FXML
	public abstract void acceptInput();

	/**
	 * Standard-Implementierung der {@link ViewController#viewWillAppear()}, die
	 * das erste Eingabefeld fokussiert.
	 */
	@Override
	public void viewWillAppear() {
		super.viewWillAppear();
		getInputControls().get(0).requestFocus();
	}
}
