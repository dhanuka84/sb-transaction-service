package com.sb.transaction.service.services;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import com.sb.model.TransactionDto;
import com.sb.transaction.service.domain.Transaction;
import com.sb.transaction.service.domain.TransactionStatus;
import com.sb.transaction.service.repositories.TransactionRepository;
import com.sb.transaction.service.services.TransactionService;

public abstract class BaseServiceTest {

	public final long uuid1 = 1;
	public final long uuid2 = 2;
	public final long uuid3 = 3;

	private static final SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
	private static final Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	private static final String currentTimeStr = sdf2.format(timestamp);

	@Autowired
	TransactionService transactionService;

	@Autowired
	TransactionRepository transactionRepository;

	TransactionDto testTxDto1;
	TransactionDto testTxDto2;
	TransactionDto testTxDto3;

	Transaction testTx1;
	Transaction testTx2;
	Transaction testTx3;

	private TransactionDto buildOrderDto(long uuid) {
		OffsetDateTime offsetDT8 = OffsetDateTime.parse(currentTimeStr);
		return TransactionDto.builder().id(uuid).timestamp(offsetDT8).amount(new BigDecimal(123.50)).build();
	}

	@BeforeEach
	void setUp() {

		testTxDto1 = buildOrderDto(uuid1);

		testTxDto2 = buildOrderDto(uuid2);

		testTxDto3 = buildOrderDto(uuid3);

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		testTx1 = transactionRepository.save(Transaction.builder().txStatus(TransactionStatus.NEW).timestamp(timestamp)
				.amount(new BigDecimal(123.50)).build());
		transactionRepository.save(testTx1);

		testTx2 = transactionRepository.save(Transaction.builder().txStatus(TransactionStatus.NEW).timestamp(timestamp)
				.amount(new BigDecimal(123.50)).build());
		transactionRepository.save(testTx2);

		testTx3 = transactionRepository.save(Transaction.builder().txStatus(TransactionStatus.NEW).timestamp(timestamp)
				.amount(new BigDecimal(123.50)).build());
		transactionRepository.save(testTx3);

	}
}
