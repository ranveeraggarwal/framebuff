package models;

import java.util.Date;


public class Chat {

	private Integer chatId;
	private Integer videoId;
	private String message;
	private Integer userId;
	private Date chatDate;
	private Integer parentId;
	
	public Chat(){}
	
	public Chat(Chat c){
		this.chatId = c.chatId;
		this.videoId = c.videoId;
		this.message = c.message;
		this.userId = c.userId;
		this.chatDate = c.chatDate;
		this.parentId = c.parentId;
	}

	/**
	 * @return the chatId
	 */
	public Integer getChatId() {
		return chatId;
	}

	/**
	 * @param chatId the chatId to set
	 */
	public void setChatId(Integer chatId) {
		this.chatId = chatId;
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
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * @return the chatDate
	 */
	public Date getChatDate() {
		return chatDate;
	}

	/**
	 * @param chatDate the chatDate to set
	 */
	public void setChatDate(Date chatDate) {
		this.chatDate = chatDate;
	}


	/**
	 * @return the parentId
	 */
	public Integer getParentId() {
		return parentId;
	}

	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	
}
