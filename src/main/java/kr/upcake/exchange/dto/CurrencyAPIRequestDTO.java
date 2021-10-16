package kr.upcake.exchange.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CurrencyAPIRequestDTO {
	private String source;
	private String currencies;
}