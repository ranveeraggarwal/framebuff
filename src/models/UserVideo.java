package models;

import java.util.Date;

public class UserVideo {

	private Integer userId;
	private Video video;
	private Integer rating;
	private String review;
	private String watch;
	private Date watchDate;
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
	 * @return the video
	 */
	public Video getVideo() {
		return video;
	}
	/**
	 * @param video the video to set
	 */
	public void setVideo(Video video) {
		this.video = video;
	}
	/**
	 * @return the rating
	 */
	public Integer getRating() {
		return rating;
	}
	/**
	 * @param rating the rating to set
	 */
	public void setRating(Integer rating) {
		this.rating = rating;
	}
	/**
	 * @return the watch
	 */
	public String getWatch() {
		return watch;
	}
	/**
	 * @param watch the watch to set
	 */
	public void setWatch(String watch) {
		this.watch = watch;
	}
	/**
	 * @return the review
	 */
	public String getReview() {
		return review;
	}
	/**
	 * @param review the review to set
	 */
	public void setReview(String review) {
		this.review = review;
	}
	public Date getWatchDate() {
		return watchDate;
	}
	public void setWatchDate(Date date) {
		this.watchDate = date;
	}
	
}
