package common;

import models.Chat;
import models.Users;

import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.util.IntegerMapper;

public class CommonSQL {

	public static Users getUserByUserId(Integer userId, DBI dbi) {
		try (Handle h = dbi.open()) {
			Users user = h
					.createQuery("select * from users where userId = :userId")
					.bind("userId", userId).map(new Mapper<Users>(Users.class))
					.first();
			return user;
		}
	}

	public static Integer updateChatToDb(Chat chat, DBI dbi) {
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

}
