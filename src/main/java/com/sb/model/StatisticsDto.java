package com.sb.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class StatisticsDto {

	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long count;

	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal sum;

	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal avg;

	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal max;

	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal min;

	public StatisticsDto(BigDecimal sum, BigDecimal avg, BigDecimal max, BigDecimal min,Long count) {
		super();
		this.count = count;
		this.sum = sum.setScale(2, RoundingMode.HALF_UP);
		this.avg = avg.setScale(2, RoundingMode.HALF_UP);
		this.max = max.setScale(2, RoundingMode.HALF_UP);
		this.min = min.setScale(2, RoundingMode.HALF_UP);

	}
	
	
}
