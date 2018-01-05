package com.watchandchill.table.genre;

import com.alexanderthelen.applicationkit.database.Data;
import com.alexanderthelen.applicationkit.database.Table;

import java.sql.SQLException;

public class Genres extends Table {
    @Override
    public String getSelectQueryForTableWithFilter(String filter) throws SQLException {
        String selectQuery = "SELECT GBezeichnung AS \"Genre\" FROM Genre";
        if (filter != null && !filter.isEmpty()) {
            selectQuery += " WHERE GBezeichnung LIKE '%" + filter + "%'";
        }
        return selectQuery;
    }

    @Override
    public String getSelectQueryForRowWithData(Data data) throws SQLException {
        return null;
    }

    @Override
    public void insertRowWithData(Data data) throws SQLException {
        throw new SQLException("Genres dürfen nur außerhalb der GUI hinzugefügt werden.");
    }

    @Override
    public void updateRowWithData(Data oldData, Data newData) throws SQLException {
        throw new SQLException("Genres dürfen nicht bearbeitet werden.");
    }

    @Override
    public void deleteRowWithData(Data data) throws SQLException {
        throw new SQLException("Genres dürfen nicht gelöscht werden.");
    }
}
