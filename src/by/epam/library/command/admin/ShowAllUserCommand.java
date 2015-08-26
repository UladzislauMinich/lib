package by.epam.library.command.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import by.epam.library.command.ActionCommand;
import by.epam.library.command.ShowAllBookCommand;
import by.epam.library.dao.UserDAO;
import by.epam.library.entity.User;
import by.epam.library.exception.DAOException;
import by.epam.library.resource.ConfigurationManager;
import by.epam.library.resource.MessageManager;



public class ShowAllUserCommand implements ActionCommand {
	private static Logger log = Logger.getLogger(ShowAllBookCommand.class);
	private final String MESSAGE = "message";
	private final String URL = "url";
	private final String USERS = "users";
	@Override
	public String execute(HttpServletRequest request) {
		String page;
		UserDAO userDAO = UserDAO.getInstance();
		try {
			List<User> users = userDAO.findAllUser();
			if (users.isEmpty()) {
				request.setAttribute(MESSAGE, MessageManager.DATABASE_ERROR);
				page =  ConfigurationManager.getProperty("path.page.error");
			} else {
				request.getSession().setAttribute(USERS, users);
				page =  ConfigurationManager.getProperty("path.page.admin.all-user");
			}
			
		} catch (DAOException e) {
			log.error(e);
			request.setAttribute(MESSAGE,MessageManager.DATABASE_ERROR);
			page =  ConfigurationManager.getProperty("path.page.error");
		}
		request.getSession().setAttribute(URL, page);
		return page;
	}

}