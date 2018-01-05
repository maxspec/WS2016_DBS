package com.watchandchill.table.movies;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.alexanderthelen.applicationkit.database.Data;
import com.alexanderthelen.applicationkit.database.Table;
import com.watchandchill.Application;

public class CommentsOnMovies extends Table {
	@Override
	public String getSelectQueryForTableWithFilter(String filter) throws SQLException {
		String selectQuery = "SELECT k.KommentarID, k.Benutzer, k.FilmID, k.Kommentar, v.Bezeichnung FROM KommentarFilm k, Video v WHERE k.FilmID=v.VideoID";
		if (filter != null && !filter.isEmpty()) {
			selectQuery += " AND v.Bezeichnung LIKE '%" + filter + "%'";
		}
		return selectQuery;
	}

	@Override
	public String getSelectQueryForRowWithData(Data data) throws SQLException {
		System.out.println(data);
		String selectQuery = "SELECT KommentarID, Benutzer, FilmID, Kommentar FROM KommentarFilm WHERE KommentarID = " + data.get("KommentarFilm.KommentarID");
		return selectQuery;
	}

	@Override
	public void insertRowWithData(Data data) throws SQLException {
		if ((Integer) Application.getInstance().getData().get("permission") < 1) {
			throw new SQLException(getClass().getName() + " Nicht die notwendigen Rechte. Nur Premiumnutzer dürfen Kommentare verfassen.");
		}
		System.out.println(data);
		PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("INSERT INTO KommentarFilm(Benutzer, FilmID, Kommentar) VALUES (?, ?, ?)");
		preparedStatement.setObject(1, Application.getInstance().getData().get("username"));
		preparedStatement.setObject(2, data.get("KommentarFilm.FilmID"));
		preparedStatement.setObject(3, data.get("KommentarFilm.Kommentar"));
		preparedStatement.executeUpdate();
	}

	@Override
	public void updateRowWithData(Data oldData, Data newData) throws SQLException {
		System.out.println(oldData);
		System.out.println(newData);
		if (!Application.getInstance().getData().get("username").equals(oldData.get("KommentarFilm.Benutzer"))) {
			throw new SQLException(getClass().getName() + " Nicht der gleiche Nutzer.");
		}

		PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("UPDATE KommentarFilm SET Kommentar = ? WHERE KommentarID = ?");
		preparedStatement.setObject(1, newData.get("KommentarFilm.Kommentar"));
		preparedStatement.setObject(2, oldData.get("KommentarFilm.KommentarID"));
		preparedStatement.executeUpdate();
	}

	@Override
	public void deleteRowWithData(Data data) throws SQLException {
		System.out.println(data);
		if (!Application.getInstance().getData().get("username").equals(data.get("KommentarFilm.Benutzer"))) {
			throw new SQLException(getClass().getName() + " Nicht der gleiche Nutzer.");
		}

		PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("DELETE FROM KommentarFilm WHERE KommentarID = ? ");
		preparedStatement.setObject(1, data.get("KommentarFilm.KommentarID"));
		preparedStatement.executeUpdate();
	}
}
