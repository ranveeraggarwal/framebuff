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
import javax.servlet.http.HttpSession;

import org.rythmengine.Rythm;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.util.IntegerMapper;

/**
 * Servlet implementation class Index
 */
@WebServlet("")
public class Index extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DBI dbi;

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Index() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map <String, Object> args = new HashMap <String, Object> ();
		HttpSession session = request.getSession();
		Integer userId = (Integer) session.getAttribute("userId");
		System.out.println("userID: "+userId.toString());
		if (userId == null){
			args.put("who", "World");
		}
		else {
			args.put("who", userId.toString());
		}
		PrintWriter out = response.getWriter();
		out.println(Rythm.render("WebContent/templates/home/index.html", args));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("emailInput");
		String password = request.getParameter("passwordInput");
		
		dbi = (DBI)request.getServletContext().getAttribute("dbi");
		
		try(Handle h = dbi.open()){
			Integer userId = h.createQuery("SELECT id FROM users NATURAL JOIN auth WHERE email=:email AND password=:password")
					.bind("email", email)
					.bind("password", password)
					.map(IntegerMapper.FIRST)
					.first();
			if (userId != null){
				HttpSession session = request.getSession();
				session.setAttribute("userId", userId);
				doGet(request, response);
			}
		}
	}

}
