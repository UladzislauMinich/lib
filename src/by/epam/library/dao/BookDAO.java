package by.epam.library.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import by.epam.library.entity.Book;
import by.epam.library.exception.DAOException;

public class BookDAO extends AbstractDAO {
	private static BookDAO instance = new BookDAO();
	private static final String SQL_SELECT_BOOK = "SELECT * FROM library.books JOIN library.Authors ON (books.idAuthor = authors.idAuthor) JOIN library.Genres ON (books.idGenre = genres.idGenre)";
	private static final String SQL_SELECT_BOOK_BY_GENRE = "SELECT * FROM library.books JOIN library.Authors ON (books.idAuthor = authors.idAuthor) JOIN library.Genres ON (books.idGenre = genres.idGenre) WHERE genres.genre= ?";
	private static final String SQL_SELECT_BOOK_BY_ID = "SELECT * FROM library.books JOIN library.Authors ON (books.idAuthor = authors.idAuthor) JOIN library.Genres ON (books.idGenre = genres.idGenre) WHERE books.idBooks= ?";
	private static final String SQL_SELECT_BOOK_BY_ID_ORDER = "SELECT * FROM library.books JOIN library.order_has_books ON (books.idBooks = order_has_books.books_idBooks)JOIN library.Authors ON (books.idAuthor = authors.idAuthor) JOIN library.Genres ON (books.idGenre = genres.idGenre) WHERE order_idOrder=?";
	private static final String SQL_CREATE_BOOK = "INSERT INTO library.books(idBooks,nameBook,impritDate,amount,onlyReadingHall,idGenre,idAuthor,description,picturePath) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String SQL_DELETE_BOOK = "DELETE FROM library.books WHERE idBooks=?";
	private static final String SQL_FIND_GENRE = "SELECT * FROM library.genres WHERE genres.genre =?";
	private static final String SQL_SELECT_GENRE_ALL = "SELECT * FROM library.genres";
	private static final String SQL_FIND_AUTHOR = "SELECT * FROM library.authors WHERE authors.name =? AND authors.surname =?";
	private static final String SQL_UPDATE_BOOK = "UPDATE library.books SET nameBook= ?, impritDate= ?, amount= ?, onlyReadingHall= ?, idGenre= ?, IdAuthor= ?, description=?,picturePath=? WHERE idBooks= ?";
	private static final String SQL_ADD_AUTHOR = "INSERT INTO library.authors(idAuthor,name,surname) VALUES(?, ?, ?)";

	public static BookDAO getInstance() {
		return instance;
	}

	public List<Book> findAll() throws DAOException {
		ArrayList<Book> books = new ArrayList<>();
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		try {
			connection = pool.takeConnection();
			prepareStatement = connection.prepareStatement(SQL_SELECT_BOOK);
			ResultSet resultSet = prepareStatement.executeQuery();
			while (resultSet.next()) {
				Book book = new Book();
				book.setIdBook(resultSet.getInt("books.idBooks"));
				book.setName(resultSet.getString("books.nameBook"));
				book.setImpritYear(resultSet.getString("books.impritDate"));
				book.setAmount(resultSet.getInt("books.amount"));
				book.setOnlyReadingHall(resultSet.getBoolean("books.onlyReadingHall"));
				book.setGenre(resultSet.getString("genres.genre"));
				book.setAuthorName(resultSet.getString("authors.name"));
				book.setAuthorSurname(resultSet.getString("authors.surname"));
				book.setDescription(resultSet.getString("description"));
				book.setPicturePath(resultSet.getString("picturePath"));
				books.add(book);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			pool.closeConnection(connection,prepareStatement);
		}
		return books;
	}

	public HashSet<String> findAllGenre() throws DAOException {
		HashSet<String> genres = new HashSet<String>();
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		try {
			connection = pool.takeConnection();
			prepareStatement = connection.prepareStatement(SQL_SELECT_GENRE_ALL);
			ResultSet resultSet = prepareStatement.executeQuery();
			while (resultSet.next()) {
				String genre = resultSet.getString("genres.genre");
				genres.add(genre);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			pool.closeConnection(connection,prepareStatement);
		}
		return genres;
	}

	public List<Book> findBooksByGenre(String type) throws DAOException {
		ArrayList<Book> books = new ArrayList<>();
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		try {
			connection = pool.takeConnection();
			prepareStatement = connection.prepareStatement(SQL_SELECT_BOOK_BY_GENRE);
			prepareStatement.setString(1, type);
			ResultSet resultSet = prepareStatement.executeQuery();
			while (resultSet.next()) {
				Book book = new Book();
				book.setIdBook(resultSet.getInt("books.idBooks"));
				book.setName(resultSet.getString("books.nameBook"));
				book.setImpritYear(resultSet.getString("books.impritDate"));
				book.setAmount(resultSet.getInt("books.amount"));
				book.setOnlyReadingHall(resultSet.getBoolean("books.onlyReadingHall"));
				book.setGenre(resultSet.getString("genres.genre"));
				book.setAuthorName(resultSet.getString("authors.name"));
				book.setAuthorSurname(resultSet.getString("authors.surname"));
				book.setDescription(resultSet.getString("description"));
				book.setPicturePath(resultSet.getString("picturePath"));
				books.add(book);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			pool.closeConnection(connection,prepareStatement);
		}
		return books;
	}

	public Book findBooksBId(String id) throws DAOException {
		Book book = null;
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		try {
			connection = pool.takeConnection();
			prepareStatement = connection.prepareStatement(SQL_SELECT_BOOK_BY_ID);
			prepareStatement.setString(1, id);
			ResultSet resultSet = prepareStatement.executeQuery();
			if (resultSet.next()) {
				book = new Book();
				book.setIdBook(resultSet.getInt("books.idBooks"));
				book.setName(resultSet.getString("books.nameBook"));
				book.setImpritYear(resultSet.getString("books.impritDate"));
				book.setAmount(resultSet.getInt("books.amount"));
				book.setOnlyReadingHall(resultSet.getBoolean("books.onlyReadingHall"));
				book.setGenre(resultSet.getString("genres.genre"));
				book.setAuthorName(resultSet.getString("authors.name"));
				book.setAuthorSurname(resultSet.getString("authors.surname"));
				book.setDescription(resultSet.getString("description"));
				book.setPicturePath(resultSet.getString("picturePath"));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			pool.closeConnection(connection,prepareStatement);
		}
		return book;
	}
	public List<Book> findBooksByIdOrder(String id) throws DAOException {
		ArrayList<Book> books = new ArrayList<>();
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		try {
			connection = pool.takeConnection();
			prepareStatement = connection.prepareStatement(SQL_SELECT_BOOK_BY_ID_ORDER);
			prepareStatement.setString(1, id);
			ResultSet resultSet = prepareStatement.executeQuery();
			while(resultSet.next()) {
				Book book = new Book();
				book.setIdBook(resultSet.getInt("books.idBooks"));
				book.setName(resultSet.getString("books.nameBook"));
				book.setImpritYear(resultSet.getString("books.impritDate"));
				book.setAmount(resultSet.getInt("books.amount"));
				book.setOnlyReadingHall(resultSet.getBoolean("books.onlyReadingHall"));
				book.setGenre(resultSet.getString("genres.genre"));
				book.setAuthorName(resultSet.getString("authors.name"));
				book.setAuthorSurname(resultSet.getString("authors.surname"));
				book.setDescription(resultSet.getString("description"));
				book.setPicturePath(resultSet.getString("picturePath"));
				books.add(book);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			pool.closeConnection(connection,prepareStatement);
		}
		return books;
	}
	public boolean delete(String idBook) throws DAOException {
		boolean result = false;
		Connection connection = null;
		PreparedStatement prepStatement = null;
		try {
			connection = pool.takeConnection();
			prepStatement = connection.prepareStatement(SQL_DELETE_BOOK);
			prepStatement.setString(1, idBook);
			if (prepStatement.executeUpdate() > 0) {
				result = true;
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			pool.closeConnection(connection,prepStatement);
		}
		return result;
	}

	public boolean create(Book book, String genre, String authorName, String authorSurname)
			throws DAOException {
		boolean result = false;
		Connection connection = null;
		PreparedStatement prepStatement = null;
		try {
			int idGenre = findGenreByName(genre);
			int idAuthor = findAuthorByNameSurname(authorName, authorSurname);
			connection = pool.takeConnection();
			prepStatement = connection.prepareStatement(SQL_CREATE_BOOK);
			prepStatement.setInt(1, book.getIdBook());
			prepStatement.setString(2, book.getName());
			prepStatement.setString(3, book.getImpritYear());
			prepStatement.setInt(4, book.getAmount());
			prepStatement.setBoolean(5, book.isOnlyReadingHall());
			prepStatement.setInt(6, idGenre);
			prepStatement.setInt(7, idAuthor);
			prepStatement.setString(8,book.getDescription());
			prepStatement.setString(9,book.getPicturePath());
			if (prepStatement.executeUpdate() > 0) {
				result = true;
			}
		} catch (SQLException | DAOException e) {
			throw new DAOException(e);
		} finally {
			pool.closeConnection(connection,prepStatement);
		}
		return result;
	}

	public int findGenreByName(String genre) throws DAOException {
		int result = 0;
		
		Connection connection = null;
		PreparedStatement prepStatement = null;
		try {
			connection = pool.takeConnection();
			prepStatement = connection.prepareStatement(SQL_FIND_GENRE);
			prepStatement.setString(1, genre);
			ResultSet resultSet = prepStatement.executeQuery();
			if (resultSet.next()) {
				result = resultSet.getInt("genres.idGenre");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			pool.closeConnection(connection,prepStatement);
		}
		return result;
	}

	public int findAuthorByNameSurname(String authorName, String authorSurname) throws DAOException {
		int result = 0;
		boolean flag = false;
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		try {
			connection = pool.takeConnection();
			prepareStatement = connection.prepareStatement(SQL_FIND_AUTHOR);
			prepareStatement.setString(1, authorName);
			prepareStatement.setString(2, authorSurname);
			ResultSet resultSet = prepareStatement.executeQuery();
			if (resultSet.next()) {
				result = resultSet.getInt("authors.idAuthor");
				flag=true;
			}
		
				
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			pool.closeConnection(connection,prepareStatement);
		}
		if(!flag){
			result = addAuthor(authorName,authorSurname);
		}
		return result;
	}

	private int addAuthor(String authorName, String authorSurname) throws DAOException {
		int result = 0;
		Connection connection = null;
		PreparedStatement prepStatement = null;
		try {
			connection = pool.takeConnection();
			prepStatement = connection.prepareStatement(SQL_ADD_AUTHOR);
			prepStatement.setInt(1, Math.abs(authorName.hashCode() + authorSurname.hashCode()));
			prepStatement.setString(2, authorName);
			prepStatement.setString(3, authorSurname);
			if (prepStatement.executeUpdate() > 0) {
				result = Math.abs(authorName.hashCode() + authorSurname.hashCode());
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			pool.closeConnection(connection,prepStatement);
		}
		return result;
	}

	public boolean updateBookInformation(Book book) throws DAOException {
		boolean result = false;
		Connection connection = null;
		PreparedStatement prepStatement = null;
		try {
			int idGenre = findGenreByName(book.getGenre());
			int idAuthor = findAuthorByNameSurname(book.getAuthorName(), book.getAuthorSurname());
			connection = pool.takeConnection();
			prepStatement = connection.prepareStatement(SQL_UPDATE_BOOK);
			prepStatement.setString(1, book.getName());
			prepStatement.setString(2, book.getImpritYear());
			prepStatement.setInt(3, book.getAmount());
			prepStatement.setBoolean(4, book.isOnlyReadingHall());
			prepStatement.setInt(5, idGenre);
			prepStatement.setInt(6, idAuthor);
			prepStatement.setString(7,book.getDescription());
			prepStatement.setString(8,book.getPicturePath());
			prepStatement.setInt(9, book.getIdBook());
			if (prepStatement.executeUpdate() > 0) {
				result = true;
			}
		} catch (SQLException | DAOException e) {
			throw new DAOException(e);
		} finally {
			pool.closeConnection(connection,prepStatement);
		}
		return result;
	}
}
