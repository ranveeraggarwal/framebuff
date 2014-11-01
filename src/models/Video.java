package models;

import java.util.List;

public class Video {
	
	private Integer videoId;
	private String title;
	private Integer runtime;
	private Integer type;
	private String poster;
	private String[] genre;
	private String[] relatedLinks;
	private String language;
	private String countryCode;
	private String synopsis;
	private Double userRating;
	private Integer totalRate;
	private String certification;
	
	private List<Actor> actors;
	private List<Director> directors;
	private List<Producer> producers;
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
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the runtime
	 */
	public Integer getRuntime() {
		return runtime;
	}
	/**
	 * @param runtime the runtime to set
	 */
	public void setRuntime(Integer runtime) {
		this.runtime = runtime;
	}
	/**
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	
	/**
	 * @return the genre
	 */
	public String[] getGenre() {
		return genre;
	}
	/**
	 * @param genre the genre to set
	 */
	public void setGenre(String[] genre) {
		this.genre = genre;
	}
	
	/**
	 * @return the relatedLinks
	 */
	public String[] getRelatedLinks() {
		return relatedLinks;
	}
	/**
	 * @param relatedLinks the relatedLinks to set
	 */
	public void setRelatedLinks(String[] relatedLinks) {
		this.relatedLinks = relatedLinks;
	}
	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}
	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}
	/**
	 * @return the countryCode
	 */
	public String getCountryCode() {
		return countryCode;
	}
	/**
	 * @param countryCode the countryCode to set
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	/**
	 * @return the synopsis
	 */
	public String getSynopsis() {
		return synopsis;
	}
	/**
	 * @param synopsis the synopsis to set
	 */
	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}
	/**
	 * @return the userRating
	 */
	public Double getUserRating() {
		return userRating;
	}
	/**
	 * @param userRating the userRating to set
	 */
	public void setUserRating(Double userRating) {
		this.userRating = userRating;
	}
	/**
	 * @return the certification
	 */
	public String getCertification() {
		return certification;
	}
	/**
	 * @param certification the certification to set
	 */
	public void setCertification(String certification) {
		this.certification = certification;
	}
	public String getPoster() {
		return poster;
	}
	public void setPoster(String poster) {
		this.poster = poster;
	}
	/**
	 * @return the totalRate
	 */
	public Integer getTotalRate() {
		return totalRate;
	}
	/**
	 * @param totalRate the totalRate to set
	 */
	public void setTotalRate(Integer totalRate) {
		this.totalRate = totalRate;
	}
	/**
	 * @return the actors
	 */
	public List<Actor> getActors() {
		return actors;
	}
	/**
	 * @param actors the actors to set
	 */
	public void setActors(List<Actor> actors) {
		this.actors = actors;
	}
	/**
	 * @return the directors
	 */
	public List<Director> getDirectors() {
		return directors;
	}
	/**
	 * @param directors the directors to set
	 */
	public void setDirectors(List<Director> directors) {
		this.directors = directors;
	}
	/**
	 * @return the producers
	 */
	public List<Producer> getProducers() {
		return producers;
	}
	/**
	 * @param producers the producers to set
	 */
	public void setProducers(List<Producer> producers) {
		this.producers = producers;
	}
	
	

}
