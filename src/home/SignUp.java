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

import org.rythmengine.Rythm;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.util.IntegerMapper;

import common.CommonSQL;

/**
 * Servlet implementation class SignUp
 */
@WebServlet("/signup")
public class SignUp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUp() {
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
		out.println(Rythm.render(
				"WebContent/templates/signup/index.html", args));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		DBI dbi = CommonSQL.getDbi();
		try(Handle h = dbi.open()){
			h.begin();
			Integer userId = h.createQuery("insert into users (email) values (:email) returning userId")
					.bind("email", email)
					.map(IntegerMapper.FIRST).first();
			h.execute("insert into auth (userId, password) values (?, md5(?))", userId, password);
			h.commit();
			request.getSession().setAttribute("userId", userId);
			response.sendRedirect("/UpdateProfile");
		} catch(Exception e){
			System.out.println(e.getMessage());
			response.sendRedirect("/SignUp");
		}
	}

}
