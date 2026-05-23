package com.srms.listener;

import com.srms.dao.DBConnection;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebListener
public class AppStartupListener implements ServletContextListener {
    private static final Logger LOGGER = Logger.getLogger(AppStartupListener.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        try {
            DBConnection.getInstance().testConnection();
            LOGGER.log(Level.INFO, "[STARTUP] Database connection OK");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "[STARTUP] DATABASE CONNECTION FAILED: " + e.getMessage(), e);
            servletContext.setAttribute("dbError", "Database connection failed: " + e.getMessage());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
