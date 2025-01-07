package com.dunup.dto;

import java.util.List;

import lombok.Data;

@Data
public class EnchantDto {
	private List<StatusDto> status;
	private List<ReinforceSkillDto> reinforceSkill;
}
