package com.dunup.dto;

import lombok.Data;

import java.util.List;

@Data
public class CharacterDetailResponseDto {
  private String serverId;
  private String characterId;
  private String characterName;
  private int level;
  private String jobId;
  private String jobGrowId;
  private String jobName;
  private String jobGrowName;
  private int fame;
  private String adventureName;
  private String guildId;
  private String guildName;
  private List<EquipmentDto> equipment;
}
