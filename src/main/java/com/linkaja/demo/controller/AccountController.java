package com.linkaja.demo.controller;

import com.linkaja.demo.model.AccountModel;
import com.linkaja.demo.model.TransferModel;
import com.linkaja.demo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "api")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping(value = "/account/{accountId}")
    public ResponseEntity<AccountModel> getAccount(@PathVariable Integer accountId) {
        AccountModel accountModel = accountService.findAccount(accountId);
        return new ResponseEntity<>(accountModel, HttpStatus.OK);
    }

    @PostMapping(value = "/account/{fromAccountNumber}/transfer")
    public ResponseEntity transfer(@PathVariable Integer fromAccountNumber, @RequestBody TransferModel model) {
        accountService.transfer(fromAccountNumber, model.getToAccountNumber(), model.getAmount());
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
