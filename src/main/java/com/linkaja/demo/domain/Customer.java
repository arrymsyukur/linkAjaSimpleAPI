package com.linkaja.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@AllArgsConstructor
@Getter
@Setter
public class Customer {
    @Id
    private Integer id;
    private String name;


    public Customer() {

    }
}
