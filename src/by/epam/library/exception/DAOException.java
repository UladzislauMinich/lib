package by.epam.library.exception;

@SuppressWarnings("serial")
public class DAOException extends Exception{

	public DAOException() {
		super();
	}

	public DAOException(String msg, Throwable e) {
		super(msg, e);
	}

	public DAOException(String msg) {
		super(msg);
	}

	public DAOException(Throwable e) {
		super(e);
	}
}
