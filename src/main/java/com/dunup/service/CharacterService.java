package com.dunup.service;

import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.dunup.dto.CharacterDetailResponseDto;
import com.dunup.dto.CharacterSearchResponseDto;

@Service
public class CharacterService {

	@Value("${neople.api.key}")
	private String apiKey;

	private final WebClient webClient;

	private static final Map<String, String> SERVER_NAME_MAP = Map.of(
		"cain", "카인",
		"diregie", "디레지에",
		"siroco", "시로코",
		"prey", "프레이",
		"casillas", "카시야스",
		"hilder", "힐더",
		"anton", "안톤",
		"bakal", "바칼"
	);

	// 서버 이름을 매핑해주는 메서드
	private String getServerName(String serverId) {
		return SERVER_NAME_MAP.getOrDefault(serverId, serverId); // 기본적으로 serverId 반환
	}

	// WebClient 인스턴스를 생성자 주입 방식으로 주입
	public CharacterService(WebClient.Builder webClientBuilder) {
		this.webClient = webClientBuilder.baseUrl("https://api.neople.co.kr/df").build();
	}

	public CharacterSearchResponseDto searchCharacter(String serverId, String characterName) {
		CharacterSearchResponseDto responseDto = webClient.get()
			.uri(uriBuilder -> uriBuilder.path("/servers/{serverId}/characters")
				.queryParam("characterName", characterName)
				.queryParam("apikey", apiKey)
				.queryParam("wordType", "full")
				.build(serverId))
			.retrieve()
			.bodyToMono(CharacterSearchResponseDto.class)
			.block();
		// 서버 이름 매핑
		Objects.requireNonNull(responseDto).getRows().forEach(row ->
			row.setServerName(getServerName(row.getServerId()))
		);
		return responseDto;
	}

	public CharacterDetailResponseDto getCharacterDetails(String serverId, String characterId) {
		return webClient.get()
			.uri(uriBuilder -> uriBuilder.path("/servers/{serverId}/characters/{characterId}/equip/equipment")
				.queryParam("apikey", apiKey)
				.build(serverId, characterId)) // serverId와 characterId를 경로 변수로 삽입
			.retrieve() // HTTP GET 요청을 보내고 응답을 받음
			.bodyToMono(CharacterDetailResponseDto.class) // 응답을 DTO로 변환
			.block(); // 비동기 방식에서 동기 방식으로 변환 (한 번의 요청/응답을 기다림)
	}
}
