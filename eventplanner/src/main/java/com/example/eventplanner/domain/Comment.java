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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Comment {
	 @Id
	 @GeneratedValue(strategy=GenerationType.AUTO)
	 @Column(name = "id", nullable = false, updatable = false)
	 private Long id;
	 @Column(nullable = false)
	 private String content;
	 @Column(nullable = false)
	 private String datetime;
	 
	 @ManyToOne
	 @JoinColumn(name = "owner", nullable=false)
	 @JsonIgnoreProperties({"comments", "events"})
	 private User owner;

	 @ManyToOne
	 @JoinColumn(name = "event", nullable=false)
	 @JsonIgnoreProperties({"comments", "owner"})
	 private Event event;
	 
	public Comment() {}
	
	public Comment(String content, String datetime, User owner, Event event) {
		super();
		this.content = content;
		this.datetime = datetime;
		this.owner = owner;
		this.event = event;
	}
	
	public Comment(String content) {
		super();
		this.content = content;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", content=" + content + ", datetime=" + datetime + ", owner=" + owner + ", event="
				+ event + "]";
	}

	 
	 

}
