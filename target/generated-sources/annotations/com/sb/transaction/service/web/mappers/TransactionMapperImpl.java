package com.sb.transaction.service.web.mappers;

import com.sb.model.TransactionDto;
import com.sb.model.TransactionDto.TransactionDtoBuilder;
import com.sb.transaction.service.domain.Transaction;
import com.sb.transaction.service.domain.Transaction.TransactionBuilder;
import com.sb.transaction.service.domain.TransactionStatus;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-09-11T21:32:33+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 16.0.1 (Oracle Corporation)"
)
@Component
public class TransactionMapperImpl implements TransactionMapper {

    @Autowired
    private DateMapper dateMapper;

    @Override
    public TransactionDto TransactionToDto(Transaction Transaction) {
        if ( Transaction == null ) {
            return null;
        }

        TransactionDtoBuilder transactionDto = TransactionDto.builder();

        transactionDto.id( Transaction.getId() );
        if ( Transaction.getVersion() != null ) {
            transactionDto.version( Transaction.getVersion().intValue() );
        }
        transactionDto.timestamp( dateMapper.asOffsetDateTime( Transaction.getTimestamp() ) );
        transactionDto.amount( Transaction.getAmount() );
        if ( Transaction.getTxStatus() != null ) {
            transactionDto.txStatus( Transaction.getTxStatus().name() );
        }

        return transactionDto.build();
    }

    @Override
    public Transaction dtoToTransaction(TransactionDto dto) {
        if ( dto == null ) {
            return null;
        }

        TransactionBuilder transaction = Transaction.builder();

        transaction.id( dto.getId() );
        if ( dto.getVersion() != null ) {
            transaction.version( dto.getVersion().longValue() );
        }
        transaction.timestamp( dateMapper.asTimestamp( dto.getTimestamp() ) );
        transaction.amount( dto.getAmount() );
        if ( dto.getTxStatus() != null ) {
            transaction.txStatus( Enum.valueOf( TransactionStatus.class, dto.getTxStatus() ) );
        }

        return transaction.build();
    }
}
