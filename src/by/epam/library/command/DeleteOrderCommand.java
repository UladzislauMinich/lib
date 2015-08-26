package by.epam.library.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import by.epam.library.dao.BookDAO;
import by.epam.library.dao.OrderDAO;
import by.epam.library.entity.Book;
import by.epam.library.exception.DAOException;
import by.epam.library.resource.ConfigurationManager;
import by.epam.library.resource.MessageManager;

public class DeleteOrderCommand implements ActionCommand {
	private static Logger log = Logger.getLogger(DeleteOrderCommand.class);

	private final String MESSAGE = "message";
	private final String URL = "url";
	private final String ID_ORDER = "order.idOrder";

	@Override
	public String execute(HttpServletRequest request) {
		String page = null;
		String idOrder = (request.getParameter(ID_ORDER));
		OrderDAO orderDAO = OrderDAO.getInstance();
		BookDAO bookDAO = BookDAO.getInstance();
		try {
			ArrayList<Book> books = (ArrayList<Book>) bookDAO
					.findBooksByIdOrder(idOrder);
			for (Book book : books) {
				book.setAmount(book.getAmount() + 1);
				bookDAO.updateBookInformation(book);

			}
			if (orderDAO.delete(Integer.parseInt(idOrder))) {
				request.setAttribute(MESSAGE,
						MessageManager.DELETE_ORDER_SUCCESS);
				page = ConfigurationManager.getProperty("path.page.main");
			} else {
				request.setAttribute(MESSAGE, MessageManager.ERROR_DELETE_ORDER);
				page = ConfigurationManager.getProperty("path.page.error");
			}
		} catch (DAOException e) {
			log.error("Error. Can't delete order", e);
			request.setAttribute(MESSAGE, MessageManager.DATABASE_ERROR);
			page = ConfigurationManager.getProperty("path.page.error");
		}
		request.getSession().setAttribute(URL, page);
		return page;

	}
}
