package by.epam.library.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import by.epam.library.dao.BookDAO;
import by.epam.library.entity.Book;
import by.epam.library.exception.DAOException;
import by.epam.library.resource.ConfigurationManager;
import by.epam.library.resource.MessageManager;

public class ShowAllBookCommand implements ActionCommand {
	private static Logger log = Logger.getLogger(ShowAllBookCommand.class);
	private final String MESSAGE = "message";
	private final String URL = "url";
	private final String BOOKS = "books";

	@Override
	public String execute(HttpServletRequest request) {
		String page = null;
		BookDAO bookDAO = BookDAO.getInstance();
		try {
			List<Book> books = bookDAO.findAll();
			if (books.isEmpty()) {
				request.setAttribute(MESSAGE, MessageManager.HAVE_NOT_BOOK);
				page = ConfigurationManager.getProperty("path.page.books");
			} else {
				request.getSession().setAttribute(BOOKS, books);
				page = ConfigurationManager.getProperty("path.page.books");
			}

		} catch (DAOException e) {
			log.error(e);
			request.setAttribute(MESSAGE, MessageManager.DATABASE_ERROR);
			page = ConfigurationManager.getProperty("path.page.error");
		}
		request.getSession().setAttribute(URL, page);
		return page;
	}
}
