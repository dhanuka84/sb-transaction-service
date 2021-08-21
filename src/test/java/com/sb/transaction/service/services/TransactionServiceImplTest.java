package com.sb.transaction.service.services;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.Transactional;

import com.sb.model.StatisticsDto;
import com.sb.model.TransactionDto;

@SpringBootTest
@ComponentScan(basePackages = { "com.sb.transaction.service.services", "com.sb.transaction.service.web.mappers" })
class TransactionServiceImplTest extends BaseServiceTest {

	@Transactional
	@Test
	void doTransaction() {

		TransactionDto placedOrder = TransactionService.doTransaction(testTxDto1);

		assertThat(placedOrder.getId()).isNotNull();
		assertThat(placedOrder.getTxStatus()).isEqualToIgnoringCase("NEW");
	}

	@Transactional
	@Test
	void getStatistics() {
		StatisticsDto dto = TransactionService.statistics();

		assertThat(dto.getCount()).isEqualTo(3);
	}

	@Transactional
	@Test
	void deleteTransactions() {
		TransactionService.deleteTransactions();

	}
}