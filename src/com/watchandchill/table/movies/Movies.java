package com.watchandchill.table.movies;

import java.sql.SQLException;

import com.alexanderthelen.applicationkit.database.Data;
import com.alexanderthelen.applicationkit.database.Table;

public class Movies extends Table {
    @Override
    public String getSelectQueryForTableWithFilter(String filter) throws SQLException {
        String selectQuery = "SELECT v.videoID, v.Bezeichnung, v.Spieldauer, v.Erscheinungsjahr, v.Information, f.Produktionsbudget, avg(bew.Bewertungspunkte) AS Bewertung FROM Video v, Film f LEFT JOIN Bewertung bew ON bew.FilmID=v.VideoID WHERE f.videoID=v.videoID";
        String filterGenre=filter;
        if (filter != null && !filter.isEmpty()) {
            selectQuery += " AND (v.Bezeichnung LIKE '%" + filter + "%'";
            selectQuery += " OR v.VideoID IN (SELECT VideoID FROM Gehoert_zu_Genre WHERE ";
            String array[] = filterGenre.split("\\s+");
            for (int i = 0; i < array.length; i++) {
                array[i].trim();
                System.out.println(array[i]);
                if (i == 0) {
                    selectQuery += " GBezeichnung LIKE '%" + array[i] + "%'";
                } else {
                    selectQuery += " OR GBezeichnung LIKE '%" + array[i] + "%'";
                }

            }
            selectQuery += " ) ) ";
        }
        selectQuery += " group by bew.FilmID";
        return selectQuery;
    }

    @Override
    public String getSelectQueryForRowWithData(Data data) throws SQLException {
        String selectQuery = "SELECT v.videoID AS \"ID von Video\", v.Bezeichnung, v.Spieldauer, v.Erscheinungsjahr, v.Information, f.Produktionsbudget FROM Video v, Film f WHERE v.VideoID = " + data.get("Video.VideoID");
        return selectQuery;
    }

    @Override
    public void insertRowWithData(Data data) throws SQLException {
    	throw new SQLException(getClass().getName() + " Einfügen nicht erlaubt für Benutzer");
    }

    @Override
    public void updateRowWithData(Data oldData, Data newData) throws SQLException {
    	throw new SQLException(getClass().getName() + " Bearbeiten nicht erlaubt für Benutzer");
    }

    @Override
    public void deleteRowWithData(Data data) throws SQLException {
    	throw new SQLException(getClass().getName() + " Löschen nicht erlaubt für Benutzer");
    }
}
