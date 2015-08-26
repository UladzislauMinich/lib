package by.epam.library.command.factory;

import javax.servlet.http.HttpServletRequest;

import by.epam.library.command.ActionCommand;
import by.epam.library.command.EmptyCommand;
import by.epam.library.command.enums.CommandEnum;
import by.epam.library.resource.MessageManager;


public class ActionFactory {
	
  public ActionCommand defineCommand(HttpServletRequest request) {
    ActionCommand current = new EmptyCommand();
    String action = request.getParameter("command");
    if (action == null || action.isEmpty()) {
      return current;
    }
    try {
    	CommandEnum currentEnum = CommandEnum.valueOf(action.toUpperCase());
      current = currentEnum.getCurrentCommand();
    } catch (IllegalArgumentException e) {
      request.setAttribute("wrongAction", action + MessageManager.WRONG_ACTIOM);
    }
    return current;
  }
}
