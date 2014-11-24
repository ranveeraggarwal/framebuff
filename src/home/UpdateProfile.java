package home;

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
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import common.CommonSQL;

/**
 * Servlet implementation class UpdateProfile
 */
@WebServlet("/UpdateProfile")
public class UpdateProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateProfile() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		Map<String, Object> args = new HashMap<String, Object>();
		Users user = CommonSQL.getUserByUserId((Integer) request.getSession().getAttribute("userId"));
		args.put("user", user);
		out.println(Rythm.render("WebContent/templates/signup/page2.html", args));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username = request.getParameter("username");
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String country = request.getParameter("country");
		String language = request.getParameter("language");
		String phone = request.getParameter("phoneno");
		String aboutme = request.getParameter("aboutme");
		
		Integer userId = (Integer) request.getSession().getAttribute("userId");
		DBI dbi = CommonSQL.getDbi();
		try(Handle h = dbi.open()){
			h.update("update users set username=?, firstname=?, lastname=?, countrycode=?, language=?, phone=?, aboutme=? where userId = ?", 
					username, firstname, lastname, country, language, phone, aboutme, userId);
			response.sendRedirect("/user/"+userId.toString());
		} catch(Exception e){
			System.out.println(e.getMessage());
			response.sendRedirect("/UpdateProfile");
			return;
		}
	}

}
