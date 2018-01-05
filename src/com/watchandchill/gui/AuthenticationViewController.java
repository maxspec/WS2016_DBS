package com.watchandchill.gui;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

import com.alexanderthelen.applicationkit.database.Data;
import com.watchandchill.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class AuthenticationViewController extends com.alexanderthelen.applicationkit.gui.AuthenticationViewController {
	public static AuthenticationViewController createWithName(String name) throws IOException {
		AuthenticationViewController viewController = new AuthenticationViewController(name);
		viewController.loadView();
		return viewController;
	}

	protected AuthenticationViewController(String name) {
		super(name);
	}

	@Override
	public void loginUser(Data data) throws SQLException {
		//System.out.println(data);
		String benutzer=null;
		String passwort=null;
		String email=null;
		ResultSet resultSet=Application.getInstance().getConnection().executeQuery("SELECT Benutzername, Email, Passwort FROM Nutzer WHERE Benutzername =\"" + data.get("username") + "\";");
		while ( resultSet.next() ) {
			benutzer = resultSet.getString("Benutzername");
			email = resultSet.getString("Email");
			passwort = resultSet.getString("Passwort");
		}

		if (data.get("password").toString().equals(passwort)) {
			Application.getInstance().getData().put("username",benutzer);
			Application.getInstance().getData().put("email",email);

			int permission=0;
			ResultSet resultSet2=Application.getInstance().getConnection().executeQuery("SELECT Benutzername FROM Premiumnutzer WHERE Benutzername =\"" + data.get("username") + "\";");
			while ( resultSet2.next() ) {
				permission=1;
			}
			if (permission==1) {
				ResultSet resultSet3=Application.getInstance().getConnection().executeQuery("SELECT Benutzername FROM Schauspieler WHERE Benutzername =\"" + data.get("username") + "\";");
				while ( resultSet3.next() ) {
					permission=2;
				}
			}
			Application.getInstance().getData().put("permission",permission);
			//System.out.println("Rechte: " + permission);

		} else {
			/*presentAlertDialog(Alert.AlertType.ERROR, "Falsche Eingabe",
					"Benutzername oder Passwort falsch, bitte erneut versuchen.", null, ButtonType.OK);*/
			throw new SQLException("Benutzername oder Passwort falsch, bitte erneut versuchen.");
		}
	}

	@Override
	public void registerUser(Data data) throws SQLException {
		//System.out.println(data);

		if (data.get("username").toString().isEmpty() || data.get("email").toString().isEmpty() || data.get("password").toString().isEmpty()) {
			throw new SQLException("Benutzername, Email und Passwort müssen eingegeben werden.");
		} else {
			String email=data.get("email").toString();

			//System.out.println(newData.get("Nutzer.Email").toString());
			if (!Pattern.matches("\\w+@\\w+[.]\\w+", email)) {
				throw new SQLException("Email ungültig.");
			}

			if ((boolean) data.get("isActor")) {
				if (data.get("firstName").toString().isEmpty() || data.get("lastName").toString().isEmpty() || data.get("birthdate").toString().isEmpty() || data.get("birthplace").toString().isEmpty()) {
					throw new SQLException("Daten für Schauspieler eigeben.");
				}
			}

			boolean userExists=false;
			ResultSet resultSet=Application.getInstance().getConnection().executeQuery("SELECT * FROM Nutzer WHERE Benutzername =\"" + data.get("username") + "\";");
			while ( resultSet.next() ) {
				userExists=true;
			}
			/*System.out.println("benutzer eistiert: " + userExists);
			System.out.println("Premium?: " + data.get("isPremium"));
			System.out.println("Actor?: " + data.get("isActor"));*/

			//Eintrag in Nutzer-Tabelle:
			if (userExists) {
				throw new SQLException("Benutzername bereits vergeben.");
			} else {
				PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("INSERT INTO Nutzer(Benutzername, Email, Passwort) VALUES (?, ?, ?)");
				preparedStatement.setObject(1,data.get("username"));
				preparedStatement.setObject(2,data.get("email"));
				preparedStatement.setObject(3,data.get("password"));
				preparedStatement.executeUpdate();
			}

			//Eintrag in Premiumnutzer-Tabelle
			if ((boolean) data.get("isPremium")) {
				PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("INSERT INTO Premiumnutzer(Benutzername) VALUES (?)");
				preparedStatement.setObject(1,data.get("username"));
				preparedStatement.executeUpdate();
			}

			//Eintrag in Schauspieler-Tabelle
			if ((boolean) data.get("isActor")) {
				PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("INSERT INTO Schauspieler(Benutzername, Vorname, Nachname, Geburtsdatum, Geburtsort, Kuenstlername, Beschreibung) VALUES (?, ?, ?, ?, ?, ?, ?)");
				preparedStatement.setObject(1,data.get("username"));
				preparedStatement.setObject(2,data.get("firstName"));
				preparedStatement.setObject(3,data.get("lastName"));
				preparedStatement.setObject(4,data.get("birthdate"));
				preparedStatement.setObject(5,data.get("birthplace"));
				preparedStatement.setObject(6,data.get("alias"));
				preparedStatement.setObject(7,"");
				preparedStatement.executeUpdate();
			}

		}
	}
}
