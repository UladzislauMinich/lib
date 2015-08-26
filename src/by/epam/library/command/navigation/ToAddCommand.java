package by.epam.library.command.navigation;

import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import by.epam.library.command.ActionCommand;
import by.epam.library.dao.BookDAO;
import by.epam.library.exception.DAOException;
import by.epam.library.resource.ConfigurationManager;
import by.epam.library.resource.MessageManager;

public class ToAddCommand implements ActionCommand {

	private static Logger log = Logger.getLogger(ToAddCommand.class);
	private final String MESSAGE = "message";
	private final String URL = "url";
	private final String GENRES = "genres";

	@Override
	public String execute(HttpServletRequest request) {
		String page;
		BookDAO bookDAO = BookDAO.getInstance();
		try {
			HashSet<String> genres = bookDAO.findAllGenre();
			request.getSession().setAttribute(GENRES, genres);
		} catch (DAOException e) {
			log.error(e);
			request.setAttribute(MESSAGE, MessageManager.DATABASE_ERROR);
			page = ConfigurationManager.getProperty("path.page.error");
		}
		page = ConfigurationManager.getProperty("path.page.admin.addbook");
		request.getSession().setAttribute(URL, page);
		return page;
	}

}
