package com.pcwk.ehr.mymapper.domain;

public class UserTKDTO {
	private String userId;
	private String password;
	private String name;
	private String nickname;
	private String email;
	private String mobile;
	private String address;
	private String rule;
	private String profile;
	private String regDt;
	private String modDt;
	
	
	
	/**
	 * @param userId
	 * @param password
	 * @param name
	 * @param nickname
	 * @param email
	 * @param mobile
	 * @param address
	 * @param rule
	 * @param profile
	 * @param regDt
	 * @param modDt
	 */
	public UserTKDTO(String userId, String password, String name, String nickname, String email, String mobile,
			String address, String rule, String profile, String regDt, String modDt) {
		super();
		this.userId = userId;
		this.password = password;
		this.name = name;
		this.nickname = nickname;
		this.email = email;
		this.mobile = mobile;
		this.address = address;
		this.rule = rule;
		this.profile = profile;
		this.regDt = regDt;
		this.modDt = modDt;
	}
	
	
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}
	/**
	 * @param nickname the nickname to set
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
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
	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the rule
	 */
	public String getRule() {
		return rule;
	}
	/**
	 * @param rule the rule to set
	 */
	public void setRule(String rule) {
		this.rule = rule;
	}
	/**
	 * @return the profile
	 */
	public String getProfile() {
		return profile;
	}
	/**
	 * @param profile the profile to set
	 */
	public void setProfile(String profile) {
		this.profile = profile;
	}
	/**
	 * @return the regDt
	 */
	public String getRegDt() {
		return regDt;
	}
	/**
	 * @param regDt the regDt to set
	 */
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	/**
	 * @return the modDt
	 */
	public String getModDt() {
		return modDt;
	}
	/**
	 * @param modDt the modDt to set
	 */
	public void setModDt(String modDt) {
		this.modDt = modDt;
	}
	@Override
	public String toString() {
		return "UserTKDTO [userId=" + userId + ", password=" + password + ", name=" + name + ", nickname=" + nickname
				+ ", email=" + email + ", mobile=" + mobile + ", address=" + address + ", rule=" + rule + ", profile="
				+ profile + ", regDt=" + regDt + ", modDt=" + modDt + "]";
	}
	
	
	
	
}
