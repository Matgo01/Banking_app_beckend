package com.example.Banking_app_beckend.dto.mapper;

import com.example.Banking_app_beckend.dto.AccountDto;
import com.example.Banking_app_beckend.model.Account;

public class AccountMapper {
    public static Account mapToAccount(AccountDto accountDto){
        Account account=new Account(
        accountDto.getId(),
        accountDto.getAccountHolderName(),
        accountDto.getBelance()
        );
        return account;
    }

    public static AccountDto matToAccountDto(Account account){
        AccountDto accountDto=new AccountDto(
                account.getId(),
                account.getAccountHolderName(),
                account.getBelance());
        return accountDto;
    }
}
