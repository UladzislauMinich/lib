package by.epam.library.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.epam.library.entity.User;
import by.epam.library.exception.DAOException;

public class UserDAO extends AbstractDAO {

	private static UserDAO instance = new UserDAO();
	private final static String SQL_SIGNUP_USER = "INSERT INTO library.users(idUser, login, password, name, surname, isAdmin) VALUES(?, ?, ?, ?, ?, ?)";
	private final static String SQL_IS_USER_EXISTS = "SELECT * FROM library.users WHERE login=?";
	private final static String SQL_FIND_USER_BY_LOGIN_AND_PASSWORD = "SELECT * FROM library.users WHERE login=? AND password=?";
	private final static String SQL_FIND_ALL_USER = "SELECT * FROM library.users";
	private final static String SQL_DELETE_USER = "DELETE  FROM library.users where users.login =?";

	private UserDAO() {
		super();
	}

	public static UserDAO getInstance() {

		return instance;
	}

	public List<User> findAllUser() throws DAOException {
		ArrayList<User> users = new ArrayList<>();
		Connection connection = null;
		PreparedStatement prepStatement = null;
		try {
			connection = pool.takeConnection();
			prepStatement = connection.prepareStatement(SQL_FIND_ALL_USER);
			ResultSet resultSet = prepStatement.executeQuery();
			while (resultSet.next()) {
				User user = new User();
				user.setIdUser(resultSet.getInt("users.idUser"));
				user.setLogin(resultSet.getString("users.login"));
				user.setName(resultSet.getString("users.name"));
				user.setSurname(resultSet.getString("users.surname"));
				user.setPassword(resultSet.getString("users.password"));
				user.setAdminFlag(resultSet.getInt("users.isAdmin"));
				users.add(user);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			pool.closeConnection(connection, prepStatement);
		}
		return users;

	}

	public boolean signUp(User user) throws DAOException {
		if (user == null) {
			throw new DAOException("User can't be null");
		}
		boolean result = false;
		if (isUserExists(user.getLogin())) {
			return result;
		}
		Connection connection = null;
		PreparedStatement prepStatement = null;
		try {
			connection = pool.takeConnection();
			prepStatement = connection.prepareStatement(SQL_SIGNUP_USER);
			prepStatement.setInt(1, user.getIdUser());
			prepStatement.setString(2, user.getLogin());
			prepStatement.setString(3, user.getPassword());
			prepStatement.setString(4, user.getName());
			prepStatement.setString(5, user.getSurname());
			prepStatement.setInt(6, user.getAdminFlag());
			if (prepStatement.executeUpdate() > 0) {
				result = true;
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			pool.closeConnection(connection, prepStatement);
		}

		return result;
	}

	public boolean isUserExists(String login) throws DAOException {
		boolean result = false;
		Connection connection = null;
		PreparedStatement prepStatement = null;
		try {
			connection = pool.takeConnection();
			prepStatement = connection.prepareStatement(SQL_IS_USER_EXISTS);
			prepStatement.setString(1, login);
			ResultSet rs;
			rs = prepStatement.executeQuery();
			result = rs.first();
		} catch (SQLException ex) {
			throw new DAOException("Database error", ex);
		} finally {
			pool.closeConnection(connection, prepStatement);
		}
		return result;
	}

	public User findUserByLoginAndPassword(String login, String password) throws DAOException {
		User user = null;
		Connection connection = null;
		PreparedStatement prepStatement = null;
		try {
			connection = pool.takeConnection();
			prepStatement = connection.prepareStatement(SQL_FIND_USER_BY_LOGIN_AND_PASSWORD);
			prepStatement.setString(1, login);
			prepStatement.setString(2, password);
			ResultSet resultSet = prepStatement.executeQuery();
			if (resultSet.next()) {
				user = new User();
				user.setIdUser(resultSet.getInt("idUser"));
				user.setLogin(resultSet.getString("login"));
				user.setPassword(resultSet.getString("password"));
				user.setName(resultSet.getString("name"));
				user.setSurname(resultSet.getString("surname"));
				user.setAdminFlag(resultSet.getInt("isAdmin"));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			pool.closeConnection(connection, prepStatement);
		}
		return user;
	}

	public boolean delete(String login) throws DAOException {
		boolean result = false;
		Connection connection = null;
		PreparedStatement prepStatement = null;
		try {
			connection = pool.takeConnection();
			prepStatement = connection.prepareStatement(SQL_DELETE_USER);
			prepStatement.setString(1, login);
			if (prepStatement.executeUpdate() > 0) {
				result = true;
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			pool.closeConnection(connection, prepStatement);
		}
		return result;
	}
}