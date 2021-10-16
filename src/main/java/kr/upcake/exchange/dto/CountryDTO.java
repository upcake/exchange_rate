package kr.upcake.exchange.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author LBW
 * 국가 정보 DTO
 */

@Getter
@Setter
@AllArgsConstructor
public class CountryDTO {
	private String name;	//국가 이름
	private String code;	//국가 코드
}