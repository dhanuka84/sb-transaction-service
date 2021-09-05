package com.sb.transaction.service.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.Transactional;

import com.sb.model.StatisticsDto;
import com.sb.model.TransactionDto;
import com.sb.transaction.service.exception.TimeExceeded;

@SpringBootTest
@ComponentScan(basePackages = { "com.sb.transaction.service.services", "com.sb.transaction.service.web.mappers" })
class TransactionServiceImplTest extends BaseServiceTest {
	
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Transactional
	@Test
	void doTransaction() {

		testTxDto1.setId(null);
		TransactionDto placedOrder = transactionService.doTransaction(testTxDto1);

		
		assertThat(placedOrder.getId()).isNotNull();
		assertThat(placedOrder.getTxStatus()).isEqualToIgnoringCase("NEW");
	}
	
	@Transactional
	@Test
	void doTransactionFail() {

		testTxDto2.setId(null);
		OffsetDateTime offsetDT8 = OffsetDateTime.parse("2019-08-31T15:20:30+08:00");
		testTxDto2.setTimestamp(offsetDT8);
		//TransactionDto placedOrder = transactionService.doTransaction(testTxDto2);

		 //when
	    final Throwable raisedException = catchThrowable(() -> transactionService.doTransaction(testTxDto2));

	    //then
	    assertThat(raisedException).isInstanceOf(TimeExceeded.class)
	            .hasMessageContaining("Time");
		
	}

	@Transactional
	@Test
	void getStatistics() {
		Optional<StatisticsDto> dto = transactionService.statistics();

		assertThat(dto.get().getCount()).isEqualTo(3);
	}

	@Transactional
	@Test
	void deleteTransactions() {
		transactionService.deleteTransactions();

	}
}