package kr.upcake.exchange.enums;

import lombok.Getter;

@Getter
public enum CurrencyEnum {
	KRW("한국")
	, USD("미국")
	, PHP("필리핀")
	, JPY("일본");
	
	private String label;

	private CurrencyEnum(String label) {
		this.label = label;
	}
}