package avideo;

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

import models.Chat;
import models.UserVideo;
import models.Video;

import org.rythmengine.Rythm;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.util.IntegerMapper;
import org.skife.jdbi.v2.util.StringMapper;

import common.Mapper;

/**
 * Servlet implementation class VideoPage
 */
@WebServlet("/video/*")
public class VideoPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DBI dbi;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VideoPage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map <String, Object> args = new HashMap <String, Object> ();
		String requestURI = request.getRequestURI();
		Integer videoId = Integer.parseInt(requestURI.split("/")[2]);
		String title;
		dbi = (DBI)request.getServletContext().getAttribute("dbi");
		try(Handle h = dbi.open())
		{
			title = h.createQuery("SELECT title FROM video WHERE videoid =:videoId")
					.bind("videoId", videoId)
					.map(StringMapper.FIRST)
					.first();
		}
		HttpSession session = request.getSession();
		Integer userId = (Integer) session.getAttribute("userId");
		PrintWriter out = response.getWriter();
		if (userId == null){
			args.put("who", "World");
			out.println(Rythm.render("WebContent/templates/home/index.html", args));
		}
		else {
			args.put("videoTitle", title);
			out.println(Rythm.render("WebContent/templates/video/index.html", args));
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	private List<Video> getVideoDetails(Integer videoId) {

		String sql = "SELECT "
				+ "* FROM video"
				+ "WHERE videoid = :videoId";
		try (Handle h = dbi.open()) {
			List<Video> videoList = h.createQuery(sql)
					.bind("videoId", videoId)
					.map(new Mapper<Video>(Video.class)).list();
			return videoList;
		}

	}
}
