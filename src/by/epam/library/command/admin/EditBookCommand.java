package by.epam.library.command.admin;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import by.epam.library.command.ActionCommand;
import by.epam.library.dao.BookDAO;
import by.epam.library.entity.Book;
import by.epam.library.exception.DAOException;
import by.epam.library.resource.ConfigurationManager;
import by.epam.library.resource.MessageManager;
import by.epam.library.utils.Validator;

public class EditBookCommand implements ActionCommand {
	private static Logger log = Logger.getLogger(EditBookCommand.class);
	private final String URL = "url";
	private final String NAME = "name";
	private final String GENRE = "genre";
	private final String AUTHOR_NAME = "authorName";
	private final String AUTHOR_SURNAME = "authorSurname";
	private final String IMPRIT_YEAR = "impritYear";
	private final String AMOUNT = "amount";
	private final String ONLY_READING_HALL = "onlyReadingHall";
	private final String DESCRIPTION = "description";
	private final String PICTURE_PATH = "picturePath";
	private final String MESSAGE = "message";
	private final String BOOK = "newBook";
	private final String BOOK_EDIT = "book";
	private final String ID_BOOK = "idBook";

	@Override
	public String execute(HttpServletRequest request) {
		String page = null;
		String bookName = request.getParameter(NAME).trim();
		String genre = request.getParameter(GENRE);
		String authorName = request.getParameter(AUTHOR_NAME).trim();
		String authorSurname = request.getParameter(AUTHOR_SURNAME).trim();
		String impritYear = request.getParameter(IMPRIT_YEAR).trim();
		String amount = request.getParameter(AMOUNT).trim();
		String description = request.getParameter(DESCRIPTION).trim();
		String picturePath = request.getParameter(PICTURE_PATH).trim();
		String onlyReadingHall = request.getParameter(ONLY_READING_HALL);
		if (bookName.length() * picturePath.length() * authorName.length()
				* authorSurname.length() * impritYear.length()
				* description.length() == 0
				|| genre == null || onlyReadingHall == null) {
			request.setAttribute(MESSAGE, MessageManager.ADD_BOOK_ERROR);
			page = ConfigurationManager.getProperty("path.page.admin.edit");
			request.getSession().setAttribute(URL, page);
			return page;
		}
		Book book = new Book();
		book.setName(bookName);
		book.setGenre(genre);
		book.setAuthorName(authorName);
		book.setAuthorSurname(authorSurname);
		book.setImpritYear(impritYear);
		book.setDescription(description);
		book.setPicturePath(picturePath);
		book.setOnlyReadingHall(onlyReadingHall);
		String flag = Validator.validateBook(book, amount);
		book.setIdBook(Integer.parseInt(request.getParameter(ID_BOOK)));
		BookDAO bookDAO = BookDAO.getInstance();
		try {
			if (flag == null) {
				if (bookDAO.updateBookInformation(book)) {
					request.setAttribute(MESSAGE,
							MessageManager.UPDATE_BOOK_SUCCESS);
					page = ConfigurationManager.getProperty("path.page.main");
					request.setAttribute(BOOK, book);
				} else {
					request.setAttribute(MESSAGE,
							MessageManager.ERROR_UPDATE_BOOK_INFO);
					page = ConfigurationManager.getProperty("path.page.error");
				}
			} else {
				request.setAttribute(MESSAGE, flag);
				request.getSession().setAttribute(BOOK_EDIT, book);
				page = ConfigurationManager.getProperty("path.page.admin.edit");
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