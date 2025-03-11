package com.thbs.consumer.accounts.service;

import com.thbs.consumer.accounts.model.Account;
import java.util.List;
import java.util.Optional;

public interface AccountService {

    List<Account> getAllAccounts();

    Optional<Account> getAccountById(Long id);

    Account createAccount(Account account);

    Optional<Account> updateAccount(Long id, Account accountDetails);

    boolean deleteAccount(Long id);
}
