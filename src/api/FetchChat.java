package api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Chat;

import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.util.IntegerMapper;

import common.CommonSQL;
import common.Mapper;
import common.Util;

/**
 * Servlet implementation class FetchChat
 */
@WebServlet("/FetchChat")
public class FetchChat extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DBI dbi;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FetchChat() {
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
		String videoIdStr = request.getParameter("videoId");
		String offsetStr = request.getParameter("offset");
		Integer videoId = null;
		Integer offset = null;
		try{
			videoId = Integer.parseInt(videoIdStr);
			offset = Integer.parseInt(offsetStr);
		} catch (Exception e){
			return;
		}
		dbi = CommonSQL.getDbi();
		try(Handle h = dbi.open())
		{
			Integer dbSize = h.createQuery("SELECT MAX(chatid) FROM chat WHERE videoid =:videoId")
					.bind("videoId", videoId)
					.map(IntegerMapper.FIRST)
					.first();
			Integer fetched = dbSize - offset;
			List <Chat> messages = h.createQuery("select * from chat where videoid =:videoId and chatid between :lengthMin and :lengthMax")
					.bind("videoId", videoId)
					.bind("lengthMax", fetched)
					.bind("lengthMin", fetched - 10)
					.map(new Mapper<Chat>(Chat.class))
					.list();
			PrintWriter out = response.getWriter();
			String JSONmessage = Util.MAPPER.writeValueAsString(messages);
			out.println(JSONmessage);
		}
	}

}
