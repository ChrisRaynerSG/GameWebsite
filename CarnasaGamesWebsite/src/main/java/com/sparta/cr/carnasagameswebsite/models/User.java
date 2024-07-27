package com.sparta.cr.carnasagameswebsite.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Entity
@Table(name = "users", schema = "carnasa_website")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    @Size(max = 20, message = "Username must be less than 20 characters.")
    @Pattern(regexp = "[a-zA-Z0-9]+", message = "Username cannot contain spaces or special characters.")
    private String username;

    @Column(name = "password", nullable = false)
    // do this at input validation as password stored as hash in db @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Password must ")
    private String password;

    @Column(name = "email",nullable = false, unique = true)
    @Pattern(regexp = "^([a-zA-Z0-9_\\-.]+)@([a-zA-Z0-9_\\-.]+)\\.([a-zA-Z]{2,5})$", message = "Please enter a valid email address")
    private String email;

    @Column(name = "roles", nullable = false)
    private String roles;

    @Column(name = "is_private", nullable = false)
    private boolean privateaccount;

    @Column(name = "description")
    private String description;

    @Column(name = "followers")
    private int followers;

    @Column(name = "first_name")
    private String firstname;

    @Column(name = "last_name")
    private String lastname;

    @Column(name = "DOB")
    private LocalDate birthdate;

    @Column(name = "profile_image", columnDefinition = "TEXT")
    private String profileimage;

    public User(){

    }
    public User(String username, String email, String password, String roles){
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public boolean isPrivateaccount() {
        return privateaccount;
    }

    public void setPrivateaccount(boolean privateaccount) {
        this.privateaccount = privateaccount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
