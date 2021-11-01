package com.example.eventplanner.domain;

public class Member {
	private String addmember;

	public Member(String addmember) {
		super();
		this.addmember = addmember;
	}
	
	public Member() {}
	
	public String getAddmember() {
		return addmember;
	}

	public void setAddmember(String addmember) {
		this.addmember = addmember;
	}
}
