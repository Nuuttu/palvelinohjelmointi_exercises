package com.example.eventplanner.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
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
	 @Column(name = "id", nullable = false, updatable = false)
	 private Long id;
	 @Column(nullable = false)
	 private String title;
	 private String description;
	 @Column(nullable = false)
	 private String datetime;
	 
	 @ManyToOne
	 @JoinColumn(name = "event", nullable = false)
	 @JsonManagedReference
	 private User owner;
	 
	 @ManyToMany
	 @JoinColumn(name = "event")
	 @JsonManagedReference
	 private List<User> members;
	 
	 @OneToMany
	 @JoinColumn(name="event")
	 @JsonManagedReference
	 private List<Comment> comments;
	 
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
	public List<User> getMembers() {
		return members;
	}
	public void setMembers(List<User> members) {
		this.members = members;
	}
	public void addMember(User newMember) {
		List<User> newList = new ArrayList<>(this.members);
		newList.add(newMember);
		setMembers(newList);
	}
	public void removeMember(User removedMember) {
		List<User> newList = new ArrayList<>(this.members);
		newList.remove(removedMember);
		setMembers(newList);
	}
	/*
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	public void addComment(Comment newComment) {
		List<Comment> newList = new ArrayList<>(this.comments);
		newList.add(newComment);
		setComments(newList);
	}
	public void removeComment(Comment removedComment) {
		List<Comment> newList = new ArrayList<>(this.comments);
		newList.remove(removedComment);
		setComments(newList);
	}
	 */
	
	@Override
	public String toString() {
		return "Event [id=" + id + ", title=" + title + ", description=" + description + ", datetime=" + datetime + ", owner=" + owner + "]";
	}
}
