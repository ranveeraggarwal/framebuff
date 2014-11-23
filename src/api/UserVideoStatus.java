package api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import common.CommonSQL;

/**
 * Servlet implementation class UserVideoStatus
 */
@WebServlet("/UserVideoStatus")
public class UserVideoStatus extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserVideoStatus() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String status = request.getParameter("status");
		Integer videoId =  null;
		try{
			String tempvideoId = request.getParameter("videoId");
			videoId = Integer.parseInt(tempvideoId);
		} catch(Exception e){
			response.getWriter().println("INVALID");
			return;
		}
		List<String> validStatus = new ArrayList<String>();
		validStatus.add("watching");
		validStatus.add("want");
		validStatus.add("watched");
		if (! validStatus.contains(status)){
			response.getWriter().println("INVALID");
			return;
		}
		
		Integer userId = (Integer) request.getSession().getAttribute("userId");
		DBI dbi = CommonSQL.getDbi();
		
		try(Handle h = dbi.open()){
			h.begin();
			h.execute("delete from userVideo where userId = ? and videoId = ?", userId, videoId);
			if ("watched".equals(status)){
				h.execute("insert into userVideo (userId, videoId, watch) values (?, ?, 'watched')", userId, videoId);
				
			}
			else if ("watching".equals(status)){
				h.execute("insert into userVideo (userId, videoId, watch) values (? ,?, 'watching')", userId, videoId);
			}
			else {
				h.execute("insert into userVideo (userId, videoId, watch) values (?, ?, 'want')", userId, videoId);
			}
			h.commit();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
