package com.dunup.service;

import java.io.IOException;
import java.util.List;

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
	private ObjectMapper objectMapper;  // ObjectMapper 자동 주입

	private List<MaxEnchantDto> enchantData;  // MaxEnchantDto 리스트를 저장

	// JSON 데이터를 한 번 로딩하여 enchantData에 저장
	public List<MaxEnchantDto> getEnchantData() throws IOException {
		if (enchantData == null) {
			loadEnchantData(); // 처음 로딩 시 한 번만 로드
		}
		return enchantData; // 로딩된 데이터 반환
	}

	// JSON 파일을 읽어와서 enchantData에 저장
	private void loadEnchantData() throws IOException {
		// resources/static/buffEnchant.json 파일을 읽어옴
		Resource resource = new ClassPathResource("static/buffEnchant.json");

		// JSON 파일을 List<MaxEnchantDto> 형태로 변환하여 필드에 저장
		enchantData = objectMapper.readValue(resource.getFile(),
			objectMapper.getTypeFactory().constructCollectionType(List.class, MaxEnchantDto.class));
	}

	// category와 slotName을 기준으로 enchantData에서 MaxEnchantDto를 찾는 메서드
	public MaxEnchantDto findEnchantByCategory(String category) throws IOException {
		for (MaxEnchantDto maxEnchantDto : this.getEnchantData()) {
			if (maxEnchantDto.getCategory().equals(category)) {
				return maxEnchantDto;
			}
		}
		return null; // 찾지 못한 경우 null 반환
	}

	// CharacterDetailResponseDto의 equipment에서 Enchant 비교
	public String compareEnchantDetails(CharacterDetailResponseDto detailResponse) throws IOException {
		StringBuilder result = new StringBuilder();

		for (EquipmentDto equipment : detailResponse.getEquipment()) {
			EnchantDto enchant = equipment.getEnchant(); // EnchantDto

			// TODO: 직업 별 나누기
			// 일단 지능 기준으로만
			if (enchant != null) {
				// 해당 장비자리의 최대 마부 찾기
				// 0 힘 1 지능 2 체력 3 정신력 4 명성
				System.out.println(equipment.getSlotName());
				MaxEnchantDto maxEnchantDto = findEnchantByCategory(equipment.getSlotName());
				if (maxEnchantDto != null) {
					StatusDto maxStatus = maxEnchantDto.getEnchant().getStatus().get(1);
					String maxEnchantIntelligence = maxStatus.getValue();
					System.out.println("최대 마부 지능 값: " + maxEnchantIntelligence);
					StatusDto nowStatus = enchant.getStatus().get(1);
					String nowStatusIntelligence = nowStatus.getValue();
					System.out.println("현재 마부 지능 값: " + nowStatusIntelligence);

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

}