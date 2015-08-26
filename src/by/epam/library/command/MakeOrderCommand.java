package by.epam.library.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import by.epam.library.dao.OrderDAO;
import by.epam.library.entity.Book;
import by.epam.library.entity.Order;
import by.epam.library.entity.User;
import by.epam.library.exception.DAOException;
import by.epam.library.resource.ConfigurationManager;
import by.epam.library.resource.MessageManager;

public class MakeOrderCommand implements ActionCommand {
	private static Logger log = Logger.getLogger(MakeOrderCommand.class);
	private final String USER = "user";
	private final String URL = "url";
	private final String MESSAGE = "message";
	private final String AMOUNT = "amount";
	private final String BASKET_BOOKS = "basketBooks";

	@SuppressWarnings("unchecked")
	@Override
	public String execute(HttpServletRequest request) {
		String page = null;
		ArrayList<Book> books = (ArrayList<Book>) request.getSession()
				.getAttribute(BASKET_BOOKS);
		User user = (User) request.getSession().getAttribute(USER);
		if (user == null) {
			page = ConfigurationManager.getProperty("path.page.main");
			request.getSession().setAttribute(URL, page);
			return page;
		}
		OrderDAO orderDAO = OrderDAO.getInstance();
		Order order = new Order();
		order.setBooks(books);
		order.setUser(user);
		order.setStatus(1);// default value
		order.setIdOrder(Math.abs(order.hashCode()));
		try {
			if (orderDAO.create(order)) {
				request.getSession().setAttribute(BASKET_BOOKS, null);
				request.setAttribute(MESSAGE, MessageManager.MAKE_ORDER_SUCCESS);
				page = ConfigurationManager.getProperty("path.page.main");
				request.getSession().setAttribute(AMOUNT, 0);// when yoo make order, basket become an empty
			} else {
				request.setAttribute(MESSAGE, MessageManager.MAKE_ORDER_ERROR);
				page = ConfigurationManager.getProperty("path.page.main");
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
