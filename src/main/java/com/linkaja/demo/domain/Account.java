package com.linkaja.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Account {
    @Id
    private Integer accountNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_number", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private Customer customer;

    @Column(name = "customer_number", insertable = false, updatable = false)
    @JsonIgnore
    private Integer customerNumber;

    @Column
    private BigDecimal balance;


    public void debet(BigDecimal amount) {
        this.balance = this.balance.subtract(amount);
    }

    public void kredit(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }

}
