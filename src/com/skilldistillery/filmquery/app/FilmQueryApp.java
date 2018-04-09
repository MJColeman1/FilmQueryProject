package com.skilldistillery.filmquery.app;

import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {

	DatabaseAccessor db = new DatabaseAccessorObject();

	public static void main(String[] args) {
		FilmQueryApp app = new FilmQueryApp();
		app.launch();
	}

	// private void test() {
	// Film film = db.getFilmById(1);
	// System.out.println(film);
	// System.out.println(db.getActorById(1));
	// System.out.println(db.getActorsByFilmId(1000));
	// }

	private void launch() {
		Scanner input = new Scanner(System.in);

		startUserInterface(input);

		input.close();
	}

	private void startUserInterface(Scanner input) {
		int choice = 0;
		System.out.println("Hello! Welcome to the Film Query Application! \n"
				+ "This application will allow you to search through hundreds of totally real movies and well-known A-list actors!");
		while (true) {
			System.out.print("Menu: \n" + "1. Look Up a Film by its ID\n" + "2. Look Up a Film by a Search Keyword\n"
					+ "3. Quit\n" + "What would you like to do? Select an option from the menu: ");
			choice = input.nextInt();
			switch (choice) {
			case 1:
				System.out.print("Please enter a film ID using a whole number: ");
				int choice2 = input.nextInt();
				if (choice2 <= 0 || choice2 > 1000) {
					System.out.println("There is no film matching that ID. ");
					continue;
				} else {
					Film film = db.getFilmById(choice2);
					System.out.println("The following film matched your ID search: ");
					System.out.println("The film title is " + film.getTitle());
					System.out.println("Description: " + film.getDescription());
					System.out.println("The rating is " + film.getRating());
					System.out.println("This film was released in " + film.getReleaseYear());
					System.out.println("Film language: " + film.getLanguage());
					List<Actor> actors = film.getActors();
					for (Actor actor : actors) {
						System.out.println("Film actor/actress: " + actor.getFirstName() + " " + actor.getLastName());
					}
				}
				break;

			case 2:
				System.out.print("Please enter a search keyword: ");
				String keyword = input.next();
				List<Film> films = db.getFilmByKeyword(keyword);
				if (films.size() == 0) {
					System.out.println("Your search did not return any results.");
				}
				else {
					for (Film film : films) {
						System.out.println("The following film matched your keyword search: ");
						System.out.println("The film title is " + film.getTitle());
						System.out.println("Description: " + film.getDescription());
						System.out.println("The rating is " + film.getRating());
						System.out.println("This film was released in " + film.getReleaseYear());
						System.out.println("Film language: " + film.getLanguage());
						List<Actor> actors = film.getActors();
						for (Actor actor : actors) {
							System.out.println("Film actor/actress: " + actor.getFirstName() + " " + actor.getLastName());
						}
						System.out.println();
					}					
				}
				break;

			case 3:
				System.out.println("Thank you for using our film query application! Have a nice day!");
				System.exit(0);
				break;
				
			default: 
				System.out.println("Invalid input. Please select an option from the menu: ");
			}

		}

	}
}
