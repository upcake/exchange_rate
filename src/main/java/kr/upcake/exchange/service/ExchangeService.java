package kr.upcake.exchange.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.upcake.exchange.dto.CountryDTO;
import kr.upcake.exchange.dto.CurrencyAPIRequestDTO;
import kr.upcake.exchange.dto.CurrencyAPIResponseDTO;

@Service
public class ExchangeService {
	//송금 국가 설정
	public CountryDTO getSource() {
		CountryDTO usa = new CountryDTO("미국", "USD");
		
		return usa;
	}
	
	//수취 국가 설정
	public List<CountryDTO> getCountries() {
		CountryDTO korea = new CountryDTO("한국", "KRW");
		CountryDTO japan = new CountryDTO("일본", "JPY");
		CountryDTO philippines = new CountryDTO("필리핀", "PHP");
		
		List<CountryDTO> countries = new ArrayList<>();
		countries.add(korea);
		countries.add(japan);
		countries.add(philippines);
		
		return countries;
	}
	
	//API용 currencies 변수 작성
	public String getCurrencies(List<CountryDTO> countries) {
		StringBuilder sb = new StringBuilder();
		for(CountryDTO i : countries) {
			sb.append(i.getCode() + ",");
		}
		String currencies = sb.toString();
		
		return currencies;
	}
	
	//환율 요청 API
	public CurrencyAPIResponseDTO getCurrencyAPIRequest(CurrencyAPIRequestDTO params) {
		String baseURL = "http://apilayer.net/api/live";
		final String ACCESS_KEY = "697130d30742360c6d3c5d3d87ca7dea";

		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseURL)
				.queryParam("access_key", ACCESS_KEY)
				.queryParam("source", params.getSource())
				.queryParam("currencies", params.getCurrencies())
				.queryParam("format", 1);
		
		HttpEntity<?> entity = new HttpEntity<>(headers);
		
		HttpEntity<String> response = new RestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, String.class);
		
		//결과 파싱
		ObjectMapper objectMapper = new ObjectMapper();
		CurrencyAPIResponseDTO result = null;
		try {
			result = objectMapper.readValue(response.getBody(), CurrencyAPIResponseDTO.class);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}