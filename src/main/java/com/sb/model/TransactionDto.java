package com.sb.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDto {

	/*
	 * @Null private UUID id = null;
	 */
	@Null
	private Long id = null;

	@Null
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Integer version = null;

	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ", shape = JsonFormat.Shape.STRING)
	private OffsetDateTime timestamp;

	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal amount;

	@Null
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private String txStatus;
}
