package models;

import java.util.List;

public class Actor {
	
	private Integer actorId;
	private Integer videoId;
	private Person person;
	private List<String> roleName;
	/**
	 * @return the actorId
	 */
	public Integer getActorId() {
		return actorId;
	}
	/**
	 * @param actorId the actorId to set
	 */
	public void setActorId(Integer actorId) {
		this.actorId = actorId;
	}
	/**
	 * @return the videoId
	 */
	public Integer getVideoId() {
		return videoId;
	}
	/**
	 * @param videoId the videoId to set
	 */
	public void setVideoId(Integer videoId) {
		this.videoId = videoId;
	}
	/**
	 * @return the person
	 */
	public Person getPerson() {
		return person;
	}
	/**
	 * @param person the person to set
	 */
	public void setPerson(Person person) {
		this.person = person;
	}
	/**
	 * @return the roleName
	 */
	public List<String> getRoleName() {
		return roleName;
	}
	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(List<String> roleName) {
		this.roleName = roleName;
	}
	
	

}
