package by.epam.library.command.admin;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import by.epam.library.command.ActionCommand;
import by.epam.library.dao.OrderDAO;
import by.epam.library.dao.UserDAO;
import by.epam.library.entity.Order;
import by.epam.library.entity.User;
import by.epam.library.exception.DAOException;
import by.epam.library.resource.ConfigurationManager;
import by.epam.library.resource.MessageManager;

public class DeleteUserCommand implements ActionCommand {
	private static Logger log = Logger.getLogger(DeleteBookCommand.class);
	private final String MESSAGE = "message";
	private final String URL = "url";
	private final String LOGIN_USER = "loginUser";
	private final String ADMIN_FLAG = "user.adminFlag";
	@Override
	public String execute(HttpServletRequest request) {
		String page = null;
		String loginUser = request.getParameter(LOGIN_USER);
		String adminFlag =  request.getParameter(ADMIN_FLAG);
		User user = null;
		
		UserDAO userDAO = UserDAO.getInstance();
		OrderDAO orderDAO=OrderDAO.getInstance();
		try {
			ArrayList<Order> orders =orderDAO.findOrderByLogin(loginUser, user);
			if(adminFlag.equals("1")){//it's parametr's value of admin
				request.setAttribute(MESSAGE, MessageManager.ERROR_DELETE_ADMIN);
				page = ConfigurationManager.getProperty("path.page.admin.all-user");
			}
			else if(!orders.isEmpty()){
				request.setAttribute(MESSAGE, MessageManager.ERROR_DELETE_USER_HAVE_ORDER);
				page = ConfigurationManager.getProperty("path.page.admin.all-user");
			}
			else if (userDAO.delete(loginUser)) {
				request.setAttribute(MESSAGE, MessageManager.DELETE_USER_SUCCESS);
				page = ConfigurationManager.getProperty("path.page.main");
			} else {
				request.setAttribute(MESSAGE, MessageManager.ERROR_DELETE_USER);
				page = ConfigurationManager.getProperty("path.page.admin.all-user");
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