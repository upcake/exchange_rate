package kr.upcake.exchange.dto;

import kr.upcake.exchange.enums.CurrencyEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CurrencyAPIRequestDTO {
	private CurrencyEnum source;
}