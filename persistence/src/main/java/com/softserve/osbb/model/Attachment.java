package com.softserve.osbb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.softserve.osbb.model.enums.AttachmentType;
import com.softserve.osbb.utils.CustomLocalDateDeserializer;
import com.softserve.osbb.utils.CustomLocalDateSerializer;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

/**
 * Created by nataliia on 09.07.2016.
 */

@Entity
@Table(name = "attachment")
public class Attachment {

    private Integer attachmentId;
    private String path;
    private AttachmentType type;
    private LocalDate date;

    @Id
    @Column(name = "attachment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Integer attachmentId) {
        this.attachmentId = attachmentId;
    }

    @Basic
    @Column(name = "path")
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    @Column(name = "type", columnDefinition = "varchar(255) default 'DATA'")
    public AttachmentType getType() {
        return type;
    }

    public void setType(AttachmentType type) {
        this.type = type;
    }

    @Basic
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @Column(name = "date")
    public LocalDate getDate() {
        return date;
    }

    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    public void setDate(LocalDate date) {
        this.date = date;
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
