package com.linkaja.demo.service;

import com.linkaja.demo.model.AccountModel;

import java.math.BigDecimal;

public interface AccountService {
    public AccountModel findAccount(Integer accountId);

    public void transfer(Integer fromAccountNumber, Integer toAccountNumber, BigDecimal amount);
}
