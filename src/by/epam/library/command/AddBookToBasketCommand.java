package by.epam.library.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import by.epam.library.dao.BookDAO;
import by.epam.library.entity.Book;
import by.epam.library.entity.User;
import by.epam.library.exception.DAOException;
import by.epam.library.resource.ConfigurationManager;
import by.epam.library.resource.MessageManager;

public class AddBookToBasketCommand implements ActionCommand {
	private static Logger log = Logger.getLogger(AddBookToBasketCommand.class);
	private final String AMOUNT = "amount";
	private final String MESSAGE = "message";
	private final String URL = "url";
	private final String ID_BOOK = "idBook";
	private final String USER = "user";
	private final String BOOKS = "books";
	private final String BASKET_BOOKS = "basketBooks";

	@SuppressWarnings("unchecked")
	@Override
	public String execute(HttpServletRequest request) {
		String page = null;
		String idBook = request.getParameter(ID_BOOK);
		String amount = request.getParameter(AMOUNT);
		User user = (User) request.getSession().getAttribute(USER);
		page = (String) request.getSession().getAttribute(URL);
		ArrayList<Book> basketBooks = new ArrayList<>();
		ArrayList<Book> books = (ArrayList<Book>) request.getSession()
				.getAttribute(BOOKS);
		basketBooks = (ArrayList<Book>) request.getSession().getAttribute(
				BASKET_BOOKS);

		if (basketBooks == null) {
			basketBooks = new ArrayList<Book>();
		}
		for (Book book : basketBooks) {
			if (book.getIdBook() == Integer.parseInt(idBook)) {
				request.setAttribute(
						MESSAGE,
						MessageManager.ADD_BOOK_TO_BASKET_MORE_ONE_ERROR_NO_USER);
				request.setAttribute(BOOKS, books);
				request.getSession().setAttribute(URL, page);
				return page;
			}
		}
		BookDAO bookDAO = BookDAO.getInstance();
		try {
			if (user != null) {
				Book book = bookDAO.findBooksBId(idBook);
				if (book != null && book.getAmount() != 0) {
					basketBooks.add(book);
					book.setAmount(book.getAmount() - 1);
					bookDAO.updateBookInformation(book);
					request.getSession().setAttribute(AMOUNT,
							Integer.parseInt(amount) + 1);
					request.getSession()
							.setAttribute(BASKET_BOOKS, basketBooks);
					for (Book bookTemp : books) {
						if (book.getIdBook() == bookTemp.getIdBook()) {
							bookTemp.setAmount(bookTemp.getAmount() - 1);
							break;
						}
					}

				} else {

					request.setAttribute(MESSAGE,
							MessageManager.ADD_BOOK_TO_BASKET_ERROR);
				}

			} else {
				request.setAttribute(MESSAGE,
						MessageManager.ADD_BOOK_TO_BASKET_ERROR_NO_USER);
			}

		} catch (DAOException e) {
			log.error(e);
			request.setAttribute(MESSAGE, MessageManager.DATABASE_ERROR);
			page = ConfigurationManager.getProperty("path.page.error");
		}
		request.setAttribute(BOOKS, books);
		request.getSession().setAttribute(URL, page);
		return page;
	}
}
