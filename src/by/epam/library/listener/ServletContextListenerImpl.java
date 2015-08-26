package by.epam.library.listener;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.PropertyConfigurator;

import by.epam.library.pool.DBConnectionPool;


public class ServletContextListenerImpl implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext context = sce.getServletContext();
		String log4jConfigFile = context.getInitParameter("log4j-config-location");
		if (log4jConfigFile == null) {
			context.log("Parameter to initialize log4j does not exist");
		}
		String fullPath = context.getRealPath("") + File.separator + log4jConfigFile;
		PropertyConfigurator.configure(fullPath);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
			DBConnectionPool.getInstance().release();		
	}
}
