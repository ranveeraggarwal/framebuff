package database;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.postgresql.ds.PGPoolingDataSource;
import org.skife.jdbi.v2.DBI;

/**
 * Application Lifecycle Listener implementation class DatabaseListener
 *
 */
@WebListener
public class DatabaseListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public DatabaseListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  { 
         PGPoolingDataSource source = new PGPoolingDataSource();
         source.setServerName("localhost");
         source.setDatabaseName("framebuff_db");
         source.setUser("framebuff_user");
         source.setPassword("password");
         source.setMaxConnections(10);
         
         DBI dbi = new DBI(source);
         ServletContext context = arg0.getServletContext();
         context.setAttribute("dbi", dbi);
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    }
	
}
