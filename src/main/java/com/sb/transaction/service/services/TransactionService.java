package com.sb.transaction.service.services;

import org.springframework.validation.annotation.Validated;

import com.sb.model.StatisticsDto;
import com.sb.model.TransactionDto;

@Validated
public interface TransactionService {

	TransactionDto doTransaction(final TransactionDto TransactionDto);

	StatisticsDto statistics();

	void deleteTransactions();
}
