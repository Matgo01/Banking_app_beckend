package com.example.Banking_app_beckend.service.impl;

import com.example.Banking_app_beckend.dto.AccountDto;
import com.example.Banking_app_beckend.dto.mapper.AccountMapper;
import com.example.Banking_app_beckend.model.Account;
import com.example.Banking_app_beckend.repository.AccountRepository;
import com.example.Banking_app_beckend.service.AccountService;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository){
        this.accountRepository=accountRepository;
    }
    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account= AccountMapper.mapToAccount(accountDto);
        Account saveAccount=accountRepository.save(account);
        return AccountMapper.matToAccountDto(saveAccount);
    }

    @Override
    public AccountDto getAccountById(Long id) {
        Account account=accountRepository.findById(id).orElseThrow(()->new RuntimeException("account doesn't exist"));
        return AccountMapper.matToAccountDto(account);
    }

    @Override
    public AccountDto deposit(Long id, double amount) {
        Account account=accountRepository.findById(id).orElseThrow(()->new RuntimeException("account doesn't exist"));
        double total=account.getBelance()+amount;
        account.setBelance(total);
        Account savedAccount=accountRepository.save(account);
        return AccountMapper.matToAccountDto(savedAccount);
    }

    @Override
    public AccountDto withdraw(Long id, double amount) {
        Account account=accountRepository.findById(id).orElseThrow(()->new RuntimeException("account doesn't exist"));
        if(account.getBelance()<amount){
            throw  new RuntimeException("insufficient amount");
        }
        double total=account.getBelance()-amount;
        account.setBelance(total);
        Account savedAccount=accountRepository.save(account);
        return AccountMapper.matToAccountDto(account);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account> accounts=accountRepository.findAll();
        return accounts.stream().map((account)->AccountMapper.matToAccountDto(account)).collect(Collectors.toList());

    }

    @Override
    public void deleteAccount( Long id) {
        Account account=accountRepository.findById(id).orElseThrow(()->new RuntimeException("account doesn't exist"));
        accountRepository.deleteById(id);

    }

}
