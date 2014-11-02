package listener.database;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.postgresql.ds.PGPoolingDataSource;
import org.rythmengine.Rythm;
import org.skife.jdbi.v2.DBI;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

import common.CommonSQL;
import common.Util;

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
         
         CommonSQL.setDbi(dbi);
         Util.MAPPER = new ObjectMapper();
         Util.MAPPER.setSerializationInclusion(Include.NON_NULL);
         
         Rythm.init();
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
    	source = null;
    	Rythm.shutdown();
    }
	
}
