package home;

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

import models.UserVideo;

import org.rythmengine.Rythm;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.util.IntegerMapper;

import com.fasterxml.jackson.databind.ObjectMapper;

import common.CommonSQL;
import common.Mapper;

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
	
	public void init(){
		dbi = CommonSQL.getDbi();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> args = new HashMap<String, Object>();
		HttpSession session = request.getSession();
		Integer userId = (Integer) session.getAttribute("userId");
		PrintWriter out = response.getWriter();
		if (userId == null) {
			args.put("who", "World");
			out.println(Rythm.render("WebContent/templates/home/index.html",
					args));
		} else {
			List<UserVideo> userVideoes = getUserVideos(userId);
			ObjectMapper mapper  = new ObjectMapper();
			System.out.println(mapper.writeValueAsString(userVideoes));
			args.put("who", userId.toString());
			args.put("userVideoes", userVideoes);
			out.println(Rythm.render(
					"WebContent/templates/logged_in_home/index.html", args));
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("emailInput");
		String password = request.getParameter("passwordInput");

		try (Handle h = dbi.open()) {
			Integer userId = h
					.createQuery(
							"SELECT userId FROM users NATURAL JOIN auth WHERE email=:email AND password=:password")
					.bind("email", email).bind("password", password)
					.map(IntegerMapper.FIRST).first();
			if (userId != null) {
				HttpSession session = request.getSession();
				session.setAttribute("userId", userId);
				doGet(request, response);
			}
		}
	}

	private List<UserVideo> getUserVideos(Integer userId) {

		String sql = "select "
				+ "rating, review, watchdate,video.videoId as \"video_videoId\", video.title as \"video_title\", video.poster as \"video_poster\", "
				+ "video.genre as \"video_genre\", video.synopsis as \"video_synopsis\", video.userrating as \"video_userRating\" "
				+ "from userVideo inner join video on userVideo.videoId = video.videoId "
				+ "where userVideo.userId = :userId "
				+ "order by watchDate desc";
		try (Handle h = dbi.open()) {
			List<UserVideo> userVideoes = h.createQuery(sql)
					.bind("userId", userId)
					.map(new Mapper<UserVideo>(UserVideo.class)).list();
			return userVideoes;
		}

	}

}
