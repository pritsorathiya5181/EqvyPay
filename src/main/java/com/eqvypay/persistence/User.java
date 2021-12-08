package com.eqvypay.persistence;

import java.util.UUID;

public class User implements IUser{

    private UUID uuid;
    private String name;
    private String email;
    private String contact;
    private String password;
    private String securityAnswer;

    public User() {
        this.uuid = UUID.randomUUID();
    }

    @Override
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }
    
    @Override
    public String getContact() {
        return contact;
    }
    
    @Override
    public void setContact(String contact) {
        this.contact = contact;
    }
    
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getSecurityAnswer() {
        return securityAnswer;
    }

    @Override
    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

}
