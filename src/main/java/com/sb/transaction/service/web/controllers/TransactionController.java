package com.sb.transaction.service.web.controllers;

import java.time.Duration;
import java.time.Instant;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.sb.model.StatisticsDto;
import com.sb.model.TransactionDto;
import com.sb.transaction.service.services.TransactionService;

import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;

@Slf4j
@RequestMapping("/sb/")
@RestController
public class TransactionController {

	private final TransactionService transactionService;

	public TransactionController(final TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@PostMapping("transactions")
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody String doTransaction(@Valid @RequestBody final TransactionDto transactionDto) {
		try {
			final Instant now = Instant.now();
			final Instant time = transactionDto.getTimestamp().toInstant();
			final Duration duration = Duration.between( time , now );
			final Duration limit = Duration.ofSeconds( 60 );
			final Boolean exceededLimit = ( duration.compareTo( limit ) > 0 );
			if (exceededLimit) {
				throw new ResponseStatusException(HttpStatus.NO_CONTENT);
			}
			if (time.isAfter(Instant.now())) {
				throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
			}
			transactionService.doTransaction(transactionDto);
		} catch (final ResponseStatusException ex) {
			log.error("Transaction validation Error "+transactionDto.toString());
			throw ex;
		} catch (final Exception ex) {
			log.error("Error occurred while transaction "+transactionDto.toString());
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
		}

		return StringUtils.EMPTY;
	}

	@GetMapping("statistics")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody StatisticsDto statistics() {
		return transactionService.statistics();
	}

	@DeleteMapping("transactions")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteTransactions(@RequestBody(required = false) final String req) {
		transactionService.deleteTransactions();
	}
}
