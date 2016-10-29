package com.softserve.osbb.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.softserve.osbb.model.enums.EventStatus;
import com.softserve.osbb.model.enums.Periodicity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by nataliia on 05.07.2016.
 */
@Entity
@Table(name = "event")
public class Event {

    private Integer eventId;
    private String title;
    private Timestamp startTime;
    private Timestamp endTime;
    private String description;
    private User author;
    private Osbb osbb;
    private Periodicity repeat;
    private List<Attachment> attachments;
    private EventStatus status;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    @JsonProperty(value = "id")
    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "start_time")
    @JsonProperty(value = "start")
    @JsonFormat(pattern = "yyyy-MM-dd'T'hh:mmZ")
    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "end_time")
    @JsonProperty(value = "end")
    @JsonFormat(pattern = "yyyy-MM-dd'T'hh:mmZ")
    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author")
    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "osbb_id", referencedColumnName = "osbb_id")
    public Osbb getOsbb() {
        return osbb;
    }

    public void setOsbb(Osbb osbb) {
        this.osbb = osbb;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "repeats")
    public Periodicity getRepeat() {
        return repeat;
    }

    public void setRepeat(Periodicity repeat) {
        this.repeat = repeat;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH )
    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    @Transient
    public EventStatus getStatus() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        if (now.compareTo(endTime) > 0) {
            return EventStatus.FINISHED;
        } else if (now.compareTo(startTime) < 0) {
            return EventStatus.FUTURE;
        } else {
            return EventStatus.IN_PROCESS;
        }
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
