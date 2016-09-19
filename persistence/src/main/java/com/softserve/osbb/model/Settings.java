package com.softserve.osbb.model;

import javax.persistence.*;

/**
 * Created by Kris on 15.09.2016.
 */
@Entity
@Table(name = "settings")
public class Settings {
    private Integer settingsId;
    private User user;
    private Boolean assigned;
    private Boolean creator;
    private Boolean comment;
    private Boolean answer;

    public Settings() {
    }

    public Settings(User user) {
        this.user = user;
        this.assigned = true;
        this.creator = true;
        this.comment = true;
        this.answer = true;
    }

    public Settings(User user, Boolean choose) {
        this.user = user;
        this.assigned = choose;
        this.creator = choose;
        this.comment = choose;
        this.answer = choose;
    }

    public Settings(User user, Boolean assigned, Boolean creator, Boolean comment, Boolean answer) {
        this.user = user;
        this.assigned = assigned;
        this.creator = creator;
        this.comment = comment;
        this.answer = answer;
    }

    @Id
    @Column(name = "settings_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getSettingsId() {
        return settingsId;
    }

    public void setSettingsId(Integer settingsId) {
        this.settingsId = settingsId;
    }

    @Column(name = "assigned")
    public Boolean getAssigned() {
        return assigned;
    }

    public void setAssigned(Boolean assigned) {
        this.assigned = assigned;
    }

    @Column(name = "creator")
    public Boolean getCreator() {
        return creator;
    }

    public void setCreator(Boolean creator) {
        this.creator = creator;
    }

    @Column(name = "comment")
    public Boolean getComment() {
        return comment;
    }

    public void setComment(Boolean comment) {
        this.comment = comment;
    }

    @Column(name = "answer")
    public Boolean getAnswer() {
        return answer;
    }

    public void setAnswer(Boolean answer) {
        this.answer = answer;
    }

    @OneToOne(optional=true)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Settings{" +
                "settingsId=" + settingsId +
                ", userId=" + user.getUserId() +
                ", assigned=" + assigned +
                ", creator=" + creator +
                ", comment=" + comment +
                ", answer=" + answer +
                '}';
    }
}
