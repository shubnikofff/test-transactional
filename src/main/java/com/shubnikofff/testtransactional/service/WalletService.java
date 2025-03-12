package com.shubnikofff.testtransactional.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.shubnikofff.testtransactional.model.Wallet;
import com.shubnikofff.testtransactional.repository.wallet.WalletRepository;
import java.math.BigDecimal;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class WalletService {
    
    private final WalletRepository walletRepository;

    public UUID createWallet() {
        UUID walletID = UUID.randomUUID();
        walletRepository.createWallet(
            new Wallet(
                walletID, 
                BigDecimal.ZERO
            )
        );

        return walletID;
    }

    public void deposit(String id, BigDecimal amount) {
        walletRepository.deposit(id, amount);
    }

    public void withdraw(String id, BigDecimal amount) {
        walletRepository.withdraw(id, amount);
    }

    public BigDecimal getBalance(String id) {
        return walletRepository.getWallet(id).getBalance();
    }

    

}
