package com.example.oop2025_week09.listener;

import com.example.oop2025_week09.db.SchemaInitializer;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class DbInitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Initializing database for College Manager...");
        SchemaInitializer.initializeSchema();
        System.out.println("Database initialization complete!");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Clean up database resources if needed
        System.out.println("College Manager shutting down...");
    }
}

