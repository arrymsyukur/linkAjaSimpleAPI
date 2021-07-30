package com.linkaja.demo;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.linkaja.demo.controller.AccountController;
import com.linkaja.demo.model.AccountModel;
import com.linkaja.demo.model.TransferModel;
import com.linkaja.demo.repository.AccountRepository;
import com.linkaja.demo.repository.CustomerRepository;
import com.linkaja.demo.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AccountController.class)
public class DemoControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AccountService accountService;
    @MockBean
    private AccountRepository accountRepository;
    @MockBean
    private CustomerRepository customerRepository;

    @Test
    public void findAccount() throws Exception {
        AccountModel accountModel = new AccountModel(101, "Ilham", new BigDecimal(10000));
        given(accountService.findAccount(101)).willReturn(accountModel);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/account/101"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.account_number",is(101)))
                .andExpect(jsonPath("$.customer_name",is("Ilham")))
                .andExpect(jsonPath("$.balance",is(10000)));
    }

    @Test
    public void transfer() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        TransferModel transferModel = new TransferModel(102,new BigDecimal(10000));
        mockMvc.perform(post("/api/account/101/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transferModel)))
                .andExpect(status().isCreated());
    }
}
