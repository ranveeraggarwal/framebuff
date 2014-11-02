package api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Video;

import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import com.fasterxml.jackson.databind.ObjectMapper;

import common.CommonSQL;
import common.Mapper;

/**
 * Servlet implementation class MovieSearch
 */
@WebServlet("/MovieSearch")
public class MovieSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DBI dbi;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MovieSearch() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init(){
    	dbi = CommonSQL.getDbi();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String searchQuery = request.getParameter("q");
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		try(Handle h = dbi.open()){
			List<Video> videos = h.
					createQuery("select videoId, title from video where lower(title) like lower('%' || :title || '%')")
					.bind("title", searchQuery)
					.map(new Mapper<Video>(Video.class))
					.list();
			ObjectMapper mapper = new ObjectMapper();
			out.println(mapper.writeValueAsString(videos));
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
