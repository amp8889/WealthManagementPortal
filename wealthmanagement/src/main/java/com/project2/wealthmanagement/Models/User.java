package com.project2.wealthmanagement.Models;

import org.springframework.data.mongodb.core.mapping.Document;

import com.project2.wealthmanagement.Enums.UserRole;

import org.springframework.data.annotation.Id;

@Document(collection = "user")
public class User {
    @Id
    private String googleId;
    private UserRole role;
    private String advisorId; // Only if role is advisor
    private String firstName;
    private String lastName;
    private String email;
    private String avatarUrl;

    public User() {
    }

    public User(String googleId, UserRole role, String advisorId, String firstName, String lastName, String email,
            String avatarUrl) {
        this.googleId = googleId;
        this.role = role;
        this.advisorId = advisorId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.avatarUrl = avatarUrl;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getAdvisorId() {
        return advisorId;
    }

    public void setAdvisorId(String advisorId) {
        this.advisorId = advisorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((googleId == null) ? 0 : googleId.hashCode());
        result = prime * result + ((role == null) ? 0 : role.hashCode());
        result = prime * result + ((advisorId == null) ? 0 : advisorId.hashCode());
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((avatarUrl == null) ? 0 : avatarUrl.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (googleId == null) {
            if (other.googleId != null)
                return false;
        } else if (!googleId.equals(other.googleId))
            return false;
        if (role != other.role)
            return false;
        if (advisorId == null) {
            if (other.advisorId != null)
                return false;
        } else if (!advisorId.equals(other.advisorId))
            return false;
        if (firstName == null) {
            if (other.firstName != null)
                return false;
        } else if (!firstName.equals(other.firstName))
            return false;
        if (lastName == null) {
            if (other.lastName != null)
                return false;
        } else if (!lastName.equals(other.lastName))
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (avatarUrl == null) {
            if (other.avatarUrl != null)
                return false;
        } else if (!avatarUrl.equals(other.avatarUrl))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "User [googleId=" + googleId + ", role=" + role + ", advisorId=" + advisorId + ", firstName=" + firstName
                + ", lastName=" + lastName + ", email=" + email + ", avatarUrl=" + avatarUrl + "]";
    }
    
}