package com.ssafy.member.model;

//-- user_id(PK), user_name(NN), user_password(NN), email_id, email_domain, join_date(NN-default CURRENT_TIMESTAMP), grade(default 'basic')
public class MemberDto {
	private String userId;
	private String userName;
	private String userPwd;
	private String emailId;
	private String emailDomain;
	private String joinDate;
	private String grade;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPassword) {
		this.userPwd = userPassword;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getEmailDomain() {
		return emailDomain;
	}
	public void setEmailDomain(String emailDomain) {
		this.emailDomain = emailDomain;
	}
	public String getJoinDate() {
		return joinDate;
	}
	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(userId);
		builder.append(", ");
		builder.append(userName);
		builder.append(", ");
		builder.append(userPwd);
		builder.append(", ");
		builder.append(emailId);
		builder.append(", ");
		builder.append(emailDomain);
		builder.append(", ");
		builder.append(joinDate);
		builder.append(", ");
		builder.append(grade);
		return builder.toString();
	}
	
}
