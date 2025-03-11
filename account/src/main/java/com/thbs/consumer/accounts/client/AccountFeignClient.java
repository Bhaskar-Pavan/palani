package com.thbs.consumer.accounts.client;

import com.thbs.consumer.accounts.model.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;

@FeignClient(name = "account-validation-service", url = "http://localhost:1080")
public interface AccountFeignClient {

    @GetMapping("/accounts/{id}")
    Account isAccountActive(@PathVariable("id") Long id);
}
