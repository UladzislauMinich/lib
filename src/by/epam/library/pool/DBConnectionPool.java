package by.epam.library.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import com.mysql.jdbc.Driver;

/**
 * Class provides methods to work properly with resources of DB. After using -
 * {@link #dispose()}, to free resources.
 */
public final class DBConnectionPool {

	private static Logger log = Logger.getLogger(DBConnectionPool.class);

	private ArrayBlockingQueue<Connection> connections;
	private String driver;
	private String url;
	private String user;
	private String password;
	private int poolSize;
	private static AtomicBoolean isNull = new AtomicBoolean(true);
	private static ReentrantLock lock = new ReentrantLock();
	private static DBConnectionPool connectionPool;

	/**
	 * Returns singleton object of this class.
	 * 
	 * @return singleton object of this class.
	 */
	public static DBConnectionPool getInstance() {
		if (isNull.get()) {
			lock.lock();
			try {
				if (isNull.get()) {
					connectionPool = new DBConnectionPool();
					isNull.set(false);
				}
			} finally {
				lock.unlock();
			}
		}
		return connectionPool;
	}

	private DBConnectionPool() {
		try {
			DriverManager.registerDriver(new Driver());
			this.driver = DBResourceManager.getValue(DBPropertyName.DB_DRIVER);
			this.url = DBResourceManager.getValue(DBPropertyName.DB_URL);
			this.user = DBResourceManager.getValue(DBPropertyName.DB_USER);
			this.password = DBResourceManager.getValue(DBPropertyName.DB_PASSWORD);
			this.poolSize = Integer.parseInt(DBResourceManager.getValue(DBPropertyName.DB_POOLSIZE));

			Class.forName(driver);
			connections = new ArrayBlockingQueue<>(poolSize);
			for (int i = 0; i < poolSize; i++) {
				connections.put(DriverManager.getConnection(url, user, password));
			}
		} catch (SQLException | InterruptedException | ClassNotFoundException e) {
			log.fatal("Error of initialization's pool:  " + e);
			throw new ExceptionInInitializerError(e);
		}
	}

	/**
	 * Returns connection if any available, otherwise waits until an element
	 * becomes available.
	 * 
	 * @return connection to use.
	 * 
	 */
	public Connection takeConnection() {
		Connection connection = null;
		try {
			connection = connections.take();
		} catch (InterruptedException e) {
			log.error("Error in getting connections " + e);
		}
		return connection;
	}

	/**
	 * Closes connection
	 * 
	 * @param connection
	 *          coonection to close.
	 */
	public void closeConnection(Connection connection, Statement statement) {
		try {
			statement.close();
			if (!connection.isClosed()) {
				connections.put(connection);
			}
		} catch (SQLException | InterruptedException e) {
			log.error("Error in closing connections" + e);
		}
	}

	/**
	 * Disposes connection pool. Frees resources.
	 * 
	 */
	public void release() {
		Iterator<Connection> iterator = connections.iterator();
		while (iterator.hasNext()) {
			try {
				iterator.next().close();
			} catch (SQLException e) {
				log.error("Error in disposing connections " + e);
			}
		}
	}

}