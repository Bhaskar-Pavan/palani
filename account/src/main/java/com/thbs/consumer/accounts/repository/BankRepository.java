package com.thbs.consumer.accounts.repository;

import com.thbs.consumer.accounts.entity.BankEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends MongoRepository<BankEntity, String> {
}
