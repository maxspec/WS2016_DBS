package com.watchandchill.table.movies;

import com.alexanderthelen.applicationkit.database.Data;
import com.alexanderthelen.applicationkit.database.Table;

import java.sql.SQLException;

public class Top10OnMovies extends Table {
    @Override
    public String getSelectQueryForTableWithFilter(String filter) throws SQLException {
        String selectQuery = "select bew.FilmID, v.Bezeichnung, v.Spieldauer, v.Erscheinungsjahr, v.Information, f.Produktionsbudget, avg(bew.Bewertungspunkte) as \"Durchschnittliche Bewertung\" " +
                "from Bewertung bew, Film f, Video v " +
                "WHERE bew.FilmID=f.VideoID AND f.videoID=v.VideoID " +
                "group by bew.FilmID " +
                "order by \"Durchschnittliche Bewertung\" DESC " +
                "LIMIT 10;";
        if (filter != null && !filter.isEmpty()) {
        }
        return selectQuery;
    }

    @Override
    public String getSelectQueryForRowWithData(Data data) throws SQLException {
        String selectQuery = "SELECT * FROM Video v, Film f WHERE v.VideoID=f.VideoID AND v.VideoID = " + data.get("Bewertung.FilmID");
        return selectQuery;
    }

    @Override
    public void insertRowWithData(Data data) throws SQLException {
        throw new SQLException(getClass().getName() + " Einfügen nicht erlaubt.");
    }

    @Override
    public void updateRowWithData(Data oldData, Data newData) throws SQLException {
        throw new SQLException(getClass().getName() + " Bearbeitung nicht erlaubt.");
    }

    @Override
    public void deleteRowWithData(Data data) throws SQLException {
        throw new SQLException(getClass().getName() + " Löschen nicht erlaubt.");
    }
}
