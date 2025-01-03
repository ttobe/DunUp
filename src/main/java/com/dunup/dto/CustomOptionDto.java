package com.dunup.dto;

import lombok.Data;

import java.util.List;

@Data
public class CustomOptionDto {
  private double damage;
  private int buff;
  private List<CustomOptionDetailDto> options;
}
