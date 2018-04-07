package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {

	private static final String URL = "jdbc:mysql://localhost:3306/sdvid";
	private static final String user = "student";
	private static final String pass = "student";

	@Override
	public Film getFilmById(int filmId) {
		Film film = null;
		String sql = "SELECT f.id, f.title, f.description, f.release_year, f.language_id, f.rental_duration, f.rental_rate,"
				+ " f.length, f.replacement_cost, f.rating, f.special_features, l.name FROM film f "
				+ "join language l on l.id = f.language_id WHERE f.id = ?";
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				String title = rs.getString(2);
				String description = rs.getString(3);
				int releaseYear = rs.getInt(4);
				int languageId = rs.getInt(5);
				int rentalDuration = rs.getInt(6);
				double rentalRate = rs.getDouble(7);
				int length = rs.getInt(8);
				double replacementCost = rs.getDouble(9);
				String rating = rs.getString(10);
				String specialFeatures = rs.getString(11);
				String language = rs.getString(12);
				List<Actor> actorsByFilmId = getActorsByFilmId(filmId);
				film = new Film(id, title, description, releaseYear, languageId, rentalDuration, rentalRate, length,
						replacementCost, rating, specialFeatures, language);
				film.setActors(actorsByFilmId);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return film;
	}

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.err.println("Unable to load database driver. Exiting the application.");
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Override
	public Actor getActorById(int actorId) {
		Actor actor = null;
		String sql = "SELECT id, first_name, last_name FROM actor WHERE id = ?";
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, actorId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				String fname = rs.getString(2);
				String lname = rs.getString(3);
				actor = new Actor(id, fname, lname);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return actor;
	}

	@Override
	public List<Actor> getActorsByFilmId(int filmId) {
		List<Actor> actors = new ArrayList<>();
		String sql = "select a.first_name, a.last_name, a.id from film f join film_actor fa on f.id = "
				+ "fa.film_id join actor a on a.id = fa.actor_id where f.id = ? limit 5";
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String fname = rs.getString(1);
				String lname = rs.getString(2);
				int id = rs.getInt(3);
				Actor actor = new Actor(id, fname, lname);
				actors.add(actor);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return actors;
	}

	@Override
	public List<Film> getFilmByKeyword(String keyword) {
		List<Film> films = new ArrayList<>();
		String sql = "SELECT f.title, f.description, f.release_year, f.language_id, "
				+ "f.rental_duration, f.rental_rate, f.length, f.replacement_cost, f.rating, "
				+ "f.special_features, f.id, l.name from film f join language l on l.id = "
				+ "f.language_id where title like ? or description like ?";
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, "%" + keyword + "%");
			stmt.setString(2, "%" + keyword + "%");
			
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String title = rs.getString(1);
				String desc = rs.getString(2);
				int releaseYear = rs.getInt(3);
				int languageId = rs.getInt(4);
				int rentalDuration = rs.getInt(5);
				double rentalRate = rs.getDouble(6);
				int length = rs.getInt(7);
				double replacementCost = rs.getDouble(8);
				String rating = rs.getString(9);
				String features = rs.getString(10);
				int id = rs.getInt(11);
				String language = rs.getString(12);
				Film film = new Film(id, title, desc, releaseYear, languageId, rentalDuration, rentalRate, length,
						replacementCost, rating, features, language);
				film.setActors(getActorsByFilmId(id));
				films.add(film);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return films;
	}
		
}