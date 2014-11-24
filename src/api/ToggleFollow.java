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

import common.CommonSQL;

/**
 * Servlet implementation class ToggleFollow
 */
@WebServlet("/ToggleFollow")
public class ToggleFollow extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ToggleFollow() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Integer followee = null, follower = null;
		try{
			followee = Integer.parseInt(request.getParameter("toFollow"));
			follower = (Integer) request.getSession().getAttribute("userId");
			if (followee == follower){
				throw new Exception();
			}
		} catch(Exception e){
			return;
		}
		
		DBI dbi = CommonSQL.getDbi();
		try(Handle h = dbi.open()){
			List<Map<String, Object>> rs = h.select("select userId from users where userId = ?", followee);
			List<Map<String, Object>> rs1 = h.select("select userId from users where userId = ?", follower);
			if (rs.size() == 0 || rs1.size() == 0){
				return;
			}
			
			rs = h.select("select followid from follow where follower = ? and followee = ?", follower, followee);
			if (rs.size() == 0){
				Integer x = h.insert("insert into follow (follower, followee) values (?, ?)", follower, followee);
				if (x == 1){
					response.getWriter().println("DONE");
					return;
				}
				return;
			}
			h.execute("delete from follow where followid = ?", (Integer) rs.get(0).get("followid"));
			response.getWriter().println("DONE");
			return;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
