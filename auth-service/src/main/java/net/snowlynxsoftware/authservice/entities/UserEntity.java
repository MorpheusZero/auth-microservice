package net.snowlynxsoftware.authservice.entities;

import net.snowlynxsoftware.authservice.AppConstants;
import net.snowlynxsoftware.authservice.dtos.UserRegisterRequestDto;
import net.snowlynxsoftware.authservice.services.HashService;
import net.snowlynxsoftware.authservice.services.RandomNumberService;

import javax.persistence.*;
import java.util.Date;

/**
 * Represents a user of the application and their
 * ability to gain access to the application.
 */
@Entity
@Table(name = "Users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "CreateDate")
    private Date createDate;

    @Column(name = "LastEditDate")
    private Date lastEditDate;

    @Column(name = "InActive")
    private boolean inActive;

    @Column(name = "Verified")
    private boolean verified;

    @Column(name = "Email")
    private String email;

    @Column(name = "Hash")
    private String hash;

    @Column(name = "AuthCode")
    private int authCode;

    @Column(name = "LastLogin")
    private Date lastLogin;

    @Column(name = "LoginAttempts")
    private int loginAttempts;

    // Default Constructor
    public UserEntity() {
    }

    // New User Constructor
    public UserEntity(UserRegisterRequestDto requestDto) {
        Date now = new Date();

        createDate = now;
        lastEditDate = now;
        inActive = false;
        verified = false;
        email = requestDto.getEmail().toUpperCase();
        try {
            hash = HashService.getSaltedHash(requestDto.getPassword());
        } catch(Exception ex) {
            hash = null;
            System.out.printf(ex.getMessage());
        }
        authCode = RandomNumberService.generateRandomInteger(AppConstants.AUTH_CODE_MIN, AppConstants.AUTH_CODE_MAX);
        lastLogin = now;
        loginAttempts = 0;
    }

    // Used when changing a users password.
    public UserEntity(UserEntity entity, String newPassword) {
        Date now = new Date();

        id = entity.id;
        createDate = entity.createDate;
        lastEditDate = now;
        inActive = false;
        verified = true;
        email = entity.getEmail().toUpperCase();
        try {
            hash = HashService.getSaltedHash(newPassword);
        } catch(Exception ex) {
            hash = null;
            verified = false;
            System.out.printf(ex.getMessage());
        }
        authCode = RandomNumberService.generateRandomInteger(AppConstants.AUTH_CODE_MIN, AppConstants.AUTH_CODE_MAX);
        lastLogin = now;
        loginAttempts = 0;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastEditDate() {
        return lastEditDate;
    }

    public void setLastEditDate(Date lastEditDate) {
        this.lastEditDate = lastEditDate;
    }

    public boolean isInActive() {
        return inActive;
    }

    public void setInActive(boolean inActive) {
        this.inActive = inActive;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public int getAuthCode() {
        return authCode;
    }

    public void setAuthCode(int authCode) {
        this.authCode = authCode;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public int getLoginAttempts() {
        return loginAttempts;
    }

    public void setLoginAttempts(int loginAttempts) {
        this.loginAttempts = loginAttempts;
    }

}

