package com.dunup.dto;

import lombok.Data;

import java.util.List;

@Data
public class ReinforceSkillDto {
    private String jobId;          // 직업 ID
    private String jobName;        // 직업 이름
    private List<SkillDto> skills; // 스킬 리스트

    @Data
    public static class SkillDto {
        private String skillId;    // 스킬 ID
        private String name;       // 스킬 이름
        private int value;         // 강화 레벨
    }
}
