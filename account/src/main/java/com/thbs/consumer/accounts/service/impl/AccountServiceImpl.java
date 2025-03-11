package com.thbs.consumer.accounts.service.impl;

import com.thbs.consumer.accounts.ApplicationConstants;
import com.thbs.consumer.accounts.client.AccountFeignClient;
import com.thbs.consumer.accounts.entity.AccountEntity;
import com.thbs.consumer.accounts.entity.BankEntity;
import com.thbs.consumer.accounts.exception.BadRequestException;
import com.thbs.consumer.accounts.exception.ResourceNotFoundException;
import com.thbs.consumer.accounts.mapper.AccountManagerMapper;
import com.thbs.consumer.accounts.model.Account;
import com.thbs.consumer.accounts.publisher.KafkaMessagePublisher;
import com.thbs.consumer.accounts.publisher.RabbitMQPublisher;
import com.thbs.consumer.accounts.repository.AccountRepository;
import com.thbs.consumer.accounts.repository.BankRepository;
import com.thbs.consumer.accounts.service.AccountService;
import com.thbs.consumer.accounts.subscriber.RabbitMQSubscriber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private final BankRepository bankRepository;

    private final AccountManagerMapper accountManagerMapper;

    private final RabbitMQPublisher rabbitMQPublisher;

    private final KafkaMessagePublisher kafkaMessagePublisher;

    private final AccountFeignClient accountFeignClient;

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll().stream().map(accountManagerMapper::mapToModel).toList();
    }

    @Override
    public Optional<Account> getAccountById(Long id) {
        try {
            Account account = accountFeignClient.isAccountActive(id);
            log.info("Account type from accountFeignClient: " + account.getAccountType());
        } catch (Exception exception) {
            log.error("Feign Exception while calling accountFeignClient : ");
        }
        Optional<Account> account = accountRepository.findById(id).map(accountManagerMapper::mapToModel);
        account.ifPresent(acc -> kafkaMessagePublisher.publishToKafka("kafka-topic", acc.getAccountNumber()));
        return account;
    }

    @Override
    public Account createAccount(Account account) {
        if (account.getId() != null && accountRepository.existsById(account.getId())) {
            throw new BadRequestException("Account with id " + account.getId() + " already exists.");
        }
        AccountEntity accountEntity = accountManagerMapper.mapToEntity(account);
        AccountEntity savedEntity = accountRepository.save(accountEntity);


        rabbitMQPublisher.publishMessage(account.getAccountNumber());
        BankEntity bankEntity = bankRepository.findById(account.getBankCode()).orElse(new BankEntity());
        bankEntity.setId(String.valueOf(UUID.randomUUID()));
        bankEntity.setIfscCode(account.getBankCode());
        bankEntity.setBankName("SBI");
        bankRepository.save(bankEntity);

        return accountManagerMapper.mapToModel(savedEntity);
    }

    @Override
    public Optional<Account> updateAccount(Long id, Account accountDetails) {
        return accountRepository.findById(id).map(existingAccount -> {
            existingAccount.setName(accountDetails.getName());
            existingAccount.setEmail(accountDetails.getEmail());
            existingAccount.setAccountNumber(accountDetails.getAccountNumber());
            existingAccount.setAccountType(accountDetails.getAccountType());
            existingAccount.setDob(accountDetails.getDob());
            existingAccount.setBankCode(accountDetails.getBankCode());
            AccountEntity updatedEntity = accountRepository.save(existingAccount);
            return accountManagerMapper.mapToModel(updatedEntity);
        }).or(() -> { throw new ResourceNotFoundException(ApplicationConstants.ACCOUNT_NOT_FOUND_WITH_ID + id); });
    }

    @Override
    public boolean deleteAccount(Long id) {
        return accountRepository.findById(id).map(account -> {
            accountRepository.delete(account);
            return true;
        }).orElseThrow(() -> new ResourceNotFoundException(ApplicationConstants.ACCOUNT_NOT_FOUND_WITH_ID + id));
    }

}
