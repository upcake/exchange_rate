package kr.upcake.exchange.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import kr.upcake.exchange.dto.CurrencyAPIRequestDTO;
import kr.upcake.exchange.dto.CurrencyAPIResponseDTO;

@ExtendWith(SpringExtension.class)
class ExchangeServiceTest {
	@SpyBean
	ExchangeService exService;

	@Test
	void testGetCurrencyAPIRequest() {
		//Given
		CurrencyAPIRequestDTO requestDTO = new CurrencyAPIRequestDTO("USD", "KRW,JPY,PHP");
		
		//When
		CurrencyAPIResponseDTO responseDTO = exService.getCurrencyAPIRequest(requestDTO);
		
		//Then
		assertEquals(responseDTO.isSuccess(), true);
	}
}