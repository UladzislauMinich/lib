package by.epam.library.command;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import by.epam.library.dao.BookDAO;
import by.epam.library.entity.Book;
import by.epam.library.exception.DAOException;
import by.epam.library.resource.ConfigurationManager;
import by.epam.library.resource.MessageManager;

public class ShowBookInformationCommand implements ActionCommand {
	private static Logger log = Logger.getLogger(ShowBookInformationCommand.class);
	private  final String MESSAGE = "message";
	private  final String URL = "url";
	private  final String ID_BOOK = "idBook";
	private  final String NEW_BOOK = "newBook";
	@Override
	public String execute(HttpServletRequest request) {
		String page = null;
		String idBook = request.getParameter(ID_BOOK);
		BookDAO bookDAO = BookDAO.getInstance();
		try{
			Book book = bookDAO.findBooksBId(idBook);
			if(book!=null){
				page = ConfigurationManager.getProperty("path.page.main");
				request.setAttribute(NEW_BOOK, book);
			}
		}catch (DAOException e) {
			log.error(e);
			request.setAttribute(MESSAGE,MessageManager.DATABASE_ERROR);
			page =  ConfigurationManager.getProperty("path.page.error");
		}
		request.getSession().setAttribute(URL, page);
		return page;
}
}