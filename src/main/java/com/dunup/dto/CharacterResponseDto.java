package com.dunup.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CharacterResponseDto {
	// Getters and Setters
	private String serverId;
	private String characterId;
	private String characterName;
	private int level;
	private String jobId;
	private String jobGrowId;
	private String jobName;
	private String jobGrowName;
	private int fame;
	private String serverName;

}
