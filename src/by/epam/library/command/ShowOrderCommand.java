package by.epam.library.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;




import by.epam.library.dao.OrderDAO;
import by.epam.library.entity.Order;
import by.epam.library.entity.User;
import by.epam.library.exception.DAOException;
import by.epam.library.resource.ConfigurationManager;
import by.epam.library.resource.MessageManager;

public class ShowOrderCommand implements ActionCommand {
	private static Logger log = Logger.getLogger(ShowOrderCommand.class);
	private final String MESSAGE = "message";
	private final String URL = "url";
	private final String ADMIN_FLAG = "user.adminFlag";
	private final String ORDERS = "orders";
	private final String USER = "user";
	
	@Override
	public String execute(HttpServletRequest request) {
		String page = null;
		try {
		String isAdmin = (String) request.getParameter(ADMIN_FLAG);
		OrderDAO orderDAO = OrderDAO.getInstance();
		if (isAdmin.equals("0")) {
			User user = (User) request.getSession().getAttribute(USER);
			ArrayList<Order> orders = orderDAO.findOrderByLogin(user.getLogin(), user);
			request.getSession().setAttribute(ORDERS, orders);
			page = ConfigurationManager.getProperty("path.page.order.user");
		} else if (isAdmin.equals("1")) {
			ArrayList<Order> orders = orderDAO.findAll();
			request.getSession().setAttribute(ORDERS, orders);
			page = ConfigurationManager.getProperty("path.page.order.admin");
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
