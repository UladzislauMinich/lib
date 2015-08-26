package by.epam.library.command.admin;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import by.epam.library.command.ActionCommand;
import by.epam.library.dao.OrderDAO;
import by.epam.library.entity.Order;
import by.epam.library.exception.DAOException;
import by.epam.library.resource.ConfigurationManager;
import by.epam.library.resource.MessageManager;

public class CompleteOrderCommand implements ActionCommand {
	private static Logger log = Logger.getLogger(CompleteOrderCommand.class);
	private final String URL = "url";
	private final String ID_ORDER = "order.idOrder";
	private final String ORDERS = "orders";
	private final String MESSAGE = "message";

	@SuppressWarnings("unchecked")
	@Override
	public String execute(HttpServletRequest request) {
		String page = null;
		ArrayList<Order> orders = (ArrayList<Order>) request.getSession()
				.getAttribute(ORDERS);
		String idOrder = request.getParameter(ID_ORDER);
		OrderDAO orderDAO = OrderDAO.getInstance();
		try {
			for (Order order : orders) {
				if (order.getIdOrder() == Integer.parseInt(idOrder)) {
					if (orderDAO.updateStatus(order.getIdOrder(),
							order.getStatus())) {
						orders = orderDAO.findAll();
						request.getSession().setAttribute(ORDERS, orders);
					} else {
						request.setAttribute(MESSAGE,
								MessageManager.ERROR_COMPLETE_ORDER);
					}
				}
			}
		} catch (DAOException e) {
			log.error(e);
			request.setAttribute(MESSAGE, MessageManager.DATABASE_ERROR);
			page = ConfigurationManager.getProperty("path.page.error");
		}
		request.getSession().setAttribute(ORDERS, orders);// ???
		page = ConfigurationManager.getProperty("path.page.order.admin");
		request.getSession().setAttribute(URL, page);
		return page;
	}
}
