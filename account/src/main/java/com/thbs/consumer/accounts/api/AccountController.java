package com.thbs.consumer.accounts.api;

import com.thbs.consumer.accounts.model.Account;
import com.thbs.consumer.accounts.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;

@RestController
public class AccountController implements AccountsApi {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public ResponseEntity<List<Account>> getAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @Override
    public ResponseEntity<Account> getAccountById(Long id) {
        Optional<Account> account = accountService.getAccountById(id);
        return account.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Account> createAccount(Account account) {
        Account createdAccount = accountService.createAccount(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
    }

    @Override
    public ResponseEntity<Account> updateAccount(Long id, Account accountDetails) {
        Optional<Account> updatedAccount = accountService.updateAccount(id, accountDetails);
        return updatedAccount.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Void> deleteAccount(Long id) {
        boolean deleted = accountService.deleteAccount(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
