package com.dunup.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.dunup.dto.CharacterDetailResponseDto;
import com.dunup.dto.EnchantComparisonResult;
import com.dunup.dto.EnchantDto;
import com.dunup.dto.EquipmentDto;
import com.dunup.dto.MaxEnchantDto;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class EnchantService {

	@Autowired
	private ObjectMapper objectMapper;  // ObjectMapper를 자동으로 주입받음.

	private Map<String, MaxEnchantDto> enchantDataMap; // category를 key로 사용하는 Map

	// JSON 데이터를 한 번 로딩하여 enchantDataMap에 저장
	public Map<String, MaxEnchantDto> getEnchantData() throws IOException {
		if (enchantDataMap == null) {
			loadEnchantData(); // 처음 로딩 시 한 번만 로드
		}
		return enchantDataMap; // 로딩된 데이터 반환
	}

	// JSON 파일을 읽어와서 enchantDataMap에 저장
	private void loadEnchantData() throws IOException {
		// resources/static/buffEnchant.json 파일을 읽어옴
		Resource resource = new ClassPathResource("static/buffEnchant.json");

		// JSON 파일을 Map으로 변환하여 필드에 저장
		MaxEnchantDto[] maxEnchantArray = objectMapper.readValue(resource.getFile(), MaxEnchantDto[].class);

		enchantDataMap = new HashMap<>();

		// category를 key로, MaxEnchantDto를 value로 넣어줍니다.
		for (MaxEnchantDto enchant : maxEnchantArray) {
			enchantDataMap.put(enchant.getCategory(), enchant);
		}
	}

	// category와 slotName을 기준으로 enchantData에서 MaxEnchantDto를 찾는 메서드
	private MaxEnchantDto findEnchantByCategory(String category) throws IOException {
		// category를 기준으로 MaxEnchantDto를 찾는 Map에서 직접 검색
		return getEnchantData().get(category); // Map에서 직접 가져옴
	}

	private String compareJob(CharacterDetailResponseDto detailResponse) throws IOException {
		if (isBuffJob(detailResponse.getJobGrowName())) {
			return buffCompare(detailResponse);
		} else {
			return dealCompare(detailResponse);
		}
	}

	private boolean isBuffJob(String jobName) {
		return jobName.contains("크루세이더") ||
			jobName.contains("인챈트리스") ||
			jobName.contains("뮤즈");
	}

	private String getStatusValue(EnchantDto enchant) {
		if (enchant.getStatus() == null || enchant.getStatus().get(0) == null)
			return "0";
		return enchant.getStatus().get(0).getValue();
	}

	private int getSkillValue(EnchantDto enchant) {
		if (enchant.getReinforceSkill() == null)
			return 0;

		if (enchant.getReinforceSkill().isEmpty())
			return 0;

		return enchant.getReinforceSkill()
			.get(0)
			.getSkills()
			.get(0)
			.getValue();
	}

	private EnchantComparisonResult compareEnchants(
		EnchantDto maxEnchantDto, EnchantDto currentEnchant, EquipmentDto equipment) {

		String maxStatusValue = getStatusValue(maxEnchantDto);
		String currentStatusValue = getStatusValue(currentEnchant);
		int maxSkillValue = getSkillValue(maxEnchantDto);
		int currentSkillValue = getSkillValue(currentEnchant);

		int statusDifference = Integer.parseInt(maxStatusValue) - Integer.parseInt(currentStatusValue);
		int skillDifference = maxSkillValue - currentSkillValue;

		EnchantComparisonResult result = new EnchantComparisonResult();
		result.setSlotName(equipment.getSlotName());
		result.setStatusDifference(statusDifference);
		result.setSkillDifference(skillDifference);

		return result;
	}

	private String buffCompare(CharacterDetailResponseDto detailResponse) throws IOException {
		List<EnchantComparisonResult> results = new ArrayList<>();
		for (EquipmentDto equipment : detailResponse.getEquipment()) {
			EnchantDto nowEnchant = equipment.getEnchant();
			MaxEnchantDto maxEnchantDto = findEnchantByCategory(equipment.getSlotName());
			// enchant가 null이 아니면 비교 시작
			if (nowEnchant != null && maxEnchantDto != null) {
				// 해당 장비 자리의 최대 마부 찾기
				EnchantComparisonResult compareResult =
					compareEnchants(maxEnchantDto.getEnchant(), nowEnchant, equipment);
				if (compareResult.getStatusDifference() > 0 || compareResult.getSkillDifference() > 0) {
					results.add(compareResult);
				}
				// TODO: 증가 폭 / 해당 경매장 가격 해서 마지막에 추가하기 이름도!
			}
		}

		return results.toString();
	}

	private String dealCompare(CharacterDetailResponseDto detailResponse) {
		return "Deal comparison for " + detailResponse.getJobName();
	}

	// CharacterDetailResponseDto의 equipment에서 Enchant 비교
	public String compareEnchantDetails(CharacterDetailResponseDto detailResponse) throws IOException {
		return compareJob(detailResponse);
	}

}