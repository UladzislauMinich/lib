package by.epam.library.command;

import javax.servlet.http.HttpServletRequest;

public interface ActionCommand {
	
	String execute(HttpServletRequest request);
}