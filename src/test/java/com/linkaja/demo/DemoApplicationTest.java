package com.linkaja.demo;

import com.linkaja.demo.domain.Account;
import com.linkaja.demo.domain.Customer;
import com.linkaja.demo.exception.ResourceNotFoundException;
import com.linkaja.demo.exception.TransactionException;
import com.linkaja.demo.model.AccountModel;
import com.linkaja.demo.repository.AccountRepository;
import com.linkaja.demo.service.AccountServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;


@RunWith(MockitoJUnitRunner.class)
public class DemoApplicationTest {

    @InjectMocks
    private AccountServiceImpl accountService;
    @Mock
    private AccountRepository accountRepository;

    @Test
    public void getAccountSuccess() {
        Customer customer = new Customer(1101, "Ahmad");
        Account account = new Account(101, customer, customer.getId(), new BigDecimal(10000));

        Mockito.when(accountRepository.findById(101)).thenReturn(Optional.of(account));
        AccountModel responseAccount = accountService.findAccount(101);

        assertEquals(Integer.valueOf(101), responseAccount.getAccountNumber());
        assertEquals(new BigDecimal(10000), responseAccount.getBalance());
        assertEquals("Ahmad", responseAccount.getCustomerName());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getAccountNotFound() {
        Mockito.when(accountRepository.findById(101)).thenReturn(Optional.empty());
        accountService.findAccount(101);
    }

    @Test
    public void transferSuccess() {
        Customer firstCustomer = new Customer(1101, "Ahmad");
        Account fromAccount = new Account(101, firstCustomer, firstCustomer.getId(), new BigDecimal(5000));

        Customer secondCustomer = new Customer(1102, "Abdul");
        Account toAccount = new Account(102, secondCustomer, secondCustomer.getId(), new BigDecimal(10000));

        Mockito.when(accountRepository.findById(101)).thenReturn(Optional.of(fromAccount));
        Mockito.when(accountRepository.findById(102)).thenReturn(Optional.of(toAccount));

        accountService.transfer(101, 102, new BigDecimal(3000));
        assertEquals(new BigDecimal(2000), fromAccount.getBalance());
        assertEquals(new BigDecimal(13000), toAccount.getBalance());
    }

    @Test(expected = TransactionException.class)
    public void transferWithInsufficientBalance() {
        Customer firstCustomer = new Customer(1101, "Ahmad");
        Account fromAccount = new Account(101, firstCustomer, firstCustomer.getId(), new BigDecimal(5000));

        Customer secondCustomer = new Customer(1102, "Abdul");
        Account toAccount = new Account(102, secondCustomer, secondCustomer.getId(), new BigDecimal(10000));

        Mockito.when(accountRepository.findById(101)).thenReturn(Optional.of(fromAccount));
        Mockito.when(accountRepository.findById(102)).thenReturn(Optional.of(toAccount));

        accountService.transfer(101, 102, new BigDecimal(8000));
    }

    @Test(expected = TransactionException.class)
    public void transferWithInvalidAmount() {
        //Transfer Amount more than source account's balance
        Customer firstCustomer = new Customer(1101, "Ahmad");
        Account fromAccount = new Account(101, firstCustomer, firstCustomer.getId(), new BigDecimal(5000));

        Customer secondCustomer = new Customer(1102, "Abdul");
        Account toAccount = new Account(102, secondCustomer, secondCustomer.getId(), new BigDecimal(10000));

        Mockito.when(accountRepository.findById(101)).thenReturn(Optional.of(fromAccount));
        Mockito.when(accountRepository.findById(102)).thenReturn(Optional.of(toAccount));

        accountService.transfer(101, 102, new BigDecimal(8000));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void transferWithToAccountNotFound() {
        Mockito.when(accountRepository.findById(101)).thenReturn(Optional.empty());
        accountService.transfer(101, 102, new BigDecimal(8000));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void transferWithFromAccountNotFound() {
        Customer firstCustomer = new Customer(1101, "Ahmad");
        Account fromAccount = new Account(101, firstCustomer, firstCustomer.getId(), new BigDecimal(5000));
        Mockito.when(accountRepository.findById(101)).thenReturn(Optional.of(fromAccount));
        Mockito.when(accountRepository.findById(102)).thenReturn(Optional.empty());
        accountService.transfer(101, 102, new BigDecimal(8000));
    }
}
