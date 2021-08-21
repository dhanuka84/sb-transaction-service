package com.sb.transaction.service.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Version;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;
import org.hibernate.annotations.Type;

import com.sb.model.StatisticsDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter

@NamedNativeQuery(name = "Transaction.findStatistics",
query = "SELECT MIN(amount) AS min, MAX (amount) as max, SUM (amount) as sum, AVG (amount) as avg , count (1) as count FROM transaction where time >= NOW() - INTERVAL 60 SECOND",
resultSetMapping = "Mapping.StatisticsDto")
@SqlResultSetMapping(name = "Mapping.StatisticsDto", classes = @ConstructorResult(targetClass = StatisticsDto.class, columns = {
		@ColumnResult(name = "min",type=BigDecimal.class), @ColumnResult(name = "max",type=BigDecimal.class), @ColumnResult(name = "sum",type=BigDecimal.class),
		@ColumnResult(name = "avg",type=BigDecimal.class), @ColumnResult(name = "count",type=Long.class) }))
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Proxy(lazy = false)
public class Transaction {

	@Id
	/*
	 * @GeneratedValue(generator = "UUID")
	 * 
	 * @GenericGenerator( name = "UUID", strategy = "org.hibernate.id.UUIDGenerator"
	 * )
	 */

	/*
	 * @Type(type="org.hibernate.type.UUIDCharType")
	 * 
	 * @Column(length = 36, columnDefinition = "varchar(36)", updatable = false,
	 * nullable = false )
	 * 
	 * private UUID id;
	 */
	@GeneratedValue(generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long id;

	@Version
	private Long version;

	@CreationTimestamp
	@Column(updatable = false,name="time")
	private Timestamp timestamp;

	private BigDecimal amount;

	@Enumerated(EnumType.STRING)
	private TransactionStatus txStatus = TransactionStatus.NEW;

}
