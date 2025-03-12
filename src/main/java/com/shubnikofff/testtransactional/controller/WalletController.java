package com.shubnikofff.testtransactional.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import lombok.RequiredArgsConstructor;
import com.shubnikofff.testtransactional.service.WalletService;
import java.util.UUID;
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/wallet")
public class WalletController {

    private final WalletService walletService;

    @PostMapping
    public UUID createWallet() {
        return walletService.createWallet();
    }

    @PutMapping("deposit")
    public void deposit(@RequestBody DepositRequest request) {
        walletService.deposit(id, amount);
    }
    
    @GetMapping
    public String getWallet() {
        return "Wallet";
    }

}
