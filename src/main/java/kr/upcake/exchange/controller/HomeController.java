package kr.upcake.exchange.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.upcake.exchange.dto.CountryDTO;
import kr.upcake.exchange.dto.CurrencyAPIRequestDTO;
import kr.upcake.exchange.dto.CurrencyAPIResponseDTO;
import kr.upcake.exchange.service.ExchangeService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {
	private final ExchangeService exService;
	
	//메인 화면
	@GetMapping(value="/")
	public String home(Model model) {
		CountryDTO source = exService.getSource();
		List<CountryDTO> countries = exService.getCountries();
		String currencies = exService.getCurrencies(countries);
		
		model.addAttribute("source", source);
		model.addAttribute("countries", countries);
		model.addAttribute("currencies", currencies);
		
		return "index";
	}
	
	//수취금액 구하기
	@PostMapping(value="/getReceivingAmount")
	@ResponseBody
	public float getReceivingAmount(Integer remittance, Float rate) {
		
		return remittance * rate;
	}
	
	//환율 요청
	@PostMapping(value="/getExchangeRate")
	@ResponseBody
	public CurrencyAPIResponseDTO getExchangeRate(@RequestBody CurrencyAPIRequestDTO params) {
		
		return exService.getCurrencyAPIRequest(params);
	}
}