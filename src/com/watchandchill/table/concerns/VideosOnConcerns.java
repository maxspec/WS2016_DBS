package com.watchandchill.table.concerns;

import com.alexanderthelen.applicationkit.database.Data;
import com.alexanderthelen.applicationkit.database.Table;

import java.sql.SQLException;

public class VideosOnConcerns extends Table {
    @Override
    public String getSelectQueryForTableWithFilter(String filter) throws SQLException {
        String selectQuery = "SELECT p.VideoID, v.Bezeichnung AS \"Video\", p.MBezeichnung AS \"Produziert von\" FROM Produzieren p, Video v WHERE p.VideoID=v.VideoID";
        if (filter != null && !filter.isEmpty()) {
            selectQuery += " AND (v.Bezeichnung LIKE '%" + filter + "%'";
            selectQuery += " OR MBezeichnung LIKE '%" + filter + "%')";
        }
        return selectQuery;
    }

    @Override
    public String getSelectQueryForRowWithData(Data data) throws SQLException {
        return null;
    }

    @Override
    public void insertRowWithData(Data data) throws SQLException {
        throw new SQLException(getClass().getName() + "Nicht erlaubt für Benutzer.");
    }

    @Override
    public void updateRowWithData(Data oldData, Data newData) throws SQLException {
        throw new SQLException(getClass().getName() + "Nicht erlaubt für Benutzer.");
    }

    @Override
    public void deleteRowWithData(Data data) throws SQLException {
        throw new SQLException(getClass().getName() + "Nicht erlaubt für Benutzer.");
    }
}
