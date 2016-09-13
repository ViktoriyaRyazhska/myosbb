package com.softserve.osbb.model;

import com.softserve.osbb.model.enums.NoticeType;

import javax.persistence.*;

/**
 * Created by kris on 06.09.2016.
 */
@Entity
@Table(name = "notice")
public class Notice {
    private Integer noticeId;
    private User user;
    private String description;
    private String path;
    private NoticeType typeNotice;

    public Notice() {
    }

    public Notice(User user, String description, String path) {
        this.user = user;
        this.description = description;
        this.path = path;
    }

    public Notice(User user, String description, String path, NoticeType typeNotice) {
        this(user, description, path);
        this.typeNotice = typeNotice;
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
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
