package by.epam.library.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import by.epam.library.dao.BookDAO;
import by.epam.library.entity.Book;
import by.epam.library.exception.DAOException;
import by.epam.library.resource.ConfigurationManager;
import by.epam.library.resource.MessageManager;

public class ShowBookByGenreCommand implements ActionCommand {
	private static Logger log = Logger.getLogger(ShowBookByGenreCommand.class);
	private final String MESSAGE = "message";
	private final String URL = "url";
	private final String BOOKS = "books";
	private final String BOOK_TYPE = "book_type";

	@Override
	public String execute(HttpServletRequest request) {
		String page = null;
		String type = request.getParameter(BOOK_TYPE);
		BookDAO bookDAO = BookDAO.getInstance();
		try {
			List<Book> books = bookDAO.findBooksByGenre(type);
			if (books.isEmpty()) {
				request.setAttribute(MESSAGE, MessageManager.GENRE_IS_EMPTY);
				request.getSession().setAttribute(BOOKS, null);
				page = ConfigurationManager.getProperty("path.page.books");
			} else {
				request.getSession().setAttribute(BOOKS, books);
				page =ConfigurationManager.getProperty("path.page.books");
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
	