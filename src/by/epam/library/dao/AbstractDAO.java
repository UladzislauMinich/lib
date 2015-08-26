package by.epam.library.dao;

import by.epam.library.pool.DBConnectionPool;

public class AbstractDAO {
	protected  static DBConnectionPool pool;
	
	protected AbstractDAO() {
		pool = DBConnectionPool.getInstance();
	}
}
