package com.watchandchill.table.serials;

import com.alexanderthelen.applicationkit.database.Data;
import com.alexanderthelen.applicationkit.database.Table;

import java.sql.SQLException;

public class SeasonsOnSerials extends Table {
    @Override
    public String getSelectQueryForTableWithFilter(String filter) throws SQLException {
        String selectQuery = "SELECT st.StaffelID, st.Nummer, se.Name FROM Staffel st, Serie se WHERE st.SerieID=se.SerieID ";
        if (filter != null && !filter.isEmpty()) {
            selectQuery += " AND (st.Nummer LIKE '%" + filter + "%'";
            selectQuery += " OR se.Name LIKE '%" + filter + "%')";
        }
        return selectQuery;
    }

    @Override
    public String getSelectQueryForRowWithData(Data data) throws SQLException {
        String selectQuery = "SELECT st.StaffelID, st.Nummer, se.Name FROM Staffel st, Serie se WHERE st.SerieID=se.SerieID AND st.StaffelID=" + data.get("Staffel.StaffelID");
        return selectQuery;
    }

    @Override
    public void insertRowWithData(Data data) throws SQLException {
        throw new SQLException(getClass().getName() + " Nicht erlaubt für Benutzer.");
    }

    @Override
    public void updateRowWithData(Data oldData, Data newData) throws SQLException {
        throw new SQLException(getClass().getName() + " Nicht erlaubt für Benutzer.");
    }

    @Override
    public void deleteRowWithData(Data data) throws SQLException {
        throw new SQLException(getClass().getName() + " Nicht erlaubt für Benutzer.");
    }
}
