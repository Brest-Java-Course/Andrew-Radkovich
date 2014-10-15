package com.epam.brest.courses.domain;

public class User
{
	private Long userId;
	private String login;
	private String userName;
	
	public void setUserId(Long userId)
	{
		this.userId = userId;
	}

	public Long getUserId()
	{
		return userId;
	}

	public void setLogin(String login)
	{
		this.login = login;
	}
	
	public String getLogin()
	{
		return login;
	}
}
