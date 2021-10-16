package kr.upcake.exchange.dto;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrencyAPIResponseDTO {
	private boolean success;
	private String terms;
	private String privacy;
	private Integer timestamp;
	private String source;
	private Map<String, String> quotes;
}
