package com.watchandchill.gui;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import com.alexanderthelen.applicationkit.database.Table;
import com.alexanderthelen.applicationkit.gui.TableViewController;
import com.alexanderthelen.applicationkit.gui.ViewController;
import com.watchandchill.Application;
import com.watchandchill.table.account.Account;
import com.watchandchill.table.concerns.ActorsOnConcerns;
import com.watchandchill.table.concerns.Concerns;
import com.watchandchill.table.concerns.VideosOnConcerns;
import com.watchandchill.table.genre.Genres;
import com.watchandchill.table.genre.VideosOnGenres;
import com.watchandchill.table.movies.*;

import com.watchandchill.table.serials.*;
import com.watchandchill.table.users.ActorsOnUsers;
import com.watchandchill.table.users.FollowersOnUsers;
import com.watchandchill.table.users.PremiumUsers;
import com.watchandchill.table.users.Users;
import com.watchandchill.table.videos.Videos;
import com.watchandchill.table.watchlists.VideosOnWatchlists;
import com.watchandchill.table.watchlists.Watchlists;
import javafx.scene.control.TreeItem;

public class MasterViewController extends com.alexanderthelen.applicationkit.gui.MasterViewController {
	public static MasterViewController createWithName(String name) throws IOException {
		MasterViewController controller = new MasterViewController(name);
		controller.loadView();
		return controller;
	}

	protected MasterViewController(String name) {
		super(name);
	}

	@Override
	protected ArrayList<TreeItem<ViewController>> getTreeItems() {
		ArrayList<TreeItem<ViewController>> treeItems = new ArrayList<>();
		TreeItem<ViewController> treeItem;
		TreeItem<ViewController> subTreeItem;
		TableViewController tableViewController;
		Table table;

		table = new Watchlists();
		table.setTitle("Watchlists");
		try {
			tableViewController = TableViewController.createWithNameAndTable("watchlists", table);
			tableViewController.setTitle("Watchlists");
		} catch (IOException e) {
			tableViewController = null;
		}
		treeItem = new TreeItem<>(tableViewController);
		treeItem.setExpanded(true);
		treeItems.add(treeItem);

		table = new VideosOnWatchlists();
		table.setTitle("Videos zu Watchlists");
		try {
			tableViewController = TableViewController.createWithNameAndTable("videosOnWatchlists", table);
			tableViewController.setTitle("Videos");
		} catch (IOException e) {
			tableViewController = null;
		}
		subTreeItem = new TreeItem<>(tableViewController);
		treeItem.getChildren().add(subTreeItem);

		table = new Serials();
		table.setTitle("Serien");
		try {
			tableViewController = TableViewController.createWithNameAndTable("serials", table);
			tableViewController.setTitle("Serien");
		} catch (IOException e) {
			tableViewController = null;
		}
		treeItem = new TreeItem<>(tableViewController);
		treeItem.setExpanded(true);
		treeItems.add(treeItem);

		table = new EpisodesOnSerials();
		table.setTitle("Folgen zu Serien");
		try {
			tableViewController = TableViewController.createWithNameAndTable("episodesOnSerials", table);
			tableViewController.setTitle("Folgen");
		} catch (IOException e) {
			tableViewController = null;
		}
		subTreeItem = new TreeItem<>(tableViewController);
		treeItem.getChildren().add(subTreeItem);

		table = new SeasonsOnSerials();
		table.setTitle("Staffeln zu Serien");
		try {
			tableViewController = TableViewController.createWithNameAndTable("seasonsOnSerials", table);
			tableViewController.setTitle("Staffeln");
		} catch (IOException e) {
			tableViewController = null;
		}
		subTreeItem = new TreeItem<>(tableViewController);
		treeItem.getChildren().add(subTreeItem);

		table = new CommentsOnSeasons();
		table.setTitle("Kommentare zu Staffeln");
		try {
			tableViewController = TableViewController.createWithNameAndTable("commentsOnSeasons", table);
			tableViewController.setTitle("Kommentare");
		} catch (IOException e) {
			tableViewController = null;
		}
		subTreeItem = new TreeItem<>(tableViewController);
		treeItem.getChildren().add(subTreeItem);

		table = new ActorsOnSerials();
		table.setTitle("Schauspieler zu Serien");
		try {
			tableViewController = TableViewController.createWithNameAndTable("actorsOnSerials", table);
			tableViewController.setTitle("Schauspieler");
		} catch (IOException e) {
			tableViewController = null;
		}
		subTreeItem = new TreeItem<>(tableViewController);
		treeItem.getChildren().add(subTreeItem);

		table = new Movies();
		table.setTitle("Filme");
		try {
			tableViewController = TableViewController.createWithNameAndTable("movie", table);
			tableViewController.setTitle("Filme");
		} catch (IOException e) {
			tableViewController = null;
		}
		treeItem = new TreeItem<>(tableViewController);
		treeItem.setExpanded(true);
		treeItems.add(treeItem);

		table = new Top10OnMovies();
		table.setTitle("Top 10 zu Filmen");
		try {
			tableViewController = TableViewController.createWithNameAndTable("top10OnMovies", table);
			tableViewController.setTitle("Top 10");
		} catch (IOException e) {
			tableViewController = null;
		}
		subTreeItem = new TreeItem<>(tableViewController);
		treeItem.getChildren().add(subTreeItem);

		table = new RatingsOnMovies();
		table.setTitle("Bewertungen zu Filmen");
		try {
			tableViewController = TableViewController.createWithNameAndTable("ratingsOnMovies", table);
			tableViewController.setTitle("Bewertungen");
		} catch (IOException e) {
			tableViewController = null;
		}
		subTreeItem = new TreeItem<>(tableViewController);
		treeItem.getChildren().add(subTreeItem);
		
		table = new CommentsOnMovies();
		table.setTitle("Kommentare zu Filmen");
		try {
			tableViewController = TableViewController.createWithNameAndTable("commentsOnMovies", table);
			tableViewController.setTitle("Kommentare");
		} catch (IOException e) {
			tableViewController = null;
		}
		subTreeItem = new TreeItem<>(tableViewController);
		treeItem.getChildren().add(subTreeItem);

		table = new TrailersOnMovies();
		table.setTitle("Trailer zu Filmen");
		try {
			tableViewController = TableViewController.createWithNameAndTable("trailersOnMovies", table);
			tableViewController.setTitle("Trailer");
		} catch (IOException e) {
			tableViewController = null;
		}
		subTreeItem = new TreeItem<>(tableViewController);
		treeItem.getChildren().add(subTreeItem);

		table = new ActorsOnMovies();
		table.setTitle("Schauspieler zu Filmen");
		try {
			tableViewController = TableViewController.createWithNameAndTable("actorsOnMovies", table);
			tableViewController.setTitle("Schauspieler");
		} catch (IOException e) {
			tableViewController = null;
		}
		subTreeItem = new TreeItem<>(tableViewController);
		treeItem.getChildren().add(subTreeItem);

		table = new Genres();
		table.setTitle("Genres");
		try {
			tableViewController = TableViewController.createWithNameAndTable("genres", table);
			tableViewController.setTitle("Genres");
		} catch (IOException e) {
			tableViewController = null;
		}
		treeItem = new TreeItem<>(tableViewController);
		treeItem.setExpanded(true);
		treeItems.add(treeItem);

		table = new VideosOnGenres();
		table.setTitle("Videos zu Genres");
		try {
			tableViewController = TableViewController.createWithNameAndTable("videosOnGenres", table);
			tableViewController.setTitle("Videos");
		} catch (IOException e) {
			tableViewController = null;
		}
		subTreeItem = new TreeItem<>(tableViewController);
		treeItem.getChildren().add(subTreeItem);

		table = new Users();
		table.setTitle("Nutzer");
		try {
			tableViewController = TableViewController.createWithNameAndTable("users", table);
			tableViewController.setTitle("Nutzer");
		} catch (IOException e) {
			tableViewController = null;
		}
		treeItem = new TreeItem<>(tableViewController);
		treeItem.setExpanded(true);
		treeItems.add(treeItem);

		table = new PremiumUsers();
		table.setTitle("Premiumnutzer");
		try {
			tableViewController = TableViewController.createWithNameAndTable("PremiumUsers", table);
			tableViewController.setTitle("Premiumnutzer");
		} catch (IOException e) {
			tableViewController = null;
		}
		subTreeItem = new TreeItem<>(tableViewController);
		treeItem.getChildren().add(subTreeItem);

		table = new FollowersOnUsers();
		table.setTitle("Follower zu Nutzern");
		try {
			tableViewController = TableViewController.createWithNameAndTable("followersOnUsers", table);
			tableViewController.setTitle("Follower");
		} catch (IOException e) {
			tableViewController = null;
		}
		subTreeItem = new TreeItem<>(tableViewController);
		treeItem.getChildren().add(subTreeItem);

		table = new ActorsOnUsers();
		table.setTitle("Schauspieler zu Nutzern");
		try {
			tableViewController = TableViewController.createWithNameAndTable("actorsOnUsers", table);
			tableViewController.setTitle("Schauspieler");
		} catch (IOException e) {
			tableViewController = null;
		}
		subTreeItem = new TreeItem<>(tableViewController);
		treeItem.getChildren().add(subTreeItem);

		table = new Concerns();
		table.setTitle("Konzerne");
		try {
			tableViewController = TableViewController.createWithNameAndTable("concerns", table);
			tableViewController.setTitle("Konzerne");
		} catch (IOException e) {
			tableViewController = null;
		}
		treeItem = new TreeItem<>(tableViewController);
		treeItem.setExpanded(true);
		treeItems.add(treeItem);

		table = new VideosOnConcerns();
		table.setTitle("Videos zu Konzernen");
		try {
			tableViewController = TableViewController.createWithNameAndTable("videosOnConcerns", table);
			tableViewController.setTitle("Videos");
		} catch (IOException e) {
			tableViewController = null;
		}
		subTreeItem = new TreeItem<>(tableViewController);
		treeItem.getChildren().add(subTreeItem);

		table = new ActorsOnConcerns();
		table.setTitle("Schauspieler zu Konzernen");
		try {
			tableViewController = TableViewController.createWithNameAndTable("actorsOnConcerns", table);
			tableViewController.setTitle("Schauspieler");
		} catch (IOException e) {
			tableViewController = null;
		}
		subTreeItem = new TreeItem<>(tableViewController);
		treeItem.getChildren().add(subTreeItem);

		table = new Videos();
		table.setTitle("Videos");
		try {
			tableViewController = TableViewController.createWithNameAndTable("videos", table);
			tableViewController.setTitle("Videos");
		} catch (IOException e) {
			tableViewController = null;
		}
		treeItem = new TreeItem<>(tableViewController);
		treeItem.setExpanded(true);
		treeItems.add(treeItem);

		table = new Account();
		table.setTitle("Account");
		try {
			tableViewController = TableViewController.createWithNameAndTable("account", table);
			tableViewController.setTitle("Account");
		} catch (IOException e) {
			tableViewController = null;
		}
		treeItem = new TreeItem<>(tableViewController);
		treeItem.setExpanded(true);
		treeItems.add(treeItem);

		return treeItems;
	}
}
