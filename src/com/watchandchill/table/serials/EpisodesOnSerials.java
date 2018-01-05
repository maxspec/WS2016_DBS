package com.watchandchill.table.serials;

import com.alexanderthelen.applicationkit.database.Data;
import com.alexanderthelen.applicationkit.database.Table;

import java.sql.SQLException;

public class EpisodesOnSerials extends Table {
    @Override
    public String getSelectQueryForTableWithFilter(String filter) throws SQLException {
        String selectQuery = "SELECT f.Bezeichnung AS Folge, st.Nummer AS Staffel, se.Name AS Serie FROM Folge f, Staffel st, Serie se WHERE f.StaffelID=st.StaffelID AND st.SerieID=se.SerieID";
        if (filter != null && !filter.isEmpty()) {
            selectQuery += " AND ( f.Bezeichnung LIKE '%" + filter + "%'";
            selectQuery += " OR st.StaffelID LIKE '%" + filter + "%' ";
            selectQuery += " OR se.Name LIKE '%" + filter + "%' )";
        }
        selectQuery += " order by se.Name ASC, st.Nummer ASC";
        return selectQuery;
    }

    @Override
    public String getSelectQueryForRowWithData(Data data) throws SQLException {
        String selectQuery = "SELECT f.Bezeichnung AS Folge, st.Nummer AS Staffel, se.Name AS Serie FROM Folge f, Staffel st, Serie se WHERE f.StaffelID=st.StaffelID AND st.SerieID=se.SerieID";
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
