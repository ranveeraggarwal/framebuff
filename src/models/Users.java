package models;

import com.neovisionaries.i18n.CountryCode;

public class Users {

	private Integer userId;
	private String firstName;
	private String lastName;
	private String username;
	private String email;
	private CountryCode countryCode;
	private String language;
	private String phone;
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * @return the id
	 */
	public Integer getId() {
		return userId;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.userId = id;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return username;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.username = userName;
	}
	
	/**
	 * @return the cc
	 */
	public CountryCode getCc() {
		return countryCode;
	}
	/**
	 * @param cc the cc to set
	 */
	public void setCc(CountryCode cc) {
		this.countryCode = cc;
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
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
}
