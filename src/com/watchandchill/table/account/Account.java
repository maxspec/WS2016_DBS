package com.watchandchill.table.account;

import com.alexanderthelen.applicationkit.database.Data;
import com.alexanderthelen.applicationkit.database.Table;
import com.watchandchill.Application;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class Account extends Table {
    @Override
    public String getSelectQueryForTableWithFilter(String filter) throws SQLException {

        String selectQuery=null;
        switch ((Integer) Application.getInstance().getData().get("permission")) {
            case 0:
                selectQuery = "SELECT n.Benutzername, n.Email, n.Passwort, p.Benutzername AS Premium, sch.Benutzername AS Schauspieler, sch.Vorname, sch.Nachname, sch.Geburtsdatum, sch.Geburtsort, sch.Kuenstlername, sch.Beschreibung FROM Nutzer n LEFT JOIN Premiumnutzer p ON n.Benutzername=p.Benutzername LEFT JOIN Schauspieler sch ON n.Benutzername=sch.Benutzername  WHERE n.Benutzername=\""+ Application.getInstance().getData().get("username") + "\"";
                break;
            case 1:
                selectQuery = "SELECT n.Benutzername, n.Email, n.Passwort, p.Benutzername AS Premium, sch.Benutzername AS Schauspieler, sch.Vorname, sch.Nachname, sch.Geburtsdatum, sch.Geburtsort, sch.Kuenstlername, sch.Beschreibung FROM Nutzer n, Premiumnutzer p LEFT JOIN Schauspieler sch ON n.Benutzername=sch.Benutzername WHERE n.Benutzername=p.Benutzername AND n.Benutzername=\""+ Application.getInstance().getData().get("username") + "\"";
                break;
            case 2:
                selectQuery = "SELECT n.Benutzername, n.Email, n.Passwort, p.Benutzername AS Premium, sch.Benutzername AS Schauspieler, sch.Vorname, sch.Nachname, sch.Geburtsdatum, sch.Geburtsort, sch.Kuenstlername, sch.Beschreibung FROM Nutzer n, Premiumnutzer p, Schauspieler sch WHERE n.Benutzername=p.Benutzername AND n.Benutzername=\""+ Application.getInstance().getData().get("username") + "\" AND n.Benutzername=sch.Benutzername ";
                break;
        }
        return selectQuery;
    }

    @Override
    public String getSelectQueryForRowWithData(Data data) throws SQLException {
        String selectQuery=null;
        switch ((Integer) Application.getInstance().getData().get("permission")) {
            case 0:
                selectQuery = "SELECT n.Benutzername, n.Email, n.Passwort, p.Benutzername AS Premium, sch.Benutzername AS Schauspieler, sch.Vorname, sch.Nachname, sch.Geburtsdatum, sch.Geburtsort, sch.Kuenstlername, sch.Beschreibung FROM Nutzer n LEFT JOIN Premiumnutzer p ON n.Benutzername=p.Benutzername LEFT JOIN Schauspieler sch ON n.Benutzername=sch.Benutzername  WHERE n.Benutzername=\""+ Application.getInstance().getData().get("username") + "\"";
                break;
            case 1:
                selectQuery = "SELECT n.Benutzername, n.Email, n.Passwort, p.Benutzername AS Premium, sch.Benutzername AS Schauspieler, sch.Vorname, sch.Nachname, sch.Geburtsdatum, sch.Geburtsort, sch.Kuenstlername, sch.Beschreibung FROM Nutzer n, Premiumnutzer p LEFT JOIN Schauspieler sch ON n.Benutzername=sch.Benutzername WHERE n.Benutzername=p.Benutzername AND n.Benutzername=\""+ Application.getInstance().getData().get("username") + "\"";
                break;
            case 2:
                selectQuery = "SELECT n.Benutzername, n.Email, n.Passwort, p.Benutzername AS Premium, sch.Benutzername AS Schauspieler, sch.Vorname, sch.Nachname, sch.Geburtsdatum, sch.Geburtsort, sch.Kuenstlername, sch.Beschreibung FROM Nutzer n, Premiumnutzer p, Schauspieler sch WHERE n.Benutzername=p.Benutzername AND n.Benutzername=\""+ Application.getInstance().getData().get("username") + "\" AND n.Benutzername=sch.Benutzername ";
                break;
        }
        return selectQuery;
    }

    @Override
    public void insertRowWithData(Data data) throws SQLException {
        throw new SQLException(getClass().getName() + "Einfügen nicht erlaubt.");
    }

    @Override
    public void updateRowWithData(Data oldData, Data newData) throws SQLException {
        //System.out.println(oldData);
        //System.out.println(newData);

        String email=newData.get("Nutzer.Email").toString();

        //System.out.println(newData.get("Nutzer.Email").toString());
        if (Pattern.matches("\\w+@\\w+[.]\\w+", email)) {
            //System.out.println("Email ist gültig.");
            PreparedStatement preparedStatementUser = Application.getInstance().getConnection().prepareStatement("UPDATE Nutzer SET Passwort = ?, Email = ? WHERE Benutzername = ? ");
            preparedStatementUser.setObject(1, newData.get("Nutzer.Passwort"));
            preparedStatementUser.setObject(2, newData.get("Nutzer.Email"));
            preparedStatementUser.setObject(3, Application.getInstance().getData().get("username"));
            preparedStatementUser.executeUpdate();
        } else {
            //System.out.println("Email ist nicht gültig.");
            throw new SQLException("Email ungültig.");
        }


        switch ((Integer) Application.getInstance().getData().get("permission")) {
            case 0:
                if (newData.get("Premiumnutzer.Premium")==null) {

                    if (newData.get("Schauspieler.Schauspieler")==null) {
                        //System.out.println("Keine Änderungen.");
                    } else {
                        //System.out.println("Schauspieler ausgefüllt, aber nicht Premium.");
                        throw new SQLException(getClass().getName() + "Premium-Feld muss ausgefüllt sein, um Schauspieler werden zu können.");
                    }

                } else {

                    if (newData.get("Schauspieler.Schauspieler")==null) {
                        //System.out.println("Von Nutzer zu Premiumnutzer ändern.");
                        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("INSERT INTO Premiumnutzer(Benutzername) VALUES (?)");
                        preparedStatement.setObject(1, Application.getInstance().getData().get("username"));
                        preparedStatement.executeUpdate();
                        Application.getInstance().getData().put("permission",1);
                        //System.out.println(Application.getInstance().getData().get("permission"));
                    } else {
                        //System.out.println("Von Nutzer zu Schauspieler ändern.");
                        PreparedStatement preparedStatementPremium = Application.getInstance().getConnection().prepareStatement("INSERT INTO Premiumnutzer(Benutzername) VALUES (?)");
                        preparedStatementPremium.setObject(1, Application.getInstance().getData().get("username"));
                        preparedStatementPremium.executeUpdate();
                        Application.getInstance().getData().put("permission",1);
                        //System.out.println(Application.getInstance().getData().get("permission"));

                        if (newData.get("Schauspieler.Vorname")==null || newData.get("Schauspieler.Nachname")==null ||newData.get("Schauspieler.Geburtsdatum")==null || newData.get("Schauspieler.Geburtsort")==null) {
                            throw new SQLException("Daten für Schauspieler eigeben.");
                        }

                        PreparedStatement preparedStatementActor = Application.getInstance().getConnection().prepareStatement("INSERT INTO Schauspieler(Benutzername, Vorname, Nachname, Geburtsdatum, Geburtsort, Kuenstlername, Beschreibung) VALUES (?, ?, ?, ?, ?, ?, ?)");
                        preparedStatementActor.setObject(1, Application.getInstance().getData().get("username"));
                        preparedStatementActor.setObject(2, newData.get("Schauspieler.Vorname"));
                        preparedStatementActor.setObject(3, newData.get("Schauspieler.Nachname"));
                        preparedStatementActor.setObject(4, newData.get("Schauspieler.Geburtsdatum"));
                        preparedStatementActor.setObject(5, newData.get("Schauspieler.Geburtsort"));
                        preparedStatementActor.setObject(6, newData.get("Schauspieler.Kuenstlername"));
                        preparedStatementActor.setObject(7, newData.get("Schauspieler.Beschreibung"));
                        preparedStatementActor.executeUpdate();
                        Application.getInstance().getData().put("permission",2);
                        //System.out.println(Application.getInstance().getData().get("permission"));
                    }

                }

                break;
            case 1:
                if (newData.get("Premiumnutzer.Premium")==null) {

                    if (newData.get("Schauspieler.Schauspieler")==null) {
                        //System.out.println("Von Premiumnutzer zu Nutzer ändern.");
                        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("DELETE FROM Premiumnutzer WHERE Benutzername = ? ");
                        preparedStatement.setObject(1, Application.getInstance().getData().get("username"));
                        preparedStatement.executeUpdate();
                        Application.getInstance().getData().put("permission",0);
                        //System.out.println(Application.getInstance().getData().get("permission"));
                    } else {
                        //System.out.println("Von Premiumnutzer zu Nutzer ändern.");
                        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("DELETE FROM Premiumnutzer WHERE Benutzername = ? ");
                        preparedStatement.setObject(1, Application.getInstance().getData().get("username"));
                        preparedStatement.executeUpdate();
                        Application.getInstance().getData().put("permission",0);
                        //System.out.println(Application.getInstance().getData().get("permission"));

                        //System.out.println("Schauspieler ausgefüllt, aber nicht mehr Premiumnutzer.");
                        throw new SQLException(getClass().getName() + "Premium-Feld muss ausgefüllt sein, um Schauspieler werden zu können.");
                    }

                } else {

                    if (newData.get("Schauspieler.Schauspieler")==null) {
                        //System.out.println("Keine Rechteänderungen.");
                    } else {
                        //System.out.println("Von Premiumnutzer zu Schauspieler ändern.");
                        if (newData.get("Schauspieler.Vorname")==null || newData.get("Schauspieler.Nachname")==null ||newData.get("Schauspieler.Geburtsdatum")==null || newData.get("Schauspieler.Geburtsort")==null) {
                            throw new SQLException("Daten für Schauspieler eigeben.");
                        }
                        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("INSERT INTO Schauspieler(Benutzername, Vorname, Nachname, Geburtsdatum, Geburtsort, Kuenstlername, Beschreibung) VALUES (?, ?, ?, ?, ?, ?, ?)");
                        preparedStatement.setObject(1, Application.getInstance().getData().get("username"));
                        preparedStatement.setObject(2, newData.get("Schauspieler.Vorname"));
                        preparedStatement.setObject(3, newData.get("Schauspieler.Nachname"));
                        preparedStatement.setObject(4, newData.get("Schauspieler.Geburtsdatum"));
                        preparedStatement.setObject(5, newData.get("Schauspieler.Geburtsort"));
                        preparedStatement.setObject(6, newData.get("Schauspieler.Kuenstlername"));
                        preparedStatement.setObject(7, newData.get("Schauspieler.Beschreibung"));
                        preparedStatement.executeUpdate();
                        Application.getInstance().getData().put("permission",2);
                        //System.out.println(Application.getInstance().getData().get("permission"));
                    }

                }
                break;
            case 2:
                if (newData.get("Premiumnutzer.Premium")==null) {

                    if (newData.get("Schauspieler.Schauspieler")==null) {
                        //System.out.println("Von Schauspieler zu Nutzer ändern.");
                        PreparedStatement preparedStatementActor = Application.getInstance().getConnection().prepareStatement("DELETE FROM Schauspieler WHERE Benutzername = ? ");
                        preparedStatementActor.setObject(1, Application.getInstance().getData().get("username"));
                        preparedStatementActor.executeUpdate();

                        PreparedStatement preparedStatementPremium = Application.getInstance().getConnection().prepareStatement("DELETE FROM Premiumnutzer WHERE Benutzername = ? ");
                        preparedStatementPremium.setObject(1, Application.getInstance().getData().get("username"));
                        preparedStatementPremium.executeUpdate();

                        Application.getInstance().getData().put("permission",0);
                        //System.out.println(Application.getInstance().getData().get("permission"));
                    } else {
                        //System.out.println("Von Premiumnutzer zu Nutzer ändern.");
                        throw new SQLException("Um von Schauspieler zu Nutzer zu wechseln, muss auch Schauspieler-Feld leer sein.");
                    }

                } else {

                    if (newData.get("Schauspieler.Schauspieler")==null) {
                        //System.out.println("Von Schauspieler zu Premiumnutzer ändern.");
                        PreparedStatement preparedStatementActor = Application.getInstance().getConnection().prepareStatement("DELETE FROM Schauspieler WHERE Benutzername = ? ");
                        preparedStatementActor.setObject(1, Application.getInstance().getData().get("username"));
                        preparedStatementActor.executeUpdate();
                        Application.getInstance().getData().put("permission",1);
                        //System.out.println(Application.getInstance().getData().get("permission"));
                    } else {
                        //System.out.println("Keine Rechteänderung.");
                        if (newData.get("Schauspieler.Vorname")==null || newData.get("Schauspieler.Nachname")==null ||newData.get("Schauspieler.Geburtsdatum")==null || newData.get("Schauspieler.Geburtsort")==null) {
                            throw new SQLException("Daten für Schauspieler eigeben.");
                        }
                        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("UPDATE Schauspieler SET Vorname = ?, Nachname = ?, Geburtsdatum = ?, Geburtsort = ?, Kuenstlername = ?, Beschreibung = ? WHERE Benutzername = ? ");
                        preparedStatement.setObject(1, newData.get("Schauspieler.Vorname"));
                        preparedStatement.setObject(2, newData.get("Schauspieler.Nachname"));
                        preparedStatement.setObject(3, newData.get("Schauspieler.Geburtsdatum"));
                        preparedStatement.setObject(4, newData.get("Schauspieler.Geburtsort"));
                        preparedStatement.setObject(5, newData.get("Schauspieler.Kuenstlername"));
                        preparedStatement.setObject(6, newData.get("Schauspieler.Beschreibung"));
                        preparedStatement.setObject(7, Application.getInstance().getData().get("username"));
                        preparedStatement.executeUpdate();
                    }

                }
                break;
        }
    }

    @Override
    public void deleteRowWithData(Data data) throws SQLException {
        throw new SQLException(getClass().getName() + "Löschen nicht erlaubt.");
    }
}
