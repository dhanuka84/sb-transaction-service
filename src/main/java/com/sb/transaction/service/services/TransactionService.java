package com.sb.transaction.service.services;

import java.util.Optional;

import org.springframework.validation.annotation.Validated;

import com.sb.model.StatisticsDto;
import com.sb.model.TransactionDto;

@Validated
public interface TransactionService {

	TransactionDto doTransaction(final TransactionDto TransactionDto);

	Optional<StatisticsDto> statistics();

	void deleteTransactions();
}
