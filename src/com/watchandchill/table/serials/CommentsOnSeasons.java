package com.watchandchill.table.serials;

import com.alexanderthelen.applicationkit.database.Data;
import com.alexanderthelen.applicationkit.database.Table;
import com.watchandchill.Application;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CommentsOnSeasons extends Table {
    @Override
    public String getSelectQueryForTableWithFilter(String filter) throws SQLException {
        String selectQuery = "SELECT k.KommentarID, k.Benutzer, k.Kommentar, k.StaffelID, st.Nummer, se.Name FROM KommentarStaffel k, Staffel st, Serie se WHERE k.StaffelID=st.StaffelID AND st.SerieID=se.SerieID";
        if (filter != null && !filter.isEmpty()) {
            selectQuery += " AND (st.StaffelID LIKE '%" + filter + "%'";
            selectQuery += " OR se.Name LIKE '%" + filter + "%')";
        }
        return selectQuery;
    }

    @Override
    public String getSelectQueryForRowWithData(Data data) throws SQLException {
        String selectQuery = "SELECT KommentarID, Benutzer, StaffelID, Kommentar FROM KommentarStaffel WHERE KommentarID = " + data.get("KommentarStaffel.KommentarID");
        return selectQuery;
    }

    @Override
    public void insertRowWithData(Data data) throws SQLException {
        if ((Integer) Application.getInstance().getData().get("permission") < 1) {
            throw new SQLException(getClass().getName() + " Nicht die notwendigen Rechte. Nur Premiumnutzer dürfen Kommentare verfassen.");
        }
        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("INSERT INTO KommentarStaffel(Benutzer, StaffelID, Kommentar) VALUES (?, ?, ?)");
        preparedStatement.setObject(1, Application.getInstance().getData().get("username"));
        preparedStatement.setObject(2, data.get("KommentarStaffel.StaffelID"));
        preparedStatement.setObject(3, data.get("KommentarStaffel.Kommentar"));
        preparedStatement.executeUpdate();
    }

    @Override
    public void updateRowWithData(Data oldData, Data newData) throws SQLException {
        if (!Application.getInstance().getData().get("username").equals(oldData.get("KommentarStaffel.Benutzer"))) {
            throw new SQLException(getClass().getName() + " Nicht der gleiche Nutzer.");
        }

        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("UPDATE KommentarStaffel SET Kommentar = ? WHERE KommentarID = ?");
        preparedStatement.setObject(1, newData.get("KommentarStaffel.Kommentar"));
        preparedStatement.setObject(2, oldData.get("KommentarStaffel.KommentarID"));
        preparedStatement.executeUpdate();
    }

    @Override
    public void deleteRowWithData(Data data) throws SQLException {
        if (!Application.getInstance().getData().get("username").equals(data.get("KommentarStaffel.Benutzer"))) {
            throw new SQLException(getClass().getName() + " Nicht der gleiche Nutzer.");
        }

        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("DELETE FROM KommentarStaffel WHERE KommentarID = ? ");
        preparedStatement.setObject(1, data.get("KommentarStaffel.KommentarID"));
        preparedStatement.executeUpdate();
    }
}
