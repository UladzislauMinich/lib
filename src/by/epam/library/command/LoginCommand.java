package by.epam.library.command;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import by.epam.library.dao.UserDAO;
import by.epam.library.entity.User;
import by.epam.library.exception.DAOException;
import by.epam.library.resource.ConfigurationManager;
import by.epam.library.resource.MessageManager;
import by.epam.library.utils.Coder;

public class LoginCommand implements ActionCommand {

	private static Logger log = Logger.getLogger(LoginCommand.class);

	private final String LOGIN = "login";
	private final String PASSWORD = "password";
	private final String AMOUNT = "amount";
	private final String MESSAGE = "message";
	private final String URL = "url";

	@Override
	public String execute(HttpServletRequest request) {
		String page = null;
		String login = request.getParameter(LOGIN).trim();
		String pass = Coder.hashMD5(request.getParameter(PASSWORD)).trim();
		String amount = request.getParameter(AMOUNT);
		if (login.length() * pass.length() == 0) {
			request.setAttribute(MESSAGE, MessageManager.LOGIN_ERROR);
			page = ConfigurationManager.getProperty("path.page.login");
		}
		UserDAO userDAO = UserDAO.getInstance();
		try {
			User user = userDAO.findUserByLoginAndPassword(login, pass);
			if (user == null) {
				request.setAttribute(MESSAGE, MessageManager.LOGIN_ERROR);
				page = ConfigurationManager.getProperty("path.page.login");
			} else {
				request.getSession().setAttribute("user", user);
				page = ConfigurationManager.getProperty("path.page.main");
			}
		} catch (DAOException e) {
			log.error(e);
			request.setAttribute(MESSAGE, MessageManager.DATABASE_ERROR);
			page = ConfigurationManager.getProperty("path.page.error");
		}
		request.getSession().setAttribute(AMOUNT, amount);
		request.getSession().setAttribute(URL, page);
		return page;
	}
}
