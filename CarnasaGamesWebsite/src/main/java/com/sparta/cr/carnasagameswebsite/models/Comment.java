package com.sparta.cr.carnasagameswebsite.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Comment {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "comment", nullable = false)
    private String comment;

    @Column(name = "date")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    public Comment() {
    }
    public Comment(String comment, Date date, User user) {
        this.comment = comment;
        this.date = date;
        this.user = user;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

