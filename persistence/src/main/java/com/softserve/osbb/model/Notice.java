package com.softserve.osbb.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.softserve.osbb.model.enums.NoticeType;

/**
 * Created by kris on 06.09.2016.
 */
@Entity
@Table(name = "notice")
public class Notice implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer noticeId;
    private User user;
    private String name;
    private String description;
    private Timestamp time;
    private String path;
    private NoticeType typeNotice;

    public Notice() {
    }

    public Notice(User user, String description, String path, NoticeType typeNotice) {
        this.user = user;
        this.description = description;
        this.path = path;
        this.typeNotice = typeNotice;
    }

    public Notice(User user,String name, String description, String path, NoticeType typeNotice) {
        this(user, description, path, typeNotice);
        this.name = name;
    }

    @Id
    @Column(name = "notice_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Integer noticeId) {
        this.noticeId = noticeId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "time")
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Basic
    @Column(name = "path")
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    public NoticeType getTypeNotice() {
        return typeNotice;
    }

    public void setTypeNotice(NoticeType typeNotice) {
        this.typeNotice = typeNotice;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "user=" + user +
                ", description='" + description + '\'' +
                ", path='" + path + '\'' +
                ", typeNotice=" + typeNotice +
                ", noticeId=" + noticeId +
                '}';
    }
}
