package by.epam.library.command;

import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import by.epam.library.dao.BookDAO;
import by.epam.library.entity.Book;
import by.epam.library.exception.DAOException;
import by.epam.library.resource.ConfigurationManager;
import by.epam.library.resource.MessageManager;

public class DeleteBookFromBasketCommand implements ActionCommand {
	private static Logger log = Logger.getLogger(DeleteBookFromBasketCommand.class);
	private final String BASKET_BOOKS = "basketBooks";
	private final String AMOUNT = "amount";
	private final String MESSAGE = "message";
	private final String URL = "url";
	private final String ID_BOOK = "idBook";

	@SuppressWarnings("unchecked")
	@Override
	public String execute(HttpServletRequest request) {
		String page = null;
		String idBook = (request.getParameter(ID_BOOK));
		ArrayList<Book> basketBooks = (ArrayList<Book>) request.getSession()
				.getAttribute(BASKET_BOOKS);
		String amount = request.getParameter(AMOUNT);
		Iterator<Book> it = basketBooks.iterator();
		BookDAO bookDAO = BookDAO.getInstance();
		try {
			while (it.hasNext()) {
				Book book = (Book) it.next();
				if (book.getIdBook() == Integer.parseInt(idBook)) {
					basketBooks.remove(book);
					book.setAmount(book.getAmount() + 1);
					bookDAO.updateBookInformation(book);
					request.getSession().setAttribute(AMOUNT,
							Integer.parseInt(amount) - 1);
					break;
				}
			}
		} catch (DAOException e) {
			log.error(e);
			request.setAttribute(MESSAGE, MessageManager.DATABASE_ERROR);
			page = ConfigurationManager.getProperty("path.page.error");
		}
		request.getSession().setAttribute(BASKET_BOOKS, basketBooks);
		page = ConfigurationManager.getProperty("path.page.basket");
		request.getSession().setAttribute(URL, page);
		return page;
	}

}
