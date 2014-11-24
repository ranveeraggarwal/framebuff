package api;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.util.LongMapper;

import common.CommonSQL;

/**
 * Servlet implementation class CheckUsername
 */
@WebServlet("/CheckUsername")
public class CheckUsername extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckUsername() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		DBI dbi = CommonSQL.getDbi();
		PrintWriter out = response.getWriter();
		try(Handle h = dbi.open()){
			if (username != null){
				Long count = h.createQuery("select count(username) from users where username = :username")
						.bind("username", username)
						.map(LongMapper.FIRST)
						.first();
				if (count == 0){
					out.println("TRUE");
					return;
				}
				else {
					out.println("FALSE");
					return;
							
				}
			}
			else if (email != null){
				Long count = h.createQuery("select count(email) from users where email = :email")
						.bind("email", email)
						.map(LongMapper.FIRST)
						.first();
				if (count == 0){
					out.println("TRUE");
					return;
				}
				else {
					out.println("FALSE");
					return;
							
				}
			}
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
