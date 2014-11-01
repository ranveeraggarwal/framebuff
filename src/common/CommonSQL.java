package common;

import java.util.ArrayList;
import java.util.List;

import models.Actor;
import models.Chat;
import models.Director;
import models.Producer;
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
	
	public static void updateVideoWithPerson(Video video){
		String sqlActors = "select actorId, videoId, roleName, "
				+ "personId as person_personId, personName as person_personName, personGender as person_personGender " 
				+ "from actor natural join person where videoId = :videoId";
		
		String sqlDirectors = "select directorId, videoId, "
				+ "personId as person_personId, personName as person_personName, personGender as person_personGender " 
				+ "from director natural join person where videoId = :videoId";
		
		String sqlProducers = "select producerId, videoId, "
				+ "personId as person_personId, personName as person_personName, personGender as person_personGender " 
				+ "from producer natural join person where videoId = :videoId";
		try(Handle h = dbi.open()){
			Mapper<Actor> mapper = new Mapper<Actor>(Actor.class);
			mapper.register(new jdbc4ArrayConverter(), List.class);
			List<Actor> actors = h
					.createQuery(sqlActors).bind("videoId", video.getVideoId())
					.map(mapper).list();
			
			List<Director> directors = h
					.createQuery(sqlDirectors).bind("videoId", video.getVideoId())
					.map(new Mapper<Director>(Director.class)).list();
			
			List<Producer> producers = h
					.createQuery(sqlProducers).bind("videoId", video.getVideoId())
					.map(new Mapper<Producer>(Producer.class)).list();
			
			video.setActors(actors);
			video.setDirectors(directors);
			video.setProducers(producers);
		}
	}

}
