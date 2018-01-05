package com.watchandchill.table.videos;

import com.alexanderthelen.applicationkit.database.Data;
import com.alexanderthelen.applicationkit.database.Table;
import com.watchandchill.Application;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Maxim on 28.03.2017.
 */
public class Videos extends Table {
    @Override
    public String getSelectQueryForTableWithFilter(String filter) throws SQLException {
        String selectQuery = "SELECT v.VideoID, v.Bezeichnung, v.Spieldauer, v.Erscheinungsjahr, v.Information, p.MBezeichnung AS Medienkonzern, f.Produktionsbudget FROM Video v LEFT JOIN Produzieren p ON v.VideoID=p.VideoID LEFT JOIN Film f ON f.VideoID=v.VideoID ";
        if (filter != null && !filter.isEmpty()) {
            selectQuery += " WHERE v.Bezeichnung LIKE '%" + filter + "%'";
        }
        return selectQuery;
    }

    @Override
    public String getSelectQueryForRowWithData(Data data) throws SQLException {
        String selectQuery = "SELECT v.VideoID, v.Bezeichnung, v.Spieldauer, v.Erscheinungsjahr, v.Information, p.VideoID AS Film, p.MBezeichnung AS Medienkonzern, f.Produktionsbudget FROM Video v LEFT JOIN Produzieren p ON v.VideoID=p.VideoID LEFT JOIN Film f ON f.VideoID=v.VideoID WHERE v.VideoID = " + data.get("Video.VideoID");
        return selectQuery;
    }

    @Override
    public void insertRowWithData(Data data) throws SQLException {
        if ((Integer) Application.getInstance().getData().get("permission") < 2) {
            throw new SQLException(getClass().getName() + " Nicht die notwendigen Rechte. Nur Schauspieler dürfen neue Videos hinzufügen.");
        }
        if (data.get("Produzieren.Film")!=null) {
            if (data.get("Produzieren.Medienkonzern")==null || data.get("Film.Produktionsbudget")==null) {
                throw new SQLException(getClass().getName() + " Wenn Film-Feld nicht leer ist, müssen auch Medienkonzern und Produktionsbudget angegeben werden.");
            }
        }
        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("INSERT INTO Video(Bezeichnung, Spieldauer, Erscheinungsjahr, Information) VALUES (?, ?, ?, ?)");
        preparedStatement.setObject(1, data.get("Video.Bezeichnung"));
        preparedStatement.setObject(2, data.get("Video.Spieldauer"));
        preparedStatement.setObject(3, data.get("Video.Erscheinungsjahr"));
        preparedStatement.setObject(4, data.get("Video.Information"));
        preparedStatement.executeUpdate();

        if (data.get("Produzieren.Film")!=null) {
            ResultSet resultSet=Application.getInstance().getConnection().executeQuery("SELECT VideoID FROM Video WHERE Bezeichnung =\"" + data.get("Video.Bezeichnung") + "\" AND Spieldauer =" + data.get("Video.Spieldauer") + " AND Erscheinungsjahr =" + data.get("Video.Erscheinungsjahr") );
            Integer videoID=null;
            while ( resultSet.next() ) {
                videoID = resultSet.getInt("VideoID");
            }
            PreparedStatement preparedStatementFilm = Application.getInstance().getConnection().prepareStatement("INSERT INTO Film(VideoID, Produktionsbudget) VALUES (?, ?)");
            preparedStatementFilm.setObject(1, videoID);
            preparedStatementFilm.setObject(2, data.get("Film.Produktionsbudget"));
            preparedStatementFilm.executeUpdate();

            PreparedStatement preparedStatementProd = Application.getInstance().getConnection().prepareStatement("INSERT INTO Produzieren(MBezeichnung, VideoID) VALUES (?, ?)");
            preparedStatementProd.setObject(1, data.get("Produzieren.Medienkonzern"));
            preparedStatementProd.setObject(2, videoID);
            preparedStatementProd.executeUpdate();
        }
    }

    @Override
    public void updateRowWithData(Data oldData, Data newData) throws SQLException {
        if ((Integer) Application.getInstance().getData().get("permission") < 2) {
            throw new SQLException(getClass().getName() + " Nicht die notwendigen Rechte. Nur Schauspieler dürfen Videos bearbeiten.");
        }
        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("UPDATE Video SET Bezeichnung = ?, Spieldauer = ?, Erscheinungsjahr = ?, Information = ? WHERE VideoID = ?");
        preparedStatement.setObject(1, newData.get("Video.Bezeichnung"));
        preparedStatement.setObject(2, newData.get("Video.Spieldauer"));
        preparedStatement.setObject(3, newData.get("Video.Erscheinungsjahr"));
        preparedStatement.setObject(4, newData.get("Video.Information"));
        preparedStatement.setObject(5, oldData.get("Video.VideoID"));
        preparedStatement.executeUpdate();
    }

    @Override
    public void deleteRowWithData(Data data) throws SQLException {
        if ((Integer) Application.getInstance().getData().get("permission") < 2) {
            throw new SQLException(getClass().getName() + " Nicht die notwendigen Rechte. Nur Schauspieler dürfen Videos entfernen.");
        }
        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("DELETE FROM Video WHERE VideoID = ?");
        preparedStatement.setObject(1, data.get("Video.VideoID"));
        preparedStatement.executeUpdate();
    }
}
