package api;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Users;

import org.skife.jdbi.v2.DBI;

import common.CommonSQL;

/**
 * Servlet implementation class GetUsername
 */
@WebServlet("/GetUsername")
public class GetUsername extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DBI dbi;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetUsername() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String userId = request.getParameter("userId");
		Integer userIdint;
		if (userId == null){
			out.println("");
			return;
		}
		try{
			userIdint = Integer.parseInt(userId);
		} catch(Exception e){
			out.print("");
			return;
		}
		dbi = (DBI) request.getServletContext().getAttribute("dbi");
		Users user = CommonSQL.getUserByUserId(userIdint, dbi);
		out.println(user.getUsername());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
