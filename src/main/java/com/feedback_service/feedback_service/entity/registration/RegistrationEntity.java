package com.feedback_service.feedback_service.entity.registration;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;


import java.time.ZonedDateTime;

@Entity
@Table(name="registration")
public class RegistrationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="registration_id", columnDefinition = "SERIAL")
    private Integer id;

    @Column(name="fio_person", columnDefinition = "VARCHAR(256)", nullable = false)
    private String fio;

    @Column(name="phone_number", columnDefinition = "VARCHAR(256)", nullable = false)
    private String phone;

    @Column(name="doctor_spec", columnDefinition = "VARCHAR(256)", nullable = false)
    private String doctor;

    @Column(name="date_registration", columnDefinition = "TIMESTAMP", nullable = false)
    private ZonedDateTime dateRegistration;

    @Column(name="is_registered", columnDefinition = "BOOLEAN", nullable = false)
    private boolean isRegistered;

    @Column(name="comments_reg", columnDefinition = "VARCHAR(256)")
    private String comments;

    @Column(name="date_user", columnDefinition = "TIMESTAMP", nullable = false)
    private ZonedDateTime date;

    public RegistrationEntity() { }

    public RegistrationEntity(String fio, String phone, String doctor, ZonedDateTime dateRegistration,
                              boolean isRegistered, String comments) {
        this.fio = fio;
        this.phone = phone;
        this.doctor = doctor;
        this.dateRegistration = dateRegistration;
        this.isRegistered = isRegistered;
        this.comments = comments;
    }

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

    public ZonedDateTime getDateRegistration() {
        return dateRegistration;
    }

    public boolean isRegistered() {
        return isRegistered;
    }

    public String getComments() {
        return comments;
    }

    public ZonedDateTime getDate() {
        return date;
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

    public void setDateRegistration(ZonedDateTime dateRegistration) {
        this.dateRegistration = dateRegistration;
    }

    public void setRegistered(boolean registered) {
        isRegistered = registered;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RegistrationEntity that = (RegistrationEntity) o;

        if (isRegistered != that.isRegistered) return false;
        if (!id.equals(that.id)) return false;
        if (!fio.equals(that.fio)) return false;
        if (!phone.equals(that.phone)) return false;
        if (!doctor.equals(that.doctor)) return false;
        if (!dateRegistration.equals(that.dateRegistration)) return false;
        if(!date.equals(that.date)) return false;
        return  comments.equals(that.comments);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + fio.hashCode();
        result = 31 * result + phone.hashCode();
        result = 31 * result + doctor.hashCode();
        result = 31 * result + dateRegistration.hashCode();
        result = 31 * result + (isRegistered ? 1 : 0);
        result = 31 * result + date.hashCode();
        result = 31 * result + comments.hashCode();
        return result;
    }
}