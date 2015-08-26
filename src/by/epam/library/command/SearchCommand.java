package by.epam.library.command;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

import org.apache.log4j.Logger;

import by.epam.library.dao.BookDAO;
import by.epam.library.entity.Book;
import by.epam.library.exception.DAOException;
import by.epam.library.resource.ConfigurationManager;
import by.epam.library.resource.MessageManager;

public class SearchCommand implements ActionCommand {
	private static Logger log = Logger.getLogger(SearchCommand.class);
	private final String URL = "url";
	private final String MESSAGE = "message";
	private final String TEXT = "text";
	private final String BOOKS = "books";

	@Override
	public String execute(HttpServletRequest request) {
		String page = null;
		String text = request.getParameter(TEXT);
		if (text.length() == 0) {
			request.setAttribute(MESSAGE, MessageManager.SEARCHING_BOOK_ERROR);
			page = ConfigurationManager.getProperty("path.page.main");
			request.getSession().setAttribute(URL, page);
			return page;
		}
		Pattern textPattern = Pattern.compile(text.toLowerCase());
		BookDAO bookDAO = BookDAO.getInstance();
		try {
			ArrayList<Book> findBooks = new ArrayList<>();
			List<Book> books = bookDAO.findAll();
			for (Book book : books) {
				if (textPattern
						.matcher(
								(book.getName() + " " + book.getAuthorName()
										+ " " + book.getAuthorSurname())
										.toLowerCase()).find()) {
					findBooks.add(book);
				}
			}
			if (findBooks.isEmpty()) {
				request.setAttribute(MESSAGE,
						MessageManager.SEARCHING_BOOK_NOT_FOUND);
				request.setAttribute(TEXT, text);
				page = ConfigurationManager.getProperty("path.page.main");
			} else {
				request.setAttribute(MESSAGE,
						MessageManager.SEARCHING_BOOK_SUCCESS);
				request.setAttribute(BOOKS, findBooks);
				request.setAttribute(TEXT, text);
				page = ConfigurationManager.getProperty("path.page.books");
			}
		} catch (DAOException e) {
			log.error("Error. Can't update book's info", e);
			request.setAttribute(MESSAGE, MessageManager.DATABASE_ERROR);
			page = ConfigurationManager.getProperty("path.page.error");
		}
		request.getSession().setAttribute(URL, page);
		return page;
	}
}
