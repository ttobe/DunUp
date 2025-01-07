package com.dunup.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dunup.dto.MaxEnchantDto;
import com.dunup.service.EnchantService;

@RestController
public class EnchantController {

	@Autowired
	private EnchantService enchantService;  // EnchantService를 주입받음

	// GET 요청이 "/enchantData"로 들어오면 enchantData를 반환
	@GetMapping("/enchantData")
	public List<MaxEnchantDto> getEnchantData() throws IOException {
		return enchantService.getEnchantData(); // EnchantService에서 데이터를 반환
	}
}