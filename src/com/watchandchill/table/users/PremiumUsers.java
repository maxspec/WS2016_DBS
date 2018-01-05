package com.watchandchill.table.users;

import com.alexanderthelen.applicationkit.database.Data;
import com.alexanderthelen.applicationkit.database.Table;

import java.sql.SQLException;

/**
 * Created by Maxim on 26.03.2017.
 */
public class PremiumUsers extends Table {
    @Override
    public String getSelectQueryForTableWithFilter(String filter) throws SQLException {
        String selectQuery = "SELECT p.Benutzername AS Premiumnutzer, COUNT(f.Premiumnutzer1) AS \"Anzahl Follower\" FROM Premiumnutzer p LEFT JOIN Folgt f ON p.Benutzername=f.Premiumnutzer2 ";
        if (filter != null && !filter.isEmpty()) {
            selectQuery += " WHERE p.Benutzername LIKE '%" + filter + "%'";
        }
        selectQuery += " GROUP BY p.Benutzername";
        return selectQuery;
    }

    @Override
    public String getSelectQueryForRowWithData(Data data) throws SQLException {
        String selectQuery = "SELECT * FROM Folgt WHERE Premiumnutzer1 = " + data.get("Premiumnutzer.Premiumnutzer") + " AND Premiumnutzer2 = " + data.get("Premiumnutzer");
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
