package com.epam.brest.courses.domain;

public class User
{
	private long userId;
	private String login;
	private String userName;

	public long getUserId()
	{
		return userId;
	}
	public String getLogin()
	{
		return login;
	}
	public String getUserName()
	{
		return userName;
	}
	public void setUserId(long userId)
	{
		this.userId = userId;
	}
	public void setLogin(String login)
	{
		this.login = login;
	}
	public void setUserId(String userName)
	{
		this.userName = userName;
	}
}
