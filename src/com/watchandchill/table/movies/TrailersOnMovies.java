package com.watchandchill.table.movies;

import com.alexanderthelen.applicationkit.database.Data;
import com.alexanderthelen.applicationkit.database.Table;

import java.sql.SQLException;

public class TrailersOnMovies extends Table {
    @Override
    public String getSelectQueryForTableWithFilter(String filter) throws SQLException {
        String selectQuery = "SELECT t.VideoID, v.Bezeichnung AS Trailer, t.LQ, t.HQ, t.FilmID, f.Bezeichnung AS Film FROM Trailer t, Video f, Video v WHERE t.FIlmID=f.VideoID AND t.VideoID=v.VideoID ";
        if (filter != null && !filter.isEmpty()) {
            selectQuery += " AND v.Bezeichnung LIKE '%" + filter + "%'";
        }
        selectQuery += " ORDER BY t.FilmID ASC";
        return selectQuery;
    }

    @Override
    public String getSelectQueryForRowWithData(Data data) throws SQLException {
        return null;
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
