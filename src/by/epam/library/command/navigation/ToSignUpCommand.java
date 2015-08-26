package by.epam.library.command.navigation;

import javax.servlet.http.HttpServletRequest;

import by.epam.library.command.ActionCommand;
import by.epam.library.resource.ConfigurationManager;

public class ToSignUpCommand implements ActionCommand {
	private final String URL = "url";

	@Override
	public String execute(HttpServletRequest request) {
		String page;
		page = ConfigurationManager.getProperty("path.page.signup");
		request.getSession().setAttribute(URL, page);
		return page;
	}

}
