package by.epam.library.command.admin;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import by.epam.library.command.ActionCommand;
import by.epam.library.dao.BookDAO;
import by.epam.library.dao.OrderDAO;
import by.epam.library.entity.Book;
import by.epam.library.exception.DAOException;
import by.epam.library.resource.ConfigurationManager;
import by.epam.library.resource.MessageManager;

public class DeleteBookCommand implements ActionCommand {
	private static Logger log = Logger.getLogger(DeleteBookCommand.class);
	private final String MESSAGE = "message";
	private final String URL = "url";
	private final String ID_BOOK = "idBook";
	private final String BASKET_BOOKS = "basketBooks";

	@SuppressWarnings("unchecked")
	@Override
	public String execute(HttpServletRequest request) {
		String page = null;
		String idBook = (request.getParameter(ID_BOOK));
		ArrayList<Book> basketBooks = (ArrayList<Book>) request.getSession()
				.getAttribute(BASKET_BOOKS);
		BookDAO bookDAO = BookDAO.getInstance();
		OrderDAO orderDAO = OrderDAO.getInstance();
		if (basketBooks!=null) {
			for (Book book : basketBooks) {
				if (book.getIdBook() == Integer.parseInt(idBook)) {
					request.setAttribute(MESSAGE,
							MessageManager.ERROR_DELETE_BOOK);
					request.setAttribute(MESSAGE,
							MessageManager.ERROR_DELETE_BOOK);
					page = ConfigurationManager.getProperty("path.page.main");
					request.getSession().setAttribute(URL, page);
					return page;
				}
			}
		}
		try {
			if (!orderDAO.findOrdersBookByIdBook(Integer.parseInt(idBook))) {
				if (bookDAO.delete(idBook)) {
					request.setAttribute(MESSAGE,
							MessageManager.DELETE_BOOK_SUCCESS);
					page = ConfigurationManager.getProperty("path.page.main");
				} else {
					request.setAttribute(MESSAGE,
							MessageManager.ERROR_DELETE_BOOK);
					page = ConfigurationManager.getProperty("path.page.error");
				}
			} else {
				request.setAttribute(MESSAGE, MessageManager.ERROR_DELETE_BOOK);
				page = ConfigurationManager.getProperty("path.page.main");
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
