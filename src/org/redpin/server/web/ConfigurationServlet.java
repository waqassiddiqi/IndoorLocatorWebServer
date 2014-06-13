package org.redpin.server.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.redpin.server.standalone.util.Configuration;

public class ConfigurationServlet implements ServletContextListener {
	
	public void contextInitialized(ServletContextEvent e) {
		
		new Configuration().init();
	}
	
	public void contextDestroyed(ServletContextEvent e) {
		System.out.println("End");
	}
}
