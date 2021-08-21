package com.sb.transaction.service.web.mappers;

import org.mapstruct.Mapper;

import com.sb.model.TransactionDto;
import com.sb.transaction.service.domain.Transaction;

@Mapper(uses = { DateMapper.class }, componentModel = "spring")
public interface TransactionMapper {

	TransactionDto TransactionToDto(Transaction Transaction);

	Transaction dtoToTransaction(TransactionDto dto);

}
