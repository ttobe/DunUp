package com.dunup.dto;

import lombok.Data;

import java.util.List;

@Data
public class FusionOptionDto {
  private List<FusionOptionDetailDto> options;
}
