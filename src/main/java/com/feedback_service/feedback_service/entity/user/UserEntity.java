package com.feedback_service.feedback_service.entity.user;

import com.feedback_service.feedback_service.entity.registration.RegistrationEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="public_user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", columnDefinition = "SERIAL")
    private Integer id;

    @Column(name="username", columnDefinition = "VARCHAR(256)", unique = true, nullable = false)
    private String username;

    @Column(name="password", columnDefinition = "VARCHAR(256)", nullable = false)
    private String password;

    @Column(name="security_stamp", columnDefinition = "VARCHAR(256)", nullable = false)
    private String securityStamp;

    @Column(name = "registration_date", columnDefinition = "TIMESTAMP", nullable = false)
    private ZonedDateTime registrationDate;

    @Column(name = "account_locked", columnDefinition = "BOOLEAN", nullable = false)
    private boolean accountLocked;

    public Integer getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getSecurityStamp() {
        return this.securityStamp;
    }

    public String getPassword() {
        return this.password;
    }

    public ZonedDateTime getRegistrationDate() {
        return registrationDate;
    }

    public boolean isAccountLocked() {
        return accountLocked;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setSecurityStamp(String securityStamp) {
        this.securityStamp = securityStamp;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRegistrationDate(ZonedDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void setAccountLocked(boolean accountLocked) {
        this.accountLocked = accountLocked;
    }


    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;

        UserEntity that = (UserEntity) other;

        if (accountLocked != that.accountLocked) return false;
        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(username, that.username)) return false;
        if (!Objects.equals(password, that.password)) return false;
        if (!Objects.equals(securityStamp, that.securityStamp))
            return false;
        return Objects.equals(registrationDate, that.registrationDate);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (securityStamp != null ? securityStamp.hashCode() : 0);
        result = 31 * result + (registrationDate != null ? registrationDate.hashCode() : 0);
        result = 31 * result + (accountLocked ? 1 : 0);
        return result;
    }
}
