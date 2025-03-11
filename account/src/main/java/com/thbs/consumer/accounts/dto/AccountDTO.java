package com.thbs.consumer.accounts.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDTO {
    private Long id;
    private String name;
    private String email;
    private String accountNumber;
    private String accountType;
    private LocalDate dob;
    private String bankCode;
}
