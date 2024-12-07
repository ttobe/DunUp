package com.dunup.controller;

import com.dunup.dto.CharacterSearchResponseDto;
import com.dunup.service.CharacterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CharacterController {

  private final CharacterService characterService;

  public CharacterController(CharacterService characterService) {
    this.characterService = characterService;
  }

  @GetMapping("/character/search")
  public CharacterSearchResponseDto searchCharacter(
      @RequestParam("serverId") String serverId,
      @RequestParam("characterName") String characterName
  ) {
    return characterService.searchCharacter(serverId, characterName);
  }
}
