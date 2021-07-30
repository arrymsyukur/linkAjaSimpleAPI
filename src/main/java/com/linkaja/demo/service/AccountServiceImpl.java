package com.linkaja.demo.service;

import com.linkaja.demo.domain.Account;
import com.linkaja.demo.exception.ResourceNotFoundException;
import com.linkaja.demo.exception.TransactionException;
import com.linkaja.demo.model.AccountModel;
import com.linkaja.demo.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public AccountModel findAccount(Integer accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        return new AccountModel(account);
    }

    @Override
    @Transactional
    public void transfer(Integer fromAccountNumber, Integer toAccountNumber, BigDecimal amount) {
        if (fromAccountNumber.equals(toAccountNumber)) {
            throw new TransactionException("Transfer Failed, Please Enter Correct Account Number");
        }
        Account fromAccount = accountRepository.findById(fromAccountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Source Account not found"));
        Account toAccount = accountRepository.findById(toAccountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Destination Account not found"));

        if (amount.compareTo(fromAccount.getBalance()) > 0) {
            throw new TransactionException("Insufficient Balance");
        }
        fromAccount.debet(amount);
        toAccount.kredit(amount);
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }
}
