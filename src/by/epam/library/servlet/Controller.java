package by.epam.library.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.library.command.ActionCommand;
import by.epam.library.command.factory.ActionFactory;
import by.epam.library.resource.ConfigurationManager;
import by.epam.library.resource.MessageManager;



@SuppressWarnings("serial")
@WebServlet("/controller")
public class Controller extends HttpServlet {

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    processRequest(request, response);
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    processRequest(request, response);
  }

  private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String page = null;
    ActionFactory client = new ActionFactory();
    ActionCommand command = client.defineCommand(request);
    page = command.execute(request);
    if (page != null) {
      RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
      dispatcher.forward(request, response);
    } else {
      page = ConfigurationManager.getProperty("path.page.index");
      request.getSession().setAttribute("message", MessageManager.NULL_PAGE);
      response.sendRedirect(request.getContextPath() + page);
    }
  }
}