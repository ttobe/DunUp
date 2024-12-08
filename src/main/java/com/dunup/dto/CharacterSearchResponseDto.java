package com.dunup.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CharacterSearchResponseDto {
  // Getters and Setters
  private List<CharacterResponseDto> rows;

}
