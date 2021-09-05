package com.sb.transaction.service.services;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sb.model.StatisticsDto;
import com.sb.model.TransactionDto;
import com.sb.transaction.service.domain.Transaction;
import com.sb.transaction.service.domain.TransactionStatus;
import com.sb.transaction.service.exception.FutureTime;
import com.sb.transaction.service.exception.NotFound;
import com.sb.transaction.service.exception.TimeExceeded;
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
	final Instant now = Instant.now();
	final Instant time = transactionDto.getTimestamp().toInstant();
	final Duration duration = Duration.between(time, now);
	final Duration limit = Duration.ofSeconds(60);
	final Boolean exceededLimit = (duration.compareTo(limit) > 0);
	if (exceededLimit) {
	    throw new TimeExceeded("Time: " + time.toString());
	}
	if (time.isAfter(Instant.now())) {
	    throw new FutureTime("Time: " + time.toString());
	}
	final Transaction tx = transactionMapper.dtoToTransaction(transactionDto);
	tx.setId(null);
	tx.setTxStatus(TransactionStatus.NEW);
	transactionRepository.saveAndFlush(tx);
	return transactionMapper.TransactionToDto(tx);
    }

    @Override
    public Optional<StatisticsDto> statistics() {
	final StatisticsDto stats = transactionRepository.findStatistics().orElseThrow(() -> new NotFound("Statistics Not Found"));
	return Optional.of(stats);
    }

    @Override
    public void deleteTransactions() {
	log.debug("Deletes All the Transactions ");
	transactionRepository.deleteAllInBatch();

    }

}
