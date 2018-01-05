package com.watchandchill.table.users;

import com.alexanderthelen.applicationkit.database.Data;
import com.alexanderthelen.applicationkit.database.Table;
import com.watchandchill.Application;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FollowersOnUsers extends Table {
    @Override
    public String getSelectQueryForTableWithFilter(String filter) throws SQLException {
        String selectQuery = "SELECT f.Premiumnutzer1 AS Premiumnutzer, f.Premiumnutzer2 AS folgt FROM Folgt f";
        if (filter != null && !filter.isEmpty()) {
            selectQuery += " WHERE (f.Premiumnutzer1 LIKE '%" + filter + "%'";
            selectQuery += " OR f.Premiumnutzer2 LIKE '%" + filter + "%' )";
        }
        return selectQuery;
    }

    @Override
    public String getSelectQueryForRowWithData(Data data) throws SQLException {
        String selectQuery = "SELECT * FROM Folgt WHERE Premiumnutzer1 = \"" + data.get("Folgt.Premiumnutzer") +"\" AND Premiumnutzer2 = \"" + data.get("Folgt.folgt") +"\"";
        return selectQuery;
    }

    @Override
    public void insertRowWithData(Data data) throws SQLException {
        if ((Integer) Application.getInstance().getData().get("permission") < 1) {
            throw new SQLException(getClass().getName() + " Nur Premiumnutzer dürfen anderen Premiumnutzern folgen.");
        }
        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("INSERT INTO Folgt(Premiumnutzer1, Premiumnutzer2) VALUES (?, ?)");
        preparedStatement.setObject(1, Application.getInstance().getData().get("username"));
        preparedStatement.setObject(2, data.get("Folgt.Premiumnutzer2"));
        preparedStatement.executeUpdate();
    }

    @Override
    public void updateRowWithData(Data oldData, Data newData) throws SQLException {
        throw new SQLException(getClass().getName() + " Followings dürfen nicht bearbeitet werden. Nur löschen oder erstellen.");
    }

    @Override
    public void deleteRowWithData(Data data) throws SQLException {
        if (!Application.getInstance().getData().get("username").equals(data.get("Folgt.Premiumnutzer"))) {
            throw new SQLException(getClass().getName() + " Nicht der gleiche Nutzer. Nur eigene Followings dürfen gelöscht werden.");
        }
        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("DELETE FROM Folgt WHERE Premiumnutzer1 = ? AND Premiumnutzer2 = ?");
        preparedStatement.setObject(1, Application.getInstance().getData().get("username"));
        preparedStatement.setObject(2, data.get("Folgt.folgt"));
        preparedStatement.executeUpdate();
    }
}
