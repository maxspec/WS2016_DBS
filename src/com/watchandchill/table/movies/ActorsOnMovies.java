package com.watchandchill.table.movies;

import com.alexanderthelen.applicationkit.database.Data;
import com.alexanderthelen.applicationkit.database.Table;
import com.watchandchill.Application;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ActorsOnMovies extends Table {
    @Override
    public String getSelectQueryForTableWithFilter(String filter) throws SQLException {
        String selectQuery = "SELECT v.Bezeichnung, a.FilmID, a.Schauspieler, sch.Vorname, sch.Nachname, sch.Kuenstlername FROM Video v, ArbeitenAnFilm a, Schauspieler sch WHERE v.VideoID=a.FilmID AND a.Schauspieler=sch.Benutzername";
        if (filter != null && !filter.isEmpty()) {
            selectQuery += " AND (v.Bezeichnung LIKE '%" + filter + "%'";
            selectQuery += " OR sch.Vorname LIKE '%" + filter + "%'";
            selectQuery += " OR sch.Nachname LIKE '%" + filter + "%'";
            selectQuery += " OR sch.Kuenstlername LIKE '%" + filter + "%')";
        }
        return selectQuery;
    }

    @Override
    public String getSelectQueryForRowWithData(Data data) throws SQLException {
        //System.out.println(data);
        String selectQuery = "SELECT * FROM ArbeitenAnFilm WHERE FilmID = " + data.get("ArbeitenAnFilm.FilmID") + " AND Schauspieler = \" " +  data.get("ArbeitenAnFilm.Schauspieler") + "\" ";
        return selectQuery;
    }

    @Override
    public void insertRowWithData(Data data) throws SQLException {
        if ((Integer) Application.getInstance().getData().get("permission") < 2) {
            throw new SQLException(getClass().getName() + " Nicht die notwendigen Rechte. Nur Schauspieler dürfen sich eintragen.");
        }
        //System.out.println(data);
        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("INSERT INTO ArbeitenAnFilm(Schauspieler, FilmID) VALUES (?, ?)");
        preparedStatement.setObject(1, Application.getInstance().getData().get("username"));
        preparedStatement.setObject(2, data.get("ArbeitenAnFilm.FilmID"));
        preparedStatement.executeUpdate();
    }

    @Override
    public void updateRowWithData(Data oldData, Data newData) throws SQLException {
        //System.out.println(oldData);
        //System.out.println(newData);
        if (!Application.getInstance().getData().get("username").equals(oldData.get("ArbeitenAnFilm.Schauspieler"))) {
            throw new SQLException(getClass().getName() + " Nicht der gleiche Nutzer.");
        }

        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("UPDATE ArbeitenAnFilm SET FilmID = ? WHERE FilmID = ? AND Schauspieler = ?");
        preparedStatement.setObject(1, newData.get("ArbeitenAnFilm.FilmID"));
        preparedStatement.setObject(2, oldData.get("ArbeitenAnFilm.FilmID"));
        preparedStatement.setObject(3, oldData.get("ArbeitenAnFilm.Schauspieler"));
        preparedStatement.executeUpdate();
    }

    @Override
    public void deleteRowWithData(Data data) throws SQLException {
        //System.out.println(data);
        if (!Application.getInstance().getData().get("username").equals(data.get("ArbeitenAnFilm.Schauspieler"))) {
            throw new SQLException(getClass().getName() + " Nicht der gleiche Nutzer.");
        }

        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("DELETE FROM ArbeitenAnFilm WHERE FilmID = ? AND Schauspieler = ? ");
        preparedStatement.setObject(1, data.get("ArbeitenAnFilm.FilmID"));
        preparedStatement.setObject(2, data.get("ArbeitenAnFilm.Schauspieler"));
        preparedStatement.executeUpdate();
    }
}
