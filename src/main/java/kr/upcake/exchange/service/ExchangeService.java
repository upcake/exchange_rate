package kr.upcake.exchange.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
import kr.upcake.exchange.enums.CurrencyEnum;

@Service
public class ExchangeService {
	//송금 국가 설정
	public CountryDTO getSource() {
		return new CountryDTO(CurrencyEnum.USD.getLabel(), CurrencyEnum.USD.name());
	}
	
	//수취 국가 설정
	public List<CountryDTO> getCountries() {
		CountryDTO korea = new CountryDTO(CurrencyEnum.KRW.getLabel(), CurrencyEnum.KRW.name());
		CountryDTO japan = new CountryDTO(CurrencyEnum.JPY.getLabel(), CurrencyEnum.JPY.name());
		CountryDTO philippines = new CountryDTO(CurrencyEnum.PHP.getLabel(), CurrencyEnum.PHP.name());
		
		List<CountryDTO> countries = new ArrayList<>();
		countries.add(korea);
		countries.add(japan);
		countries.add(philippines);
		
		return countries;
	}
	
	//API용 currencies 변수 작성
	public String getCurrencies(CurrencyEnum source) {
		CurrencyEnum[] values = CurrencyEnum.values();
		
		String currencies =
			Arrays.stream(values)
				.filter(e -> !e.equals(source))
				.map(Enum::name)
				.collect(Collectors.joining(","));
		
		return currencies;
	}
	
	//환율 요청 API
	public CurrencyAPIResponseDTO getCurrencyAPIRequest(CurrencyAPIRequestDTO params) {
		String baseURL = "http://apilayer.net/api/live";
		String accessKey = "697130d30742360c6d3c5d3d87ca7dea"; 

		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseURL)
				.queryParam("access_key", accessKey)
				.queryParam("source", params.getSource())
				.queryParam("currencies", getCurrencies(params.getSource()))
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