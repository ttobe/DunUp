package com.dunup.controller.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CharacterController {

  // 캐릭터 검색 API
  @GetMapping("/character/search")
  public Map<String, Object> searchCharacter(
      @RequestParam String serverId,
      @RequestParam String characterName) {

    // 임시 데이터로 검색 결과 생성
    Map<String, Object> characterData = new HashMap<>();
    characterData.put("serverId", serverId);
    characterData.put("characterName", characterName);
    characterData.put("characterId", "abc12345"); // 샘플 ID
    characterData.put("level", 100);
    characterData.put("job", "Sword Master");
    characterData.put("guild", "Adventurer Guild");

    // 응답 데이터 반환
    return characterData;
  }
}
