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

import common.CommonSQL;

/**
 * Servlet implementation class VideoPage
 */
@WebServlet("/video/*")
public class VideoPage extends HttpServlet {
	private static final long serialVersionUID = 1L;  
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

		Video vidObject = CommonSQL.getVideoByVideoId(videoId);
		CommonSQL.updateVideoWithPerson(vidObject);

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
}
