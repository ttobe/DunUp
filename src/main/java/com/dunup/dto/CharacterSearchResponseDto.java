package com.dunup.dto;

import java.util.List;

public class CharacterSearchResponseDto {
  private List<CharacterResponseDto> rows;

  // Getters and Setters
  public List<CharacterResponseDto> getRows() {
    return rows;
  }

  public void setRows(List<CharacterResponseDto> rows) {
    this.rows = rows;
  }
}
