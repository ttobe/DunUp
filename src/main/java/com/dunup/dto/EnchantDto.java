package com.dunup.dto;

import lombok.Data;

import java.util.List;

@Data
public class EnchantDto {
  private List<StatusDto> status;
  private List<ReinforceSkillDto> reinforceSkill;
}
