package com.feedback_service.feedback_service;

import jakarta.persistence.*;

import javax.xml.crypto.Data;
import java.util.Date;

@Entity
@Table(name="registration")
public class Registration {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="registration_id")
    private Integer id;

    @Column(name="fio_person")
    private String fio;

    @Column(name="phone_number")
    private String phone;

    @Column(name="doctor_spec")
    private String doctor;

    @Column(name="date_registration")
    private Date dateRegistration;

    @Column(name="is_registered")
    private boolean isRegistered;

    @Column(name="comments_reg")
    private String comments;

    @Column(name="public_user_id")
    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    public Integer getId() {
        return id;
    }

    public String getFio() {
        return fio;
    }

    public String getPhone() {
        return phone;
    }

    public String getDoctor() {
        return doctor;
    }

    public Date getDateRegistration() {
        return dateRegistration;
    }

    public boolean isRegistered() {
        return isRegistered;
    }

    public String getComments() {
        return comments;
    }

    public User getUser() {
        return user;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public void setDateRegistration(Date dateRegistration) {
        this.dateRegistration = dateRegistration;
    }

    public void setRegistered(boolean registered) {
        isRegistered = registered;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setUser(User user) {
        this.user = user;
    }
}