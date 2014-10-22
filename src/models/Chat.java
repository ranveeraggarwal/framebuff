package models;

import java.util.Date;

public class Chat {

	private Integer chatId;
	private String text;
	private Date date;
	private String chatroom;
	private Integer parentId;
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
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}
	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	 * @return the chatroom
	 */
	public String getChatroom() {
		return chatroom;
	}
	/**
	 * @param chatroom the chatroom to set
	 */
	public void setChatroom(String chatroom) {
		this.chatroom = chatroom;
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
