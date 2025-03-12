package com.shubnikofff.testtransactional.repository.wallet;


import com.shubnikofff.testtransactional.model.Wallet;
import java.math.BigDecimal;
public interface WalletRepository {

    public Wallet createWallet(Wallet wallet);

    public Wallet getWallet(String id);

    public void deposit(String id, BigDecimal amount);

    public void withdraw(String id, BigDecimal amount);
    
}
