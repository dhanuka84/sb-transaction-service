package com.sb.transaction.service.web.controllers;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sb.model.StatisticsDto;
import com.sb.model.TransactionDto;
import com.sb.transaction.service.services.TransactionService;

@RunWith(SpringRunner.class)
@WebMvcTest(TransactionController.class)
class TestTransactionControllerTest {

	// test properties
	static final String API_ROOT = "/sb/";

	@MockBean
	TransactionService transactionService;

	@Autowired
	MockMvc mockMvc;

	@Test
	public void getStatistics() throws Exception {

		BigDecimal val1 = new BigDecimal(1.0);
		given(transactionService.statistics()).willReturn(new StatisticsDto(val1, val1, val1, val1, 1l));

		mockMvc.perform(get(API_ROOT + "statistics").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().string(containsString("")));

		then(transactionService).should().statistics();

	}

	@Test
	public void deleteTx() throws Exception {

		// given(transactionService.deleteTransactions());
		mockMvc.perform(delete(API_ROOT + "transactions").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent()).andExpect(content().string(containsString("")));

		then(transactionService).should().deleteTransactions();

	}

	@Test
	public void doTransactionFail() throws Exception {
		OffsetDateTime offsetDT8 = OffsetDateTime.parse("2019-08-31T15:20:30+08:00");
		TransactionDto tx = buildOrderDto(offsetDT8);
		String req = asJsonString(tx);
		System.out.print(req);

		given(transactionService.doTransaction(tx)).willReturn(null);
		mockMvc.perform(post(API_ROOT + "transactions").content(req).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent())
				.andExpect(content().string(containsString("")));


	}

	@Test
	public void doTransaction() throws Exception {
		OffsetDateTime offsetDT8 = OffsetDateTime.now();
		TransactionDto tx = buildOrderDto(offsetDT8);
		String req = asJsonString(tx);
		System.out.print(req);

		given(transactionService.doTransaction(tx)).willReturn(null);
		mockMvc.perform(post(API_ROOT + "transactions").content(req).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
				.andExpect(content().string(containsString("")));

	}

	private TransactionDto buildOrderDto(final OffsetDateTime offsetDT8) {

		return TransactionDto.builder().id(null).timestamp(offsetDT8).amount(new BigDecimal(123.50)).build();
	}

	public static String asJsonString(final Object obj) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
			objectMapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, true);
			objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
			objectMapper.registerModule(new JavaTimeModule());

			return objectMapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}