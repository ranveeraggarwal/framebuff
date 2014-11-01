package models;

public class Producer {
	
	private Integer producerId;
	private Person person;
	private Integer videoId;
	/**
	 * @return the producerId
	 */
	public Integer getProducerId() {
		return producerId;
	}
	/**
	 * @param producerId the producerId to set
	 */
	public void setProducerId(Integer producerId) {
		this.producerId = producerId;
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
