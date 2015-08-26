package by.epam.library.command;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import by.epam.library.dao.UserDAO;
import by.epam.library.entity.User;
import by.epam.library.exception.DAOException;
import by.epam.library.resource.ConfigurationManager;
import by.epam.library.resource.MessageManager;
import by.epam.library.utils.Coder;
import by.epam.library.utils.Validator;

public class SignUpCommand implements ActionCommand {

	private static Logger log = Logger.getLogger(SignUpCommand.class);

	private final String NAME = "name";
	private final String SURNAME = "surname";
	private final String LOGIN = "login";
	private final String PASSWORD = "password";
	private final String AMOUNT = "amount";
	private final String MESSAGE = "message";
	private final String URL = "url";

	@Override
	public String execute(HttpServletRequest request) {
		String page = null;
		String amount = request.getParameter(AMOUNT);
		try {
			UserDAO userDAO;
			userDAO = UserDAO.getInstance();
			String login;
			String password;
			String name;
			String surname;
			login = request.getParameter(LOGIN).trim();
			password = request.getParameter(PASSWORD).trim();
			name = request.getParameter(NAME).trim();
			surname = request.getParameter(SURNAME).trim();
			
			if (login.length() * password.length() == 0) {
				request.setAttribute(MESSAGE, MessageManager.SIGNUP_ERROR);
				return ConfigurationManager.getProperty("path.page.signup");
			}
			User user = new User(login, password, name, surname, 0);
			user.setIdUser(Math.abs(user.hashCode()));
			String flag = Validator.validateUser(user);
			if (flag == null) {
				user.setPassword(Coder.hashMD5(password));
				boolean result = userDAO.signUp(user);
				if (!result) {
					request.setAttribute(MESSAGE,
							MessageManager.SIGNUP_ERROR_EXIST);
					page = ConfigurationManager.getProperty("path.page.signup");
				} else {
					request.getSession().setAttribute("user", user);
					request.setAttribute(MESSAGE,
							MessageManager.SIGNUP_SUCCESS);
					page = ConfigurationManager.getProperty("path.page.main");
				}
			} else {
				request.setAttribute(MESSAGE, flag);
				page = ConfigurationManager.getProperty("path.page.signup");
			}
		} catch (DAOException e) {
			log.error("Error. Can't sign up user", e);
			request.setAttribute(MESSAGE, MessageManager.DATABASE_ERROR);
			return ConfigurationManager.getProperty("path.page.error");
		}
		request.getSession().setAttribute(AMOUNT, amount);
		request.getSession().setAttribute(URL, page);
		return page;
	}
}
