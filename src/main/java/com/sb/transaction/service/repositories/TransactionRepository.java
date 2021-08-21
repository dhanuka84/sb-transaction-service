package com.sb.transaction.service.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sb.model.StatisticsDto;
import com.sb.transaction.service.domain.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	@Query(nativeQuery = true)
	Optional<StatisticsDto> findStatistics();
}
