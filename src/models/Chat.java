package models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Chat {

	private Integer chatId;
	private String text;
	private String senderNick;
	private Date date;
	private String chatroom;
	private Integer parentId;
	/**
	 * @return the chatId
	 */
	@JsonProperty("id")
	public Integer getChatId() {
		return chatId;
	}
	/**
	 * @return the sender
	 */
	@JsonProperty("sender")
	public String getSender() {
		return senderNick;
	}
	/**
	 * @param sender the sender to set
	 */
	public void setSender(String sender) {
		this.senderNick = sender;
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
	@JsonProperty("message")
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
	@JsonProperty("received")
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
	@JsonProperty("room")
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
	@JsonProperty("pid")
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