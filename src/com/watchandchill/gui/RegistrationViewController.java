package com.watchandchill.gui;

import java.io.IOException;
import java.util.ArrayList;

import com.alexanderthelen.applicationkit.database.Data;

import javafx.fxml.FXML;
import javafx.scene.control.Control;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class RegistrationViewController extends com.alexanderthelen.applicationkit.gui.RegistrationViewController {
	@FXML
	protected TextField usernameTextField;
	@FXML
	protected TextField emailTextField;
	@FXML
	protected PasswordField passwordTextField;
	@FXML
	protected RadioButton yesActorRadioButton;
	@FXML
	protected RadioButton noActorRadioButton;
	@FXML
	protected ToggleGroup actorToggleGroup;
	@FXML
	protected TextField firstNameTextField;
	@FXML
	protected TextField lastNameTextField;
	@FXML
	protected TextField aliasTextField;
	@FXML
	protected TextField birthdateTextField;
	@FXML
	protected TextField birthplaceTextField;
	@FXML
	protected RadioButton yesPremiumRadioButton;
	@FXML
	protected RadioButton noPremiumRadioButton;
	@FXML
	protected ToggleGroup premiumToggleGroup;

	public static RegistrationViewController createWithName(String name) throws IOException {
		RegistrationViewController viewController = new RegistrationViewController(name);
		viewController.loadView();
		return viewController;
	}

	protected RegistrationViewController(String name) {
		super(name, RegistrationViewController.class.getResource("RegistrationView.fxml"));
	}

	@Override
	@FXML
	protected void initialize() {
		yesActorRadioButton.setUserData(true);
		noActorRadioButton.setUserData(false);
		yesPremiumRadioButton.setUserData(true);
		noPremiumRadioButton.setUserData(false);
		actorToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue == yesActorRadioButton) {
				firstNameTextField.setDisable(false);
				lastNameTextField.setDisable(false);
				aliasTextField.setDisable(false);
				birthdateTextField.setDisable(false);
				birthplaceTextField.setDisable(false);
				yesPremiumRadioButton.setSelected(true);
				yesPremiumRadioButton.setDisable(true);
				noPremiumRadioButton.setDisable(true);
			} else {
				firstNameTextField.setDisable(true);
				lastNameTextField.setDisable(true);
				aliasTextField.setDisable(true);
				birthdateTextField.setDisable(true);
				birthplaceTextField.setDisable(true);
				noPremiumRadioButton.setSelected(true);
				yesPremiumRadioButton.setDisable(false);
				noPremiumRadioButton.setDisable(false);
			}
		});
	}

	@Override
	public ArrayList<Control> getInputControls() {
		ArrayList<Control> inputControls = new ArrayList<>();
		inputControls.add(usernameTextField);
		inputControls.add(emailTextField);
		inputControls.add(passwordTextField);
		inputControls.add(yesActorRadioButton);
		inputControls.add(noActorRadioButton);
		inputControls.add(firstNameTextField);
		inputControls.add(lastNameTextField);
		inputControls.add(aliasTextField);
		inputControls.add(birthdateTextField);
		inputControls.add(birthplaceTextField);
		inputControls.add(yesPremiumRadioButton);
		inputControls.add(noPremiumRadioButton);
		return inputControls;
	}

	@Override
	public Data getInputData() {
		Data data = new Data();
		data.put("username", usernameTextField.getText() == null ? null : usernameTextField.getText());
		data.put("email", emailTextField.getText() == null ? null : emailTextField.getText());
		data.put("password", passwordTextField.getText() == null ? null : passwordTextField.getText());
		data.put("firstName", firstNameTextField.getText() == null ? null : firstNameTextField.getText());
		data.put("lastName", lastNameTextField.getText() == null ? null : lastNameTextField.getText());
		data.put("alias", aliasTextField.getText() == null ? null : aliasTextField.getText());
		data.put("birthdate", birthdateTextField.getText() == null ? null : birthdateTextField.getText());
		data.put("birthplace", birthplaceTextField.getText() == null ? null : birthplaceTextField.getText());
		data.put("isActor", actorToggleGroup.getSelectedToggle().getUserData());
		data.put("isPremium", premiumToggleGroup.getSelectedToggle().getUserData());
		return data;
	}
}
