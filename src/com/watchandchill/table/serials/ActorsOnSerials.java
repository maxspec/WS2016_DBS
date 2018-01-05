package com.watchandchill.table.serials;

import com.alexanderthelen.applicationkit.database.Data;
import com.alexanderthelen.applicationkit.database.Table;
import com.watchandchill.Application;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ActorsOnSerials extends Table {
    @Override
    public String getSelectQueryForTableWithFilter(String filter) throws SQLException {
        String selectQuery = "SELECT se.Name, a.SerieID, sch.Vorname, sch.Nachname, a.Schauspieler AS Benutzername FROM Serie se, ArbeitenAnSerie a, Schauspieler sch WHERE se.SerieID=a.SerieID AND a.Schauspieler=sch.Benutzername";
        if (filter != null && !filter.isEmpty()) {
            selectQuery += " AND (se.Name LIKE '%" + filter + "%'";
            selectQuery += " OR sch.Vorname LIKE '%" + filter + "%'";
            selectQuery += " OR sch.Nachname LIKE '%" + filter + "%'";
            selectQuery += " OR sch.Kuenstlername LIKE '%" + filter + "%')";
        }
        return selectQuery;
    }

    @Override
    public String getSelectQueryForRowWithData(Data data) throws SQLException {
        //System.out.println(data);
        String selectQuery = "SELECT * FROM ArbeitenAnSerie WHERE SerieID = " + data.get("ArbeitenAnSerie.SerieID") + " AND Schauspieler = \" " +  data.get("ArbeitenAnSerie.Benutzername") + "\" ";
        return selectQuery;
    }

    @Override
    public void insertRowWithData(Data data) throws SQLException {
        if ((Integer) Application.getInstance().getData().get("permission") < 2) {
            throw new SQLException(getClass().getName() + " Nicht die notwendigen Rechte. Nur Schauspieler dürfen sich eintragen.");
        }
        //System.out.println(data);
        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("INSERT INTO ArbeitenAnSerie(Schauspieler, SerieID) VALUES (?, ?)");
        preparedStatement.setObject(1, Application.getInstance().getData().get("username"));
        preparedStatement.setObject(2, data.get("ArbeitenAnSerie.SerieID"));
        preparedStatement.executeUpdate();
    }

    @Override
    public void updateRowWithData(Data oldData, Data newData) throws SQLException {
        //System.out.println(oldData);
        //System.out.println(newData);
        if (!Application.getInstance().getData().get("username").equals(oldData.get("ArbeitenAnSerie.Schauspieler"))) {
            throw new SQLException(getClass().getName() + " Nicht der gleiche Nutzer.");
        }

        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("UPDATE ArbeitenAnSerie SET SerieID = ? WHERE SerieID = ? AND Schauspieler = ?");
        preparedStatement.setObject(1, newData.get("ArbeitenAnSerie.SerieID"));
        preparedStatement.setObject(2, oldData.get("ArbeitenAnSerie.SerieID"));
        preparedStatement.setObject(3, oldData.get("ArbeitenAnSerie.Schauspieler"));
        preparedStatement.executeUpdate();
    }

    @Override
    public void deleteRowWithData(Data data) throws SQLException {
        //System.out.println(data);
        if (!Application.getInstance().getData().get("username").equals(data.get("ArbeitenAnSerie.Benutzername"))) {
            throw new SQLException(getClass().getName() + " Nicht der gleiche Nutzer.");
        }

        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("DELETE FROM ArbeitenAnSerie WHERE SerieID = ? AND Schauspieler = ? ");
        preparedStatement.setObject(1, data.get("ArbeitenAnSerie.SerieID"));
        preparedStatement.setObject(2, data.get("ArbeitenAnSerie.Benutzername"));
        preparedStatement.executeUpdate();
    }
}
