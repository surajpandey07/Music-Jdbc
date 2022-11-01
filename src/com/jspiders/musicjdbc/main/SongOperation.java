package com.jspiders.musicjdbc.main;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
//import java.util.Random;
import java.util.Scanner;

public class SongOperation extends Song {

	static ArrayList<Song> list = new ArrayList<Song>();
	static Scanner sc = new Scanner(System.in);
	static Connection connection;
	static PreparedStatement preparedStatement;
	static Statement statement;
	static Properties properties = new Properties();
	static FileReader fileReader;
	static ResultSet resultSet;
	static String driverPath = "com.mysql.cj.jdbc.Driver";
	static String filePath = "C:\\Users\\Suraj\\eclipse-workspace\\musicJDBC\\resoures\\multiPlayer.properties";
	static String query;
	static int resultInt;

	// constructor
	public SongOperation(int id, String songName, String singerName, String movieName, String composer, String lyrist,
			double duration) {
		super(id, songName, singerName, movieName, composer, lyrist, duration);

	}

//		choose song to play
	public static void chooseSongToPlay() {
		try {

			fileReader = new FileReader(filePath);
			properties.load(fileReader);
//			Class.forName(properties.getProperty("driverPath"));
			Class.forName(driverPath);
			connection = DriverManager.getConnection(properties.getProperty("dbPath"), properties);

			query = "select count(*) from music";
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				if (resultSet.getInt(1) == 0) {
					System.out.println("\nno songs present\n");
					return;
				}
			}

			int index = 1;
			query = "select * from music";
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				if (index == 1) {
					System.out.println("choose any of the songs shown below");
				}
				System.out.println("press " + index + " for " + resultSet.getString(1) + " | " + resultSet.getString(2)
						+ " | " + resultSet.getString(3) + " | " + resultSet.getString(4) + " | "
						+ resultSet.getString(5) + " | " + resultSet.getString(6) + " | " + resultSet.getDouble(7));
				index++;
			}

			resultSet = statement.executeQuery(query);
			int a = sc.nextInt();
			index = 1;
			while (resultSet.next()) {
				if (index == a) {
					System.out.println("\n" + resultSet.getString(2) + " is playing now \n");
					break;
				}
				index++;
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

//		play all songs
	public static void playAllSongs() {
		try {
			fileReader = new FileReader(filePath);
			properties.load(fileReader);
			Class.forName(driverPath);
			// Class.forName(properties.getProperty("driverPath"));
			connection = DriverManager.getConnection(properties.getProperty("dbPath"), properties);

			query = "select count(*) from music";
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				if (resultSet.getInt(1) == 0) {
					System.out.println("\nno songs present\n");
					return;
				}
			}

			query = "select * from music";
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				System.out.println(resultSet.getString(2) + " is now playing");
				Thread.sleep(2000);

			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	// play random songs
	public static void playRandomSongs() {
		try {
			fileReader = new FileReader(filePath);
			properties.load(fileReader);
//			Class.forName(properties.getProperty("driverPath"));
			Class.forName(driverPath);
			connection = DriverManager.getConnection(properties.getProperty("dbPath"), properties);
			query = "select count(*) from music";
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				if (resultSet.getInt(1) == 0) {
					System.out.println("\nno songs present\n");
					return;
				}
			}
			query = "select * from music";
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			boolean loop = true;
			while (resultSet.next()) {
				list.add(new Song(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
						resultSet.getString(4), resultSet.getString(5), resultSet.getString(6),
						resultSet.getDouble(7)));
			}
			while (loop) {
				int max = list.size() - 1;
				int min = 0;
				System.out.println(list.get((int) (Math.random() * (max - min + 1) + min)).getSongName()
						+ " is playing now (shuffled)");
				loop = false;
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	// add songs
	public static void addSongs() {
		try {
			fileReader = new FileReader(filePath);
			properties.load(fileReader);
//			Class.forName(properties.getProperty("driverPath"));
			Class.forName(driverPath);
			connection = DriverManager.getConnection(properties.getProperty("dbPath"), properties);
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from music");
			int index = 1;
			while (resultSet.next()) {
				System.out.println((index++) + "] songId " + resultSet.getString(1) + " " + resultSet.getString(2));
			}
			query = "insert into music values(?,?,?,?,?,?,?)";
			preparedStatement = connection.prepareStatement(query);
			System.out.println(
					"please enter the song details(Id, songName,singerName,movieName, composer,lyrist, duration, do not add a duplicate one22");
			preparedStatement.setInt(1, sc.nextInt());
			preparedStatement.setString(2, sc.next());
			preparedStatement.setString(3, sc.next());
			preparedStatement.setString(4, sc.next());
			preparedStatement.setString(5, sc.next());
			preparedStatement.setString(6, sc.next());
			preparedStatement.setString(7, sc.next());
			resultInt = preparedStatement.executeUpdate();
			System.out.println(" " + resultInt + " song added successfully");

		} catch (FileNotFoundException e) {

//				e.printStackTrace();
			System.out.println("file not found");

		} catch (IOException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

//		remove songs
	public static void removeSongs() throws ClassNotFoundException {
		try {
			fileReader = new FileReader(filePath);
			properties.load(fileReader);
//			Class.forName(properties.getProperty("driverPath"));

			connection = DriverManager.getConnection(properties.getProperty("dbPath"), properties);
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select count(*) from music");
			while (resultSet.next()) {
				if (resultSet.getInt(1) == 0) {
					System.out.println("\nno songs present\n");
					return;
				}
			}
			int index = 1;
			resultSet = statement.executeQuery("select * from music");
			while (resultSet.next()) {
				System.out.println((index++) + "] Id " + resultSet.getString(1) + "| songName " + resultSet.getString(2)
						+ "| singerName " + resultSet.getString(3) + "| movieName " + resultSet.getString(4)
						+ "| composer " + resultSet.getString(5) + "| lyrist " + resultSet.getString(6) + "| duration "
						+ resultSet.getString(7));
			}

			query = "delete from music where(id=?)";
			preparedStatement = connection.prepareStatement(query);
			System.out.println("please enter the song id you want to delete");
			preparedStatement.setInt(1, sc.nextInt());
			resultInt = preparedStatement.executeUpdate();
			System.out.println(" " + resultInt + " song deleted successfully");

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	// edit songs
	public static void editSongs() {
		try {
			fileReader = new FileReader(filePath);
			properties.load(fileReader);
			// Class.forName(properties.getProperty("driverPath"));
			Class.forName(driverPath);
			connection = DriverManager.getConnection(properties.getProperty("dbPath"), properties);
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select count(*) from music");
			while (resultSet.next()) {
				if (resultSet.getInt(1) == 0) {
					System.out.println("\nno songs present\n");
					return;
				}
			}
			int index = 1;
			resultSet = statement.executeQuery("select * from music");
			while (resultSet.next()) {
				System.out.println((index++) + "] songId " + resultSet.getString(1) + "| songName "
						+ resultSet.getString(2) + "| singerName " + resultSet.getString(3));
			}

			System.out.println("what changes you want to make\n1.id\n2.songName\n3.singerName");
			int a = sc.nextInt();
			switch (a) {
			case 1:
				query = "update music set `id`= ? where `id` = ?";
				preparedStatement = connection.prepareStatement(query);
				System.out.println("enter the value to be set for id");
				preparedStatement.setInt(1, sc.nextInt());
				System.out.println("enter the current id");
				preparedStatement.setInt(2, sc.nextInt());
				resultInt = preparedStatement.executeUpdate();
				System.out.println(" " + resultInt + " song updated successfully");
				break;

			case 2:
				query = "update music set `songName`= ? where `songName`= ?";
				preparedStatement = connection.prepareStatement(query);
				System.out.println("enter the value to be set for songName");
				preparedStatement.setString(1, sc.next());
				System.out.println("enter the current value of the songName");
				preparedStatement.setString(2, sc.next());
				resultInt = preparedStatement.executeUpdate();
				System.out.println(" " + resultInt + " song updated successfully");
				break;

			case 3:
				query = "update music set `singerName`= ? where `singerName` = ?";
				preparedStatement = connection.prepareStatement(query);
				System.out.println("enter the value to be set for singerName");
				preparedStatement.setString(1, sc.next());
				System.out.println("enter the current value of the singerName");
				preparedStatement.setString(2, sc.next());
				resultInt = preparedStatement.executeUpdate();
				System.out.println(" " + resultInt + " song updated successfully");
				break;
			default:
				System.out.println("wrong input");
				return;

			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	// displayAllSongs
	public static void displayAllSongs() {
		try {
			fileReader = new FileReader(filePath);
			properties.load(fileReader);
//			Class.forName(properties.getProperty("driverPath"));
			Class.forName(driverPath);

			connection = DriverManager.getConnection(properties.getProperty("dbPath"), properties);
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from music");
			System.out.println("------------------");
			System.out.println("Music Library");
			int index = 1;
			while (resultSet.next()) {
				System.out.println((index++) + "] " + resultSet.getString(1) + " | " + resultSet.getString(2) + " | "
						+ resultSet.getString(3) + " | " + resultSet.getString(4));

			}
			System.out.println("------------------");

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
}
