package com.example.eventplanner.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Event {
	 @Id
	 @GeneratedValue(strategy=GenerationType.AUTO)
	 private Long id;
	 private String title;
	 private String description;
	 private String datetime;
	 
	 @ManyToOne
	 @JoinColumn(name = "event")
	 @JsonManagedReference
	 private User owner;
	 
	 @ManyToMany
	 @JoinColumn(name = "event")
	 @JsonManagedReference
	 private List<User> members;
	 
	 public Event() {}
	 
	public Event(String title, String description, String datetime, User owner) {
		super();
		this.title = title;
		this.description = description;
		this.datetime = datetime;
		this.owner = owner;
	}
	
	public Event(String title, String description, String datetime, User owner, List<User> members) {
		super();
		this.title = title;
		this.description = description;
		this.datetime = datetime;
		this.owner = owner;
		this.members = members;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
	public List<User> members() {
		return members;
	}
	public void setMembers(List<User> members) {
		this.members = members;
	}
	 
	
	@Override
	public String toString() {
		return "Event [id=" + id + ", title=" + title + ", description=" + description + ", datetime=" + datetime + ", owner=" + owner + "]";
	}
}
