package models;

import java.util.Date;

public class UserVideo {

	private Video video;
	private Integer rating;
	private String review;
	private Date watchDate;
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
