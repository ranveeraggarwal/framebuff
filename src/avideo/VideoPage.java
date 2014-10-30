package avideo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Video;

import org.rythmengine.Rythm;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

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
		dbi = (DBI)request.getServletContext().getAttribute("dbi");

		Video vidObject = getVideoDetails(videoId);

		PrintWriter out = response.getWriter();
		args.put("videoDetails", vidObject);
		out.println(Rythm.render("WebContent/templates/video/index.html", args));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
	}

	private Video getVideoDetails(Integer videoId) 
	{

		String sql = "SELECT "
				+ "* FROM video "
				+ "WHERE videoid = :videoId";
		try (Handle h = dbi.open()) 
		{
			Video videoObject = h.createQuery(sql)
					.bind("videoId", videoId)
					.map(new Mapper<Video>(Video.class)).first();
			return videoObject;
		}

	}
}
