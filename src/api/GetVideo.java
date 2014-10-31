package api;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.skife.jdbi.v2.DBI;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.CommonSQL;

/**
 * Servlet implementation class GetVideo
 */
@WebServlet("/getVideo")
public class GetVideo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DBI dbi;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetVideo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("applicatoin/json");
		String videoId = request.getParameter("videoId");
		if (videoId == null){
			return;
		}
		Integer videoIdint = null;
		try{
			videoIdint = Integer.parseInt(videoId);
		} catch (Exception e){
			return;
		}
		dbi=(DBI) request.getServletContext().getAttribute("dbi");
		out.println(new ObjectMapper().writeValueAsString(CommonSQL.getVideoByVideoId(videoIdint, dbi)));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
