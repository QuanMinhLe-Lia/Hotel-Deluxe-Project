/*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author Admin
 */
public class Account {

    private int accountId;
    private Role role;
    private String fullName;
    private String email;
    private String password;
    private String phone;
    private String image;
    private String address;
    private Date createdDate;
    private String status;
    private String OTP;

    public Account() {
    }

    public Account(int accountId, Role role, String fullName, String email, String password, String phone, String image, String address, Date createdDate, String status, String OTP) {
        this.accountId = accountId;
        this.role = role;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.image = image;
        this.address = address;
        this.createdDate = createdDate;
        this.status = status;
        this.OTP = OTP;
    }

    public Account(Role role, String fullName, String email, String phone, String address, Date createdDate) {
        this.role = role;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.createdDate = createdDate;
    }

    public Account(int accountId, Role role, String fullName, String email, String password, String phone, String status) {
        this.accountId = accountId;
        this.role = role;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.status = status;
    }

    public Account(String fullName, String email, String password, String phone) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public Account(int accountId, String fullName) {
        this.accountId = accountId;
        this.fullName = fullName;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOTP() {
        return OTP;
    }

    public void setOTP(String OTP) {
        this.OTP = OTP;
    }

}
