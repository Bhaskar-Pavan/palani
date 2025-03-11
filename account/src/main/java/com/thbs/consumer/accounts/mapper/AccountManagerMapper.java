package com.thbs.consumer.accounts.mapper;

import com.thbs.consumer.accounts.entity.AccountEntity;
import com.thbs.consumer.accounts.model.Account;

public interface AccountManagerMapper {

    Account mapToModel(AccountEntity entity);

    AccountEntity mapToEntity(Account model);
}
