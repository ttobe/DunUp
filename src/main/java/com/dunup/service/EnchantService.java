package com.dunup.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.dunup.dto.MaxEnchantDto;
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
}