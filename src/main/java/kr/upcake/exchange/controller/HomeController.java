package kr.upcake.exchange.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.upcake.exchange.dto.CountryDTO;
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
		
		model.addAttribute("source", source);
		model.addAttribute("countries", countries);
		
		return "index";
	}
}