package com.watchandchill.table.concerns;

import com.alexanderthelen.applicationkit.database.Data;
import com.alexanderthelen.applicationkit.database.Table;

import java.sql.SQLException;

public class ActorsOnConcerns extends Table {
    @Override
    public String getSelectQueryForTableWithFilter(String filter) throws SQLException {
        String selectQuery = "SELECT s.Vorname, s.Nachname, s.Kuenstlername, s.Beschreibung, m.MBezeichnung FROM Schauspieler s, Unter_Vertrag_bei m WHERE s.Benutzername=m.Schauspieler";
        if (filter != null && !filter.isEmpty()) {
            selectQuery += " AND (m.MBezeichnung LIKE '%" + filter + "%'";
            selectQuery += " OR s.Vorname LIKE '%" + filter + "%'";
            selectQuery += " OR s.Nachname LIKE '%" + filter + "%'";
            selectQuery += " OR s.Kuenstlername LIKE '%" + filter + "%')";
        }
        return selectQuery;
    }

    @Override
    public String getSelectQueryForRowWithData(Data data) throws SQLException {
        return null;
    }

    @Override
    public void insertRowWithData(Data data) throws SQLException {
        throw new SQLException(getClass().getName() + ".insertRowWithData(Data) nicht implementiert.");
    }

    @Override
    public void updateRowWithData(Data oldData, Data newData) throws SQLException {
        throw new SQLException(getClass().getName() + ".updateRowWithData(Data, Data) nicht implementiert.");
    }

    @Override
    public void deleteRowWithData(Data data) throws SQLException {
        throw new SQLException(getClass().getName() + ".deleteRowWithData(Data) nicht implementiert.");
    }
}
