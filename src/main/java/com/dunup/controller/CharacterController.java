package com.dunup.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dunup.dto.CharacterDetailResponseDto;
import com.dunup.dto.CharacterSearchResponseDto;
import com.dunup.service.CharacterService;
import com.dunup.service.EnchantService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CharacterController {

	private final CharacterService characterService;
	private final EnchantService enchantService;

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
	) throws IOException {
		CharacterDetailResponseDto detailResponse = characterService.getCharacterDetails(serverId, characterId);
		// TODO: 최대 마부와 수치 비교 단순 명성으로 210보다 더 낮으면 그때 수치 값 출력

		// TODO: 경매장 최근 판매 가격 검색
		// TODO: 수치 별 판매 가격 정렬하기

		String result = enchantService.compareEnchantDetails(detailResponse);
		System.out.println(result);
		model.addAttribute("characterDetails", detailResponse);

		return "characterDetail";
	}
}
