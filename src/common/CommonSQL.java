package common;

import models.Chat;
import models.Users;
import models.Video;

import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.util.IntegerMapper;

public class CommonSQL {
	
	private static DBI dbi;

	/**
	 * @return the dbi
	 */
	public static DBI getDbi() {
		return dbi;
	}

	/**
	 * @param dbi the dbi to set
	 */
	public static void setDbi(DBI dbi) {
		CommonSQL.dbi = dbi;
	}

	public static Users getUserByUserId(Integer userId) {
		
		try (Handle h = dbi.open()) {
			Users user = h
					.createQuery("select * from users where userId = :userId")
					.bind("userId", userId).map(new Mapper<Users>(Users.class))
					.first();
			return user;
		}
	}

	public static Integer updateChatToDb(Chat chat) {
		try(Handle h = dbi.open()){
			Integer insertion = h
					.createQuery("INSERT INTO chat (videoId, userId, message, parentId, chatDate)"
							+ " values (:videoId, :userId, :message, :parentId, :chatDate) RETURNING chatId")
							.bindFromProperties(chat)
							.map(IntegerMapper.FIRST)
							.first();
			return insertion;
		}
	}
	
	public static Video getVideoByVideoId(Integer videoId) {
		try(Handle h = dbi.open()){
			Video video = h
					.createQuery("select * from video where videoId = :videoId")
					.bind("videoId", videoId)
					.map(new Mapper<Video>(Video.class))
					.first();
			return video;
		}
	}
	
	public static void updateVideoWithPerson(Video video, DBI dbi){
		
	}

}
