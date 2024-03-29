package api;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Users;

import common.CommonSQL;
import common.Util;

/**
 * Servlet implementation class GetUsername
 */
@WebServlet("/getUser")
public class GetUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
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
		Users user = CommonSQL.getUserByUserId(userIdint);
		out.println(Util.MAPPER.writeValueAsString(user));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
