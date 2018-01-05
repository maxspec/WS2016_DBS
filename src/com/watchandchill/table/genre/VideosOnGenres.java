package com.watchandchill.table.genre;

import com.alexanderthelen.applicationkit.database.Data;
import com.alexanderthelen.applicationkit.database.Table;

import java.sql.SQLException;

public class VideosOnGenres extends Table {
    @Override
    public String getSelectQueryForTableWithFilter(String filter) throws SQLException {
        String selectQuery = "SELECT g.VideoID, v.bezeichnung, g.GBezeichnung FROM Video v, Gehoert_zu_Genre g WHERE v.videoID=g.videoID";
        if (filter != null && !filter.isEmpty()) {
            selectQuery += " AND ( ";
            String array[] = filter.split("\\s+");
            for (int i=0; i<array.length; i++) {
                array[i].trim();
                System.out.println(array[i]);
                if (i==0) {
                    selectQuery += " GBezeichnung LIKE '%" + array[i] + "%'";
                } else {
                    selectQuery += " OR GBezeichnung LIKE '%" + array[i] + "%'";
                }

            }


            selectQuery += " ) ";
        }
        return selectQuery;
    }

    @Override
    public String getSelectQueryForRowWithData(Data data) throws SQLException {
        return null;
    }

    @Override
    public void insertRowWithData(Data data) throws SQLException {
        throw new SQLException(getClass().getName() + "Nicht für Benutzer erlaubt.");
    }

    @Override
    public void updateRowWithData(Data oldData, Data newData) throws SQLException {
        throw new SQLException(getClass().getName() + "Nicht für Benutzer erlaubt.");
    }

    @Override
    public void deleteRowWithData(Data data) throws SQLException {
        throw new SQLException(getClass().getName() + "Nicht für Benutzer erlaubt.");
    }
}
