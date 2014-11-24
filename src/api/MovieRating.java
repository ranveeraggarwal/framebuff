package api;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.util.IntegerMapper;

import common.CommonSQL;

/**
 * Servlet implementation class MovieRating
 */
@WebServlet("/MovieRating")
public class MovieRating extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MovieRating() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Integer videoId = null, rating = null, userId = null;
		try{
			String temp = request.getParameter("videoId");
			videoId = Integer.parseInt(temp);
			temp = request.getParameter("rating");
			rating = Integer.parseInt(temp);
			userId = (Integer) request.getSession().getAttribute("userId");
		} catch(Exception e){
			System.out.println(e.getMessage());
			return;
		}
		if (rating > 10){
			rating = 10;
		}
		if (rating < 0){
			rating = 0;
		}
		DBI dbi = CommonSQL.getDbi();
		try(Handle h = dbi.open()){
			Integer updated = h.update("update userVideo set rating = ?, rated = 't' where userId = ? and videoId = ?", rating, userId, videoId);
			List<Map<String, Object>> rs = h.select("select userrating, totalrate from video where videoId = ?", videoId);
			if (rs.size() == 0){
				return;
			}
			Double userRating = (Double) rs.get(0).get("userrating");
			Integer totalRate = (Integer) rs.get(0).get("totalrate");
			response.getWriter().println(userRating.toString()+"~"+totalRate.toString());	
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
