package com.linkaja.demo;

import com.linkaja.demo.domain.Account;
import com.linkaja.demo.domain.Customer;
import com.linkaja.demo.repository.AccountRepository;
import com.linkaja.demo.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.math.BigDecimal;
import java.util.List;

@SpringBootApplication
@Slf4j
public class DemoApplication {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AccountRepository accountRepository;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {
        //Initialize Database Data
        List<Customer> allCustomers = this.customerRepository.findAll();
        log.info("Number Init of customers: " + allCustomers.size());

        Customer newCustomer = new Customer();
        newCustomer.setId(1001);
        newCustomer.setName("Bob Martin");
        log.info("Saving new customer...");
        //Saving Account 1
        Account account = new Account();
        account.setAccountNumber(555001);
        account.setCustomer(newCustomer);
        account.setCustomerNumber(newCustomer.getId());
        account.setBalance(new BigDecimal(10000));

        this.customerRepository.save(newCustomer);
        this.accountRepository.save(account);

        newCustomer = new Customer();
        newCustomer.setId(1002);
        newCustomer.setName("Linus Torvalds");
        log.info("Saving new customer...");
        //Saving Account 2
        account = new Account();
        account.setAccountNumber(555002);
        account.setCustomer(newCustomer);
        account.setCustomerNumber(newCustomer.getId());
        account.setBalance(new BigDecimal(15000));
        this.customerRepository.save(newCustomer);
        this.accountRepository.save(account);

        allCustomers = this.customerRepository.findAll();
        log.info("Number of customers: " + allCustomers.size());
    }
}
