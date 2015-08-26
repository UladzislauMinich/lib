package by.epam.library.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import by.epam.library.dao.BookDAO;
import by.epam.library.entity.Book;
import by.epam.library.exception.DAOException;
import by.epam.library.resource.ConfigurationManager;
import by.epam.library.resource.MessageManager;

public class LogoutCommand implements ActionCommand {
	private static Logger log = Logger.getLogger(AddBookToBasketCommand.class);
	private final String BASKET_BOOKS = "basketBooks";
	private final String MESSAGE = "message";

	@SuppressWarnings("unchecked")
	@Override
	public String execute(HttpServletRequest request) {
		String page = ConfigurationManager.getProperty("path.page.index");
		ArrayList<Book> basketBooks = (ArrayList<Book>) request.getSession().getAttribute(BASKET_BOOKS);
		BookDAO bookDAO = BookDAO.getInstance();
		try {
			if (basketBooks != null) {
				for (Book book : basketBooks) {
					book.setAmount(book.getAmount() + 1);
					bookDAO.updateBookInformation(book);
				}
				basketBooks=null;
			}
		} catch (DAOException e) {
			log.error(e);
			request.setAttribute(MESSAGE, MessageManager.DATABASE_ERROR);
			page = ConfigurationManager.getProperty("path.page.error");
		}
		request.getSession().invalidate();
		return page;
	}
}
