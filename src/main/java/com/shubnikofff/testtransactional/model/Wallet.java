package com.shubnikofff.testtransactional.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Wallet {

    private final UUID id;
    private BigDecimal balance = BigDecimal.ZERO;

    public Wallet(UUID id, BigDecimal balance) {
        this.id = id;
    }

    public void deposit(BigDecimal amount) {
        balance = balance.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        balance = balance.subtract(amount);
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public UUID getId() {
        return id;
    }

}
