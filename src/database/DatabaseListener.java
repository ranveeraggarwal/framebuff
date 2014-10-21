package database;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.postgresql.ds.PGPoolingDataSource;
import org.rythmengine.Rythm;
import org.skife.jdbi.v2.DBI;

/**
 * Application Lifecycle Listener implementation class DatabaseListener
 *
 */
@WebListener
public class DatabaseListener implements ServletContextListener {

	private PGPoolingDataSource source;
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
         source = new PGPoolingDataSource();
         source.setServerName("localhost");
         source.setDatabaseName("framebuff_db");
         source.setUser("framebuff_user");
         source.setPassword("password");
         source.setMaxConnections(10);
         
         DBI dbi = new DBI(source);
         ServletContext context = arg0.getServletContext();
         context.setAttribute("dbi", dbi);
         Rythm.init();
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
    	source = null;
    }
	
}
