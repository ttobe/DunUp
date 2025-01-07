package com.dunup.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.dunup.dto.CharacterDetailResponseDto;
import com.dunup.dto.EnchantDto;
import com.dunup.dto.EquipmentDto;
import com.dunup.dto.MaxEnchantDto;
import com.dunup.dto.StatusDto;
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
	public MaxEnchantDto findEnchantByCategory(String category) throws IOException {
		// category를 기준으로 MaxEnchantDto를 찾는 Map에서 직접 검색
		return getEnchantData().get(category); // Map에서 직접 가져옴
	}

	// CharacterDetailResponseDto의 equipment에서 Enchant 비교
	public String compareEnchantDetails(CharacterDetailResponseDto detailResponse) throws IOException {
		StringBuilder result = new StringBuilder();

		// equipment 목록에서 하나씩 확인
		for (EquipmentDto equipment : detailResponse.getEquipment()) {
			EnchantDto enchant = equipment.getEnchant(); // EnchantDto

			// enchant가 null이 아니면 비교 시작
			if (enchant != null) {
				// 해당 장비 자리의 최대 마부 찾기
				// category는 slotName을 기준으로 가져오기
				String slotName = equipment.getSlotName();
				MaxEnchantDto maxEnchantDto = findEnchantByCategory(slotName);

				// 최대 마부를 찾았다면
				if (maxEnchantDto != null) {
					// 최대 마부의 지능 값
					StatusDto maxStatus = findStatusByName(maxEnchantDto.getEnchant(), "지능");
					String maxEnchantIntelligence = maxStatus != null ? maxStatus.getValue() : "0";
					System.out.println("최대 마부 지능 값: " + maxEnchantIntelligence);

					// 현재 마부의 지능 값
					StatusDto nowStatus = findStatusByName(enchant, "지능");
					String nowStatusIntelligence = nowStatus != null ? nowStatus.getValue() : "0";
					System.out.println("현재 마부 지능 값: " + nowStatusIntelligence);

					// 비교 후, 값이 낮으면 결과에 추가
					if (Integer.parseInt(nowStatusIntelligence) < Integer.parseInt(maxEnchantIntelligence)) {
						result.append(equipment.getSlotName())
							.append(", ");
					} else {
						System.out.println("마부가 낮지 않습니다..");
					}
				}
			}
		}

		return result.toString();
	}

	// StatusDto에서 특정 name을 기준으로 StatusDto를 찾는 유틸리티 메서드
	private StatusDto findStatusByName(EnchantDto enchant, String name) {
		for (StatusDto status : enchant.getStatus()) {
			if (status.getName().equals(name)) {
				return status;
			}
		}
		return null; // 해당하는 Status가 없으면 null 반환
	}

}