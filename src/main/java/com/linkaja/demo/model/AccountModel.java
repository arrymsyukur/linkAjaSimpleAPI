package com.linkaja.demo.model;

import com.linkaja.demo.domain.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccountModel {
    private Integer accountNumber;
    private String customerName;
    private BigDecimal balance;

    public AccountModel(Account account){
        this.accountNumber = account.getAccountNumber();
        this.customerName = account.getCustomer().getName();
        this.balance = account.getBalance();
    }
}
