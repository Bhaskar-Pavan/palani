package com.thbs.consumer.accounts.mapper.impl;

import com.thbs.consumer.accounts.entity.AccountEntity;
import com.thbs.consumer.accounts.mapper.AccountManagerMapper;
import com.thbs.consumer.accounts.model.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AccountManagerMapperImpl implements AccountManagerMapper {
    @Override
    public Account mapToModel(AccountEntity entity) {
        Account account = new Account();
        account.setId(entity.getId());
        account.setName(entity.getName());
        account.setEmail(entity.getEmail());
        account.setAccountNumber(entity.getAccountNumber());
        account.setAccountType(entity.getAccountType());
        account.setDob(entity.getDob());
        account.setBankCode(entity.getBankCode());
        return account;
    }

    @Override
    public AccountEntity mapToEntity(Account account) {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(account.getId());
        accountEntity.setName(account.getName());
        accountEntity.setEmail(account.getEmail());
        accountEntity.setAccountNumber(account.getAccountNumber());
        accountEntity.setAccountType(account.getAccountType());
        accountEntity.setAccountType(account.getAccountType());
        accountEntity.setDob(account.getDob());
        accountEntity.setBankCode(account.getBankCode());
        return accountEntity;
    }
}
