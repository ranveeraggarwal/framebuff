package common;

import models.Users;

import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

public class CommonSQL {
	
	public static Users getUserByUserId(Integer userId, DBI dbi){
		Handle h = dbi.open();
		Users user = h.createQuery("select * from users where userId = :userId")
				.bind("userId", userId)
				.map(new Mapper<Users>(Users.class)).first();
		return user;
	}

}
