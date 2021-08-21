package com.sb.transaction.service.services;

import com.sb.model.StatisticsDto;
import com.sb.model.TransactionDto;

public interface TransactionService {

	TransactionDto doTransaction(TransactionDto TransactionDto);

	StatisticsDto statistics();

	void deleteTransactions();
}
