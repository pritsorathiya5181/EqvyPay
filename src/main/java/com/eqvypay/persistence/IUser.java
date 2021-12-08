package com.eqvypay.persistence;

import java.util.UUID;

public interface IUser {
    public void setUuid(UUID uuid);
    public UUID getUuid();
    public String getName();
    public void setName(String name);
    public String getEmail();
    public void setEmail(String email);
    public String getContact();
    public void setContact(String contact);
    public String getPassword();
    public void setPassword(String password);
    public String getSecurityAnswer();
    public void setSecurityAnswer(String securityAnswer);
}
