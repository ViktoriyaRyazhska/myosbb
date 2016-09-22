package com.softserve.osbb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


/**
 * Created by cavayman on 05.07.2016.
 */
@Entity
@Table(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User implements Serializable {

    private Integer userId;
    private String firstName;
    private String lastName;
    private Timestamp birthDate;
    private String email;
    private String phoneNumber;
    private String password;
    private String gender;

    private Boolean activated;
    private Role role;
    private Apartment apartment;
    private Osbb osbb;
    private Collection<Notice> notices=new ArrayList<>();
    private Collection<Vote> votes=new ArrayList<>();
    private Collection<Apartment> apartments=new ArrayList<>();
    private Collection<Message> messages=new ArrayList<>();
    private Collection<Ticket> assigned=new ArrayList<>();
    private Collection<Ticket> tickets=new ArrayList<>();
    private Collection<Event> events = new ArrayList<>();
    private Collection<Option> options=new ArrayList<>();
    private Collection<Report> reports=new ArrayList<>();

    private Boolean isOwner;

    public User(User user) {
        this.userId = user.getUserId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.birthDate = user.getBirthDate();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.password = user.getPassword();
        this.gender = user.getGender();
        this.role = user.getRole();
        this.apartment=user.apartment;
        this.osbb=user.getOsbb();
    }


    public User() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "firstName")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "lastName")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "birth_date")
    public Timestamp getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Timestamp birthDate) {
        this.birthDate = birthDate;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "phoneNumber")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    @Basic
    @Column(name = "password")
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "gender")
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Column(name = "activated")
    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    public Collection<Vote> getVotes() {
        return votes;
    }

    public void setVotes(Collection<Vote> votes) {
        this.votes = votes;
    }


    @Basic
    @Column(name = "is_owner")
    public Boolean isOwner() {
        return isOwner;
    }

    public void setOwner(Boolean owner) {
        isOwner = owner;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apartment_id", referencedColumnName = "apartment_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment appartament) {
        this.apartment = appartament;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "osbb_id")
    @JsonIgnore
    public Osbb getOsbb() {
        return osbb;
    }

    public void setOsbb(Osbb osbb) {
        this.osbb = osbb;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)
    @JsonIgnore
    public Collection<Message> getMessages() {
        return messages;
    }

    public void setMessages(Collection<Message> messages) {
        this.messages = messages;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    public Collection<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Collection<Ticket> tickets) {
        this.tickets = tickets;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @JsonIgnore
    public Collection<Report> getReports() {
        return reports;
    }

    public void setReports(Collection<Report> reports) {
        this.reports = reports;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "assigned")
    @JsonIgnore
    public Collection<Ticket> getAssigned() {
        return assigned;
    }

    public void setAssigned(Collection<Ticket> assigned) {
        this.assigned = assigned;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author")
    @JsonIgnore
    public Collection<Event> getEvents() {
        return events;
    }

    public void setEvents(Collection<Event> events) {
        this.events = events;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @JsonIgnore
    public Collection<Notice> getNotices() {
        return notices;
    }

    public void setNotices(Collection<Notice> notices) {
        this.notices = notices;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", roles='" + role + '\'' +
                ", osbb='" + osbb + '\'' +

                '}';
    }

    @JsonIgnore
    @ManyToMany(mappedBy = "users")
    public Collection<Option> getOptions() {
        return options;
    }

    public void setOptions(Collection<Option> options) {
        this.options = options;
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
