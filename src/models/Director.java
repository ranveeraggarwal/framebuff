package models;

public class Director {
	
	private Integer directorId;
	private Person person;
	private Integer videoId;
	/**
	 * @return the directorId
	 */
	public Integer getDirectorId() {
		return directorId;
	}
	/**
	 * @param directorId the directorId to set
	 */
	public void setDirectorId(Integer directorId) {
		this.directorId = directorId;
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

}
