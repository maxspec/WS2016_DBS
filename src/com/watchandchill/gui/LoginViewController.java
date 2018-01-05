package com.watchandchill.gui;

import java.io.IOException;
import java.util.ArrayList;

import com.alexanderthelen.applicationkit.database.Data;

import javafx.fxml.FXML;
import javafx.scene.control.Control;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginViewController extends com.alexanderthelen.applicationkit.gui.LoginViewController {
	@FXML
	protected TextField usernameTextField;
	@FXML
	protected PasswordField passwordPasswordField;

	public static LoginViewController createWithName(String name) throws IOException {
		LoginViewController viewController = new LoginViewController(name);
		viewController.loadView();
		return viewController;
	}

	protected LoginViewController(String name) {
		super(name, LoginViewController.class.getResource("LoginView.fxml"));
	}

	@Override
	public ArrayList<Control> getInputControls() {
		ArrayList<Control> inputControls = new ArrayList<>();
		inputControls.add(usernameTextField);
		inputControls.add(passwordPasswordField);
		return inputControls;
	}

	@Override
	public Data getInputData() {
		Data data = new Data();
		data.put("username", usernameTextField.getText());
		data.put("password", passwordPasswordField.getText());
		return data;
	}
}
