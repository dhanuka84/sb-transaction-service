package com.sb.transaction.service.services;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.sb.model.StatisticsDto;
import com.sb.model.TransactionDto;
import com.sb.transaction.service.domain.Transaction;
import com.sb.transaction.service.domain.TransactionStatus;
import com.sb.transaction.service.repositories.TransactionRepository;
import com.sb.transaction.service.web.mappers.TransactionMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

	private final TransactionRepository transactionRepository;
	private final TransactionMapper transactionMapper;

	@Override
	public TransactionDto doTransaction(final TransactionDto transactionDto) {
		log.debug("Placing a Transaction ");
		final Transaction tx = transactionMapper.dtoToTransaction(transactionDto);
		tx.setId(null);
		tx.setTxStatus(TransactionStatus.NEW);
		transactionRepository.saveAndFlush(tx);
		return transactionMapper.TransactionToDto(tx);
	}

	@Override
	public StatisticsDto statistics() {
		final StatisticsDto stats = transactionRepository.findStatistics()
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Statistics Not Found"));
		return stats;
	}

	@Override
	public void deleteTransactions() {
		log.debug("Deletes All the Transactions ");
		transactionRepository.deleteAllInBatch();

	}

}
