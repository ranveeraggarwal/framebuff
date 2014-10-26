package wsChat;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Video;

import org.rythmengine.Rythm;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import common.Mapper;


/**
 * Servlet implementation class Chat
 */
@WebServlet("/Chat")
public class Discuss extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Discuss() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Integer userId = (Integer) session.getAttribute("userId");
		if (userId != null){
			DBI dbi = (DBI) request.getServletContext().getAttribute("dbi");
			List<Video> list = null;
			try (Handle h = dbi.open()){
				list = h.createQuery("SELECT videoId, title FROM video WHERE 1=1")
						.map(new Mapper<Video>(Video.class)).list();
			}
			Map<String, List<Video>> map = new HashMap<String, List<Video>>();
			map.put("videos", list);
			PrintWriter out = response.getWriter();
			out.println(Rythm.render("WebContent/templates/chat/index.html", map));
		}
		else response.sendRedirect("");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
