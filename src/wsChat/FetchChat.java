package wsChat;

import java.io.IOException;
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

import common.Mapper;

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
		Integer videoId = Integer.parseInt(request.getParameter("videoId"));
		Integer offset = Integer.parseInt(request.getParameter("offset"));
		dbi = (DBI)request.getServletContext().getAttribute("dbi");
		try(Handle h = dbi.open())
		{
			Integer dbSize = h.createQuery("SELECT MAX(chatid) FROM chat WHERE videoid =:videoId")
					.bind("videoId", videoId)
					.map(IntegerMapper.FIRST)
					.first();
			List <Chat> messages = h.createQuery("select * from chat where videoid =:videoId and chatid between ':lengthMax' and 'lengthMin'")
					.bind("videoId", videoId)
					.bind("lengthMax", dbSize)
					.bind("lengthMin", dbSize-offset)
					.map(new Mapper<Chat>(Chat.class))
					.list();
		}
	}

}
