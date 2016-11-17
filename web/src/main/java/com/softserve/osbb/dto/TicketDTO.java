package com.softserve.osbb.dto;

import com.softserve.osbb.model.enums.TicketState;

import java.sql.Timestamp;

/**
 * Created by Kris on 22.08.2016.
 */
public class TicketDTO {
    private Integer ticketId;
    private String name;
    private String description;
    private TicketState state;
    private Timestamp stateTime;
    private Timestamp discussed;
    private Timestamp deadline;
    private Timestamp time;
    private UserDTO user;
    private UserDTO assigned;

    public TicketDTO() {
    }

    public TicketDTO(Integer ticketId, String name, String description, TicketState state, Timestamp stateTime, Timestamp time,Timestamp discussed) {
        this.ticketId = ticketId;
        this.name = name;
        this.description = description;
        this.state = state;
        this.stateTime = stateTime;
        this.time = time;
        this.discussed = discussed;
    }

    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TicketState getState() {
        return state;
    }

    public void setState(TicketState state) {
        this.state = state;
    }

    public Timestamp getStateTime() {
        return stateTime;
    }

    public void setStateTime(Timestamp stateTime) {
        this.stateTime = stateTime;
    }

    public Timestamp getTime() {
        return time;
    }

    public Timestamp getDeadline() {
        return deadline;
    }

    public void setDeadline(Timestamp deadline) {
        this.deadline = deadline;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Timestamp getDiscussed() {
        return discussed;
    }

    public void setDiscussed(Timestamp discussed) {
        this.discussed = discussed;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public UserDTO getAssigned() {
        return assigned;
    }

    public void setAssigned(UserDTO assigned) {
        this.assigned = assigned;
    }

    @Override
    public String toString() {
        return "TicketDTO{" +
                "ticketId=" + ticketId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", state=" + state +
                ", stateTime=" + stateTime +
                ", time=" + time +
                ", deadline=" + deadline +
                ", user=" + user +
                '}';
    }
}
