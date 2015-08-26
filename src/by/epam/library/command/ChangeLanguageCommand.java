package by.epam.library.command;

import javax.servlet.http.HttpServletRequest;

/**
 * Uses when needed to change language.
 */
public class ChangeLanguageCommand implements ActionCommand {
	private static final String URL = "url";
	private static final String LOCALE = "locale";
	@Override
	public String execute(HttpServletRequest request) {
		String language = request.getParameter(LOCALE);
		request.getSession().setAttribute(LOCALE, language);
		String page = (String) request.getSession().getAttribute(URL);
		return page;
	}
}
