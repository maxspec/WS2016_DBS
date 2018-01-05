package com.watchandchill.table.movies;

import com.alexanderthelen.applicationkit.database.Data;
import com.alexanderthelen.applicationkit.database.Table;
import com.watchandchill.Application;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RatingsOnMovies extends Table {
    @Override
    public String getSelectQueryForTableWithFilter(String filter) throws SQLException {
        String selectQuery = "SELECT b.Benutzer, b.Bewertungspunkte, f.VideoID as VideoID, v.Bezeichnung AS Film, v.Spieldauer, v.Erscheinungsjahr FROM Bewertung b, Film f, video v WHERE b.FilmID=f.VideoID AND f.VideoID=v.VideoID";
        if (filter != null && !filter.isEmpty()) {
            selectQuery += " AND v.Bezeichnung LIKE '%" + filter + "%'";
        }
        return selectQuery;
    }

    @Override
    public String getSelectQueryForRowWithData(Data data) throws SQLException {
        String selectQuery = "SELECT Benutzer, FilmID, Bewertungspunkte FROM Bewertung WHERE FilmID = " + data.get("Film.VideoID") + " AND Benutzer=\"" + data.get("Bewertung.Benutzer") + "\"";
        return selectQuery;
    }

    @Override
    public void insertRowWithData(Data data) throws SQLException {
        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("INSERT INTO Bewertung(Benutzer, FilmID, Bewertungspunkte) VALUES (?, ?, ?)");
        preparedStatement.setObject(1, Application.getInstance().getData().get("username"));
        preparedStatement.setObject(2, data.get("Bewertung.FilmID"));
        preparedStatement.setObject(3, data.get("Bewertung.Bewertungspunkte"));
        preparedStatement.executeUpdate();
    }

    @Override
    public void updateRowWithData(Data oldData, Data newData) throws SQLException {
        if (!Application.getInstance().getData().get("username").equals(oldData.get("Bewertung.Benutzer"))) {
            throw new SQLException(getClass().getName() + " Nicht der gleiche Nutzer.");
        }
        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("UPDATE Bewertung SET Bewertungspunkte = ? WHERE FilmID = ? AND Benutzer = ?");
        preparedStatement.setObject(1, newData.get("Bewertung.Bewertungspunkte"));
        preparedStatement.setObject(2, oldData.get("Bewertung.FilmID"));
        preparedStatement.setObject(3, oldData.get("Bewertung.Benutzer"));
        preparedStatement.executeUpdate();
    }

    @Override
    public void deleteRowWithData(Data data) throws SQLException {
        if (!Application.getInstance().getData().get("username").equals(data.get("Bewertung.Benutzer"))) {
            throw new SQLException(getClass().getName() + " Nicht der gleiche Nutzer.");
        }
        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("DELETE FROM Bewertung WHERE Benutzer = ? AND FilmID = ?");
        preparedStatement.setObject(1, data.get("Bewertung.Benutzer"));
        preparedStatement.setObject(2, data.get("Film.VideoID"));
        preparedStatement.executeUpdate();
    }
}
