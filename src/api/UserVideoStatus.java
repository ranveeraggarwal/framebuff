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
			Integer updated = null;
			if ("watching".equals(status)){
				updated = h.update("update userVideo set watch = 'watching' where userId = ? and videoId = ?",  userId, videoId);
			}
			else if ("watched".equals(status)){
				updated = h.update("update userVideo set watch = 'watched' where userId = ? and videoId = ?",  userId, videoId);
			}
			else {
				updated = h.update("update userVideo set watch = 'want' where userId = ? and videoId = ?",  userId, videoId);
			}
			if (updated == 0){
				h.execute("insert into userVideo (userId, videoId, watch) values (?, ?, CAST(? AS watchtype))", userId, videoId, status);
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
