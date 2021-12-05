package com.eqvypay.web;

import com.eqvypay.persistence.User;
import com.eqvypay.service.repository.IRepositories;

public interface WebOptions {
    public void options(User user, IRepositories repositories) throws Exception;
}
