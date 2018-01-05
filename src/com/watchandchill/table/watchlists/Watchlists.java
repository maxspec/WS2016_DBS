package com.watchandchill.table.watchlists;

import com.alexanderthelen.applicationkit.database.Data;
import com.alexanderthelen.applicationkit.database.Table;
import com.watchandchill.Application;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Watchlists extends Table {
    @Override
    public String getSelectQueryForTableWithFilter(String filter) throws SQLException {
        String selectQuery = "SELECT WatchlistID, Bezeichnung AS Titel, Privat AS \"Privat?\", Premiumnutzer FROM Watchlist";
        selectQuery += " WHERE (Privat=0 OR Premiumnutzer=\"" + Application.getInstance().getData().get("username") + "\" )";
        if (filter != null && !filter.isEmpty()) {
            selectQuery += " AND (Premiumnutzer LIKE '%" + filter + "%'";
            selectQuery += " OR Bezeichnung LIKE '%" + filter + "%')";
        }
        return selectQuery;
    }

    @Override
    public String getSelectQueryForRowWithData(Data data) throws SQLException {
        //System.out.println(Application.getInstance().getData());
        String selectQuery = "SELECT WatchlistID AS \"ID von Watchlist\", Bezeichnung, Privat, Premiumnutzer FROM Watchlist WHERE WatchlistID = " + data.get("Watchlist.WatchlistID");
        return selectQuery;
    }

    @Override
    public void insertRowWithData(Data data) throws SQLException {
        if ((Integer) Application.getInstance().getData().get("permission") < 1) {
            throw new SQLException(getClass().getName() + " Nicht die notwendigen Rechte.");
        }

        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("INSERT INTO Watchlist(Bezeichnung, Privat, Premiumnutzer) VALUES (?, ?, ?)");
        preparedStatement.setObject(1, data.get("Watchlist.Bezeichnung"));
        preparedStatement.setObject(2, data.get("Watchlist.Privat"));
        preparedStatement.setObject(3, Application.getInstance().getData().get("username"));
        preparedStatement.executeUpdate();
        /*preparedStatement.getGeneratedKeys().next();
        int index = preparedStatement.getGeneratedKeys().getInt(1);*/
    }

    @Override
    public void updateRowWithData(Data oldData, Data newData) throws SQLException {
        if ((Integer) Application.getInstance().getData().get("permission") < 1) {
            throw new SQLException(getClass().getName() + " Nicht die notwendigen Rechte.");
        } else if (!Application.getInstance().getData().get("username").equals(oldData.get("Watchlist.Premiumnutzer"))) {
            throw new SQLException(getClass().getName() + " Nicht der gleiche Nutzer.");
        }

        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("UPDATE Watchlist SET Bezeichnung = ?, Privat = ? WHERE WatchlistID = ?");
        preparedStatement.setObject(1, newData.get("Watchlist.Bezeichnung"));
        preparedStatement.setObject(2, newData.get("Watchlist.Privat"));
        preparedStatement.setObject(3, oldData.get("Watchlist.ID von Watchlist"));
        preparedStatement.executeUpdate();
    }

    @Override
    public void deleteRowWithData(Data data) throws SQLException {
        if ((Integer) Application.getInstance().getData().get("permission") < 1) {
            throw new SQLException(getClass().getName() + " Nicht die notwendigen Rechte.");
        } else if (!Application.getInstance().getData().get("username").equals(data.get("Watchlist.Premiumnutzer"))) {
            throw new SQLException(getClass().getName() + " Nicht der gleiche Nutzer.");
        }

        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("DELETE FROM Watchlist WHERE WatchlistID = ?");
        preparedStatement.setObject(1, data.get("Watchlist.WatchlistID"));
        preparedStatement.executeUpdate();
    }
}
