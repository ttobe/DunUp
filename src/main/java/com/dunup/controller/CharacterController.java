package com.dunup.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dunup.dto.CharacterDetailResponseDto;
import com.dunup.dto.CharacterSearchResponseDto;
import com.dunup.service.CharacterService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CharacterController {

	private final CharacterService characterService;

	@GetMapping("/character/search")
	public String searchCharacter(
		@RequestParam("serverId") String serverId,
		@RequestParam("characterName") String characterName,
		Model model
	) {
		CharacterSearchResponseDto responseDto = characterService.searchCharacter(serverId, characterName);

		model.addAttribute("characters", responseDto.getRows());
		return "searchResult";
	}

	@GetMapping("/character/detail")
	public String getCharacterDetails(
		@RequestParam("serverId") String serverId,
		@RequestParam("characterId") String characterId,
		Model model
	) {
		CharacterDetailResponseDto detailResponse = characterService.getCharacterDetails(serverId, characterId);

		model.addAttribute("characterDetails", detailResponse);

		return "characterDetail";
	}
}
