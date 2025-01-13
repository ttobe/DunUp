package com.dunup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnchantComparisonResult {
	private String slotName;
	private int statusDifference;
	private int skillDifference;

}