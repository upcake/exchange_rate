package kr.upcake.exchange.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.Gson;

import kr.upcake.exchange.dto.CountryDTO;

@Controller
public class HomeController {
	@GetMapping(value="/")
	public String home(Model model) {
		//국가 설정
		CountryDTO usa = new CountryDTO("미국", "USD");
		CountryDTO korea = new CountryDTO("한국", "KRW");
		CountryDTO japan = new CountryDTO("일본", "JPY");
		CountryDTO philippines = new CountryDTO("필리핀", "PHP");
		
		List<CountryDTO> countries = new ArrayList<>();
		countries.add(korea);
		countries.add(japan);
		countries.add(philippines);

		//currencies 변수 작성
		StringBuilder sb = new StringBuilder();
		for(CountryDTO i : countries) {
			sb.append(i.getCode() + ",");
		}
		String currencies = sb.toString();
		
		//모델에 추가
		model.addAttribute("source", usa);
		model.addAttribute("countries", countries);
		model.addAttribute("currencies", currencies);
		
		return "index";
	}
	
	@PostMapping(value="/getReceivingAmount")
	@ResponseBody
	public float getReceivingAmount(Integer remittance, Float rate) {
		
		return remittance * rate;
	}
	
	@PostMapping(value="/getExchangeRate")
	@SuppressWarnings("unchecked")
	@ResponseBody
	public Map<String, Object> getExchangeRate(@RequestBody MultiValueMap<String, String> params) {
		String baseURL = "http://apilayer.net/api/live";
		String accessKey = "697130d30742360c6d3c5d3d87ca7dea";

		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseURL)
				.queryParam("access_key", accessKey)
				.queryParam("source", params.get("source"))
				.queryParam("currencies", params.get("currencies"))
				.queryParam("format", 1);
		
		HttpEntity<?> entity = new HttpEntity<>(headers);
		
		HttpEntity<String> response = new RestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, String.class);
		
		Map<String, Object> result = new Gson().fromJson(response.getBody(), Map.class); 
		
		return (Map<String, Object>) result.get("quotes"); 
	}
}