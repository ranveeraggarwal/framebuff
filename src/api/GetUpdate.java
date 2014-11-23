package api;

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

import models.UserVideo;

import org.rythmengine.Rythm;

import common.CommonSQL;
import common.Mapper;
import common.Util;

/**
 * Servlet implementation class GetUpdate
 */
@WebServlet("/GetUpdate")
public class GetUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetUpdate() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		String offsetStr = request.getParameter("offset");
		String userIdStr = request.getParameter("userId");
		Integer offset = null, userId = null;
		try{
			offset = Integer.parseInt(offsetStr);
			userId = Integer.parseInt(userIdStr);
		} catch(Exception e){
			return;
		}
		
		List<UserVideo> userVideo = CommonSQL.getUpdateFromFriends(userId, offset);
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("update", userVideo);
		String rendered = Rythm.render("WebContent/templates/logged_in_home/update.html",args);
		Integer length = userVideo.size();
		Map<String, String> map = new HashMap<String, String>();
		map.put("offset", length.toString());
		map.put("content", rendered);
		String text = Util.MAPPER.writeValueAsString(map);
		out.println(text);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
