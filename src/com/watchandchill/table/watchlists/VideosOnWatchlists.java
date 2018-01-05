package com.watchandchill.table.watchlists;

import com.alexanderthelen.applicationkit.database.Data;
import com.alexanderthelen.applicationkit.database.Table;
import com.watchandchill.Application;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VideosOnWatchlists extends Table {
    @Override
    public String getSelectQueryForTableWithFilter(String filter) throws SQLException {
        String selectQuery = "SELECT b.videoID, v.Bezeichnung AS \"Video\", b.WatchlistID, w.Bezeichnung AS \"Watchlist Titel\" FROM Video v, Beinhaltet b, Watchlist w WHERE v.VideoID=b.VideoID AND b.WatchlistID=w.WatchlistID ";
        selectQuery +=" AND (w.Privat=0 OR w.Premiumnutzer=\"" + Application.getInstance().getData().get("username") + "\" )";
        if (filter != null && !filter.isEmpty()) {
            selectQuery += " AND w.Bezeichnung LIKE '%" + filter + "%'";
        }
        return selectQuery;
    }

    @Override
    public String getSelectQueryForRowWithData(Data data) throws SQLException {
        //System.out.println(Application.getInstance().getData());
        //System.out.println(data);
        String selectQuery = "SELECT b.WatchlistID, w.Bezeichnung, b.VideoID, v.Bezeichnung FROM Video v, Beinhaltet b, Watchlist w WHERE v.VideoID=b.VideoID AND b.WatchlistID=w.WatchlistID AND v.Bezeichnung=\"" + data.get("Video.Video") + "\"  AND w.WatchlistID = " + data.get("Beinhaltet.WatchlistID");
        return selectQuery;
    }

    @Override
    public void insertRowWithData(Data data) throws SQLException {
        if ((Integer) Application.getInstance().getData().get("permission") < 1) {
            throw new SQLException(getClass().getName() + " Nicht die notwendigen Rechte.");
        }

        boolean wlExists=false;
        ResultSet resultSetWl=Application.getInstance().getConnection().executeQuery("SELECT * FROM Watchlist WHERE WatchlistID = " + data.get("Beinhaltet.WatchlistID"));
        while ( resultSetWl.next() ) {
            wlExists=true;
        }
        if (!wlExists) {
            throw new SQLException(getClass().getName() + " Watchlist existiert nicht.");
        }

        boolean gleicherBenutzer=false;
        ResultSet resultSet=Application.getInstance().getConnection().executeQuery("SELECT * FROM Watchlist WHERE WatchlistID = " + data.get("Beinhaltet.WatchlistID") + " AND Premiumnutzer =\"" + Application.getInstance().getData().get("username") + "\";");
        while ( resultSet.next() ) {
            gleicherBenutzer=true;
        }
        if (!gleicherBenutzer) {
            throw new SQLException(getClass().getName() + " Nicht der gleiche Nutzer.");
        }

        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("INSERT INTO Beinhaltet(WatchlistID, VideoID) VALUES (?, ?)");
        preparedStatement.setObject(1, data.get("Beinhaltet.WatchlistID"));
        preparedStatement.setObject(2, data.get("Beinhaltet.VideoID"));
        preparedStatement.executeUpdate();
    }

    @Override
    public void updateRowWithData(Data oldData, Data newData) throws SQLException {
        //System.out.println(oldData);
        //System.out.println(newData);
        if ((Integer) Application.getInstance().getData().get("permission") < 1) {
            throw new SQLException(getClass().getName() + " Nicht die notwendigen Rechte.");
        } else {
            boolean gleicherBenutzer=false;
            ResultSet resultSet=Application.getInstance().getConnection().executeQuery("SELECT * FROM Watchlist WHERE WatchlistID = " + oldData.get("Beinhaltet.WatchlistID") + " AND Premiumnutzer =\"" + Application.getInstance().getData().get("username") + "\";");
            while ( resultSet.next() ) {
                gleicherBenutzer=true;
            }
            if (!gleicherBenutzer) {
                throw new SQLException(getClass().getName() + " Nicht der gleiche Nutzer.");
            }
        }
        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("UPDATE Beinhaltet SET VideoID = ? WHERE WatchlistID = ? AND VideoID = ?");
        preparedStatement.setObject(1, newData.get("Beinhaltet.VideoID"));
        preparedStatement.setObject(2, oldData.get("Beinhaltet.WatchlistID"));
        preparedStatement.setObject(3, oldData.get("Beinhaltet.VideoID"));
        preparedStatement.executeUpdate();
    }

    @Override
    public void deleteRowWithData(Data data) throws SQLException {
        if ((Integer) Application.getInstance().getData().get("permission") < 1) {
            throw new SQLException(getClass().getName() + " Nicht die notwendigen Rechte.");
        } else {
            boolean gleicherBenutzer=false;
            ResultSet resultSet=Application.getInstance().getConnection().executeQuery("SELECT * FROM Watchlist WHERE WatchlistID = " + data.get("Beinhaltet.WatchlistID") + " AND Premiumnutzer =\"" + Application.getInstance().getData().get("username") + "\";");
            while ( resultSet.next() ) {
                gleicherBenutzer=true;
            }
            if (!gleicherBenutzer) {
                throw new SQLException(getClass().getName() + " Nicht der gleiche Nutzer.");
            }
        }

        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("DELETE FROM Beinhaltet WHERE WatchlistID = ? AND VideoID = ?");
        preparedStatement.setObject(1, data.get("Beinhaltet.WatchlistID"));
        preparedStatement.setObject(2, data.get("Beinhaltet.VideoID"));
        preparedStatement.executeUpdate();
    }
}
