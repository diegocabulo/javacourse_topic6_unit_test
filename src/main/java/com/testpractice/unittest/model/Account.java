package com.testpractice.unittest.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Account {
    private String firstName;
    private String lastName;
    private Integer documentId;
    private Double accountBalance;
    private Boolean isCurrentAccount;
    private Long bankId;
    private String bankName;
}
