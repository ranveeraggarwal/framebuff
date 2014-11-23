package auser;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Users;

import org.rythmengine.Rythm;

import common.CommonSQL;

/**
 * Servlet implementation class UserPage
 */
@WebServlet("/user/*")
public class UserPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserPage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map <String, Object> args = new HashMap <String, Object> ();
		String requestURI = request.getRequestURI();
		Integer userId = Integer.parseInt(requestURI.split("/")[2]);

		Users userObject = CommonSQL.getUserByUserId(userId);

		PrintWriter out = response.getWriter();
		args.put("userDetails", userObject);
		out.println(Rythm.render("WebContent/templates/users/index.html", args));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
