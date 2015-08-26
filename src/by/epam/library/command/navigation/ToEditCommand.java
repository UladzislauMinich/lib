package by.epam.library.command.navigation;

import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import by.epam.library.command.ActionCommand;
import by.epam.library.dao.BookDAO;
import by.epam.library.entity.Book;
import by.epam.library.exception.DAOException;
import by.epam.library.resource.ConfigurationManager;
import by.epam.library.resource.MessageManager;

public class ToEditCommand implements ActionCommand {

	private static Logger log = Logger.getLogger(ToEditCommand.class);
	private final String URL = "url";
	private final String GENRES = "genres";	
	private final String MESSAGE = "message";
	private final String BOOK = "book";
	private final String ID_BOOK = "idBook";

	@Override
	public String execute(HttpServletRequest request) {
		String page = null;
		String idBook = request.getParameter(ID_BOOK);
		BookDAO bookDAO = BookDAO.getInstance();
		try {
			Book book = bookDAO.findBooksBId(idBook);
			if (book != null) {
				request.getSession().setAttribute(BOOK, book);
				page = ConfigurationManager.getProperty("path.page.admin.edit");
			}
			HashSet<String> genres = bookDAO.findAllGenre();
			request.setAttribute(GENRES, genres);
		} catch (DAOException e) {
			log.error(e);
			request.setAttribute(MESSAGE, MessageManager.DATABASE_ERROR);
			page = ConfigurationManager.getProperty("path.page.error");
		}
		request.getSession().setAttribute(URL, page);
		return page;
	}
}
