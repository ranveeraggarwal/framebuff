package common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Actor;
import models.Chat;
import models.Director;
import models.Producer;
import models.UserVideo;
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
	
	public static Video updateVideoWithPerson(Video video){
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
			h.begin();
			Mapper<Actor> mapper = new Mapper<Actor>(Actor.class);
			mapper.register(new Jdbc4ArrayConverter(), List.class);
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
			h.commit();
			return video;
		}
	}
	
	public static Map<String, Map<String, Long>> getUserTape(Integer userId){
		Map<String, Map<String, Long>> tape = new HashMap<String, Map<String, Long>>();
		Map<String, Long> tempdel1 = new HashMap<String, Long>();
		Map<String, Long> tempdel2 = new HashMap<String, Long>();
		Map<String, Long> tempdel3 = new HashMap<String, Long>();
		tempdel1.put("watched", 0L);	tempdel1.put("want", 0L);  tempdel1.put("watching", 0L);
		tempdel2.put("watched", 0L);	tempdel2.put("want", 0L);  tempdel2.put("watching", 0L);
		tempdel3.put("watched", 0L);	tempdel3.put("want", 0L);  tempdel3.put("watching", 0L);
		
		tape.put("1", tempdel1);
		tape.put("2", tempdel2);
		tape.put("3", tempdel3);
		
		String sql = "select video.type, watch, count(watch) as tapecount\n" + 
				"from uservideo \n" + 
				"inner join video on video.videoID = uservideo.videoID\n" + 
				"where userVideo.userId = ? " +
				"group by watch, video.type";
		try(Handle h = dbi.open()){
			
			List<Map<String, Object>> rs = h.select(sql, userId);
			for (Map<String, Object> e: rs){
				String type = ((Integer)e.get("type")).toString();
				Map<String, Long> temp = tape.get(type);
				if (temp == null){
					temp = new HashMap<String, Long>();
				}
				
				temp.put((String)e.get("watch"), (Long)e.get("tapecount"));
				tape.put(type, temp);
			}
			
			return tape;
		}
		
	}
	
	public static List<UserVideo> getUpdateFromFriends(Integer userId, Integer offset){
		String sql = "select userId,  watch, rating, review, watchdate, \n" + 
				"video.videoId as \"video_videoId\", video.title as \"video_title\", video.poster as \"video_poster\"\n" + 
				"from userVideo\n" + 
				"inner join follow on follow.followee = userVideo.userId\n" + 
				"inner join video on video.videoId = userVideo.videoId\n" + 
				"where follow.follower = :userId\n"
				+ " ORDER BY watchdate ASC"
				+ " LIMIT 10 OFFSET :offset";
		
		try(Handle h = dbi.open()){
			List<UserVideo> userVideos = h.createQuery(sql).bind("userId", userId).bind("offset", offset)
					.map(new Mapper<UserVideo>(UserVideo.class))
					.list();
			return userVideos;
		}
	}
	
	public static UserVideo getUserVideoByVideoId(Integer videoId, Integer userId){
		String sql = "select userId, videoId as video_videoId, rating, review, watchDate, watch " +
				 "from uservideo where videoId = ? AND userId = ?";
		try(Handle h = dbi.open()){
			UserVideo userVideo = h.createQuery(sql).bind(0, videoId).bind(1, userId)
					.map(new Mapper<UserVideo>(UserVideo.class))
					.first();
			return userVideo;
		}
	}

}
