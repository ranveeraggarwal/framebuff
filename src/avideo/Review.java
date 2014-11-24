package avideo;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import common.CommonSQL;

/**
 * Servlet implementation class Review
 */
@WebServlet("/Review")
public class Review extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Review() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String review = request.getParameter("review");
		String videoid = request.getParameter("videoid");
		
		Integer userId = (Integer) request.getSession().getAttribute("userId");
		DBI dbi = CommonSQL.getDbi();
		try(Handle h = dbi.open()){
			h.update("update uservideo set review=? where userid=? and videoid=?", 
					review, userId, Integer.parseInt(videoid));
			response.sendRedirect("/video/"+videoid.toString());
		} catch(Exception e){
			System.out.println(e.getMessage());
			response.sendRedirect("/video/"+videoid.toString());
			return;
		}
	}

}
