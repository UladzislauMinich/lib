package by.epam.library.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import by.epam.library.entity.Book;
import by.epam.library.entity.Order;
import by.epam.library.entity.User;
import by.epam.library.exception.DAOException;

public class OrderDAO extends AbstractDAO {
	private static OrderDAO instance = new OrderDAO();
	private static final String SQL_SELECT_ORDER_ALL = "SELECT * FROM library.order JOIN library.order_has_books ON (idOrder=order_has_books.order_idOrder) JOIN library.books ON (idBooks=order_has_books.books_idBooks)JOIN library.authors ON(books.idAuthor = authors.idAuthor) JOIN library.Genres ON (books.idGenre = genres.idGenre)";
	private static final String SQL_FIND_ORDER_BOOK_BY_LOGIN = "SELECT * FROM library.order JOIN library.order_has_books ON (idOrder=order_has_books.order_idOrder) JOIN library.books ON (idBooks=order_has_books.books_idBooks)JOIN library.authors ON(books.idAuthor = authors.idAuthor) JOIN library.Genres ON (books.idGenre = genres.idGenre) WHERE users_Login=? ORDER BY order.date DESC";
	private static final String SQL_FIND_ORDER_BOOK_BY_IDBOOK = "SELECT * FROM library.order_has_books where books_idBooks=?";
	private static final String SQL_CREATE_ORDER = "INSERT INTO library.order (idOrder, users_Login,status,date) VALUES (?,?,?,?)";
	private static final String SQL_DELETE_ORDER = "DELETE FROM library.order WHERE idOrder= ?";
	private static final String SQL_DELETE_ORDER_BOOKS = "DELETE FROM library.order_has_books WHERE order_idOrder= ?";
	private static final String SQL_ADD_BOOK_TO_ORDER = "INSERT INTO library.order_has_books (order_idOrder, books_idBooks) VALUES (?,?)";
	private static final String SQL_UPDATE_ORDER_STATUS = "UPDATE library.order SET status= ? WHERE idOrder= ?";

	private OrderDAO() {
		super();
	}

	public static OrderDAO getInstance() {

		return instance;
	}

	public ArrayList<Order> findAll() throws DAOException {
		ArrayList<Order> orders = new ArrayList<>();
		Order order = null;
		User user = new User();
		ArrayList<Book> books = new ArrayList<>();
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		try {
			connection = pool.takeConnection();
			prepareStatement = connection.prepareStatement(SQL_SELECT_ORDER_ALL);
			ResultSet resultSet = prepareStatement.executeQuery();
			int flag = 0;
			while (resultSet.next()) {
				flag = resultSet.getInt("idOrder");

				if (order != null && order.getIdOrder() != flag && order.getBooks() != null) {
					orders.add(order);
					order = new Order();
				}

				if (order == null || order.getIdOrder() != flag) {
					order = new Order();
					order.setIdOrder(flag);
					order.setStatus(resultSet.getInt("order.status"));
					order.setDate(resultSet.getString("order.date"));
					books = new ArrayList<Book>();
					user = new User();

				}
				if (order.getIdOrder() == resultSet.getInt("idOrder")) {
					Book book = new Book();
					book.setIdBook(resultSet.getInt("books.idBooks"));
					book.setName(resultSet.getString("books.nameBook"));
					book.setImpritYear(resultSet.getString("books.impritDate"));
					book.setAmount(resultSet.getInt("books.amount"));
					book.setOnlyReadingHall(resultSet.getBoolean("books.onlyReadingHall"));
					book.setGenre(resultSet.getString("genres.genre"));
					book.setAuthorName(resultSet.getString("authors.name"));
					book.setAuthorSurname(resultSet.getString("authors.surname"));
					user.setLogin(resultSet.getString("order.users_Login"));
					books.add(book);
					order.setBooks(books);
					order.setUser(user);
				}

			}
			orders.add(order);
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			pool.closeConnection(connection, prepareStatement);
		}
		return orders;
	}

	public ArrayList<Order> findOrderByLogin(String login, User user) throws DAOException {
		ArrayList<Order> orders = new ArrayList<>();
		Order order = null;
		ArrayList<Book> books = new ArrayList<>();
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		try {
			connection = pool.takeConnection();
			prepareStatement = connection.prepareStatement(SQL_FIND_ORDER_BOOK_BY_LOGIN);
			prepareStatement.setString(1, login);
			ResultSet resultSet = prepareStatement.executeQuery();
			int flag = 0;
			while (resultSet.next()) {
				flag = resultSet.getInt("idOrder");

				if (order != null && order.getIdOrder() != flag && order.getBooks() != null) {
					orders.add(order);
				}

				if (order == null || order.getIdOrder() != flag) {
					order = new Order();
					order.setIdOrder(flag);
					order.setStatus(resultSet.getInt("order.status"));
					order.setDate(resultSet.getString("order.date"));
					books = new ArrayList<Book>();

				}
				if (order.getIdOrder() == resultSet.getInt("idOrder")) {
					Book book = new Book();
					book.setIdBook(resultSet.getInt("books.idBooks"));
					book.setName(resultSet.getString("books.nameBook"));
					book.setImpritYear(resultSet.getString("books.impritDate"));
					book.setAmount(resultSet.getInt("books.amount"));
					book.setOnlyReadingHall(resultSet.getBoolean("books.onlyReadingHall"));
					book.setGenre(resultSet.getString("genres.genre"));
					book.setAuthorName(resultSet.getString("authors.name"));
					book.setAuthorSurname(resultSet.getString("authors.surname"));
					books.add(book);
					order.setBooks(books);
					order.setUser(user);
				}

			}
			if (order != null) {
				orders.add(order);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			pool.closeConnection(connection, prepareStatement);
		}
		return orders;
	}

	public boolean findOrdersBookByIdBook(int idBook) throws DAOException {
		boolean result = false;
		Connection connection = null;
		PreparedStatement prepStatement = null;
		try {
			connection = pool.takeConnection();
			prepStatement = connection.prepareStatement(SQL_FIND_ORDER_BOOK_BY_IDBOOK);
			prepStatement.setInt(1, idBook);
			ResultSet rs = prepStatement.executeQuery();
			result = rs.first();

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			pool.closeConnection(connection, prepStatement);
		}
		return result;
	}

	public boolean delete(int idOrder) throws DAOException {
		boolean result = false;
		Connection connection = null;
		PreparedStatement prepStatement = null;
		try {
			connection = pool.takeConnection();
			prepStatement = connection.prepareStatement(SQL_DELETE_ORDER);
			prepStatement.setInt(1, idOrder);
			if (deleteFromOrdersBooks(idOrder)) {
				int count = prepStatement.executeUpdate();
				if (count > 0) {
					result = true;
				}
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			pool.closeConnection(connection, prepStatement);
		}
		return result;
	}

	public boolean deleteFromOrdersBooks(int idOrder) throws DAOException {
		boolean result = false;
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		try {
			connection = pool.takeConnection();
			prepareStatement = connection.prepareStatement(SQL_DELETE_ORDER_BOOKS);
			prepareStatement.setInt(1, idOrder);
			if (prepareStatement.executeUpdate() > 0) {
				result = true;
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			pool.closeConnection(connection, prepareStatement);
		}
		return result;
	}

	public boolean create(Order order) throws DAOException {
		boolean result = false;
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		GregorianCalendar gc = new GregorianCalendar();
		try {
			connection = pool.takeConnection();
			prepareStatement = connection.prepareStatement(SQL_CREATE_ORDER);
			prepareStatement.setInt(1, order.getIdOrder());
			prepareStatement.setString(2, order.getUser().getLogin());
			prepareStatement.setInt(3, order.getStatus());
			prepareStatement.setString(4, "" + gc.getTime());

			if (prepareStatement.executeUpdate() == 1
					&& addBooksToOrder(order.getIdOrder(), order.getBooks())) {
				result = true;
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			pool.closeConnection(connection, prepareStatement);
		}
		return result;
	}

	public boolean addBooksToOrder(int idOrder, List<Book> list) throws DAOException {

		boolean result = false;
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		try {
			connection = pool.takeConnection();
			prepareStatement = connection.prepareStatement(SQL_ADD_BOOK_TO_ORDER);
			for (Book book : list) {
				result = false;
				prepareStatement.setInt(1, idOrder);
				prepareStatement.setInt(2, book.getIdBook());
				if (prepareStatement.executeUpdate() == 1) {
					result = true;
				}
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			pool.closeConnection(connection, prepareStatement);
		}
		return result;
	}

	public boolean updateStatus(int idOrder, int status) throws DAOException {
		boolean result = false;
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		try {
			connection = pool.takeConnection();
			prepareStatement = connection.prepareStatement(SQL_UPDATE_ORDER_STATUS);
			if (status == 1) {
				prepareStatement.setInt(1, 0);
			} else {
				prepareStatement.setInt(1, 1);
			}
			prepareStatement.setInt(2, idOrder);
			if (prepareStatement.executeUpdate() == 1) {
				result = true;
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			pool.closeConnection(connection, prepareStatement);
		}
		return result;
	}
}
