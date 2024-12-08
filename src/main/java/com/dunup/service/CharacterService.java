package com.dunup.service;
import com.dunup.dto.CharacterDetailResponseDto;
import com.dunup.dto.CharacterSearchResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.beans.factory.annotation.Value;

@Service
public class CharacterService {

  @Value("${neople.api.key}")
  private String apiKey;

  private final WebClient webClient;

  // WebClient 인스턴스를 생성자 주입 방식으로 주입
  public CharacterService(WebClient.Builder webClientBuilder) {
    this.webClient = webClientBuilder.baseUrl("https://api.neople.co.kr/df").build();
  }

  public CharacterSearchResponseDto searchCharacter(String serverId, String characterName) {

    // URL 생성 (serverId, characterName을 uriBuilder를 통해 안전하게 처리)
    return webClient.get()
        .uri(uriBuilder -> uriBuilder.path("/servers/{serverId}/characters")
            .queryParam("characterName", characterName)
            .queryParam("apikey", apiKey)
            .build(serverId)) // serverId는 경로 변수로 삽입
        .retrieve() // HTTP GET 요청을 보내고 응답을 받음
        .bodyToMono(CharacterSearchResponseDto.class) // 응답을 DTO로 변환
        .block(); // 비동기 방식에서 동기 방식으로 변환 (한 번의 요청/응답을 기다림)
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
