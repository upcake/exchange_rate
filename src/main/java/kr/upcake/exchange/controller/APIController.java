package kr.upcake.exchange.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.upcake.exchange.dto.CurrencyAPIRequestDTO;
import kr.upcake.exchange.dto.CurrencyAPIResponseDTO;
import kr.upcake.exchange.service.ExchangeService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class APIController {
	private final ExchangeService exService;
	
	//수취금액 구하기
	@GetMapping(value="/getReceivingAmount")
	public float getReceivingAmount(Integer remittance, Float rate) {
		
		return remittance * rate;
	}
	
	//환율 요청
	@GetMapping(value="/getExchangeRate")
	public CurrencyAPIResponseDTO getExchangeRate(CurrencyAPIRequestDTO params) {
		
		return exService.getCurrencyAPIRequest(params);
	}
}
