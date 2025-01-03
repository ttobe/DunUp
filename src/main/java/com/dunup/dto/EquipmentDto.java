package com.dunup.dto;

import lombok.Data;

import java.util.List;

@Data
public class EquipmentDto {
  private String slotId;
  private String slotName;
  private String itemId;
  private String itemName;
  private String itemTypeId;
  private String itemType;
  private String itemTypeDetailId;
  private String itemTypeDetail;
  private int itemAvailableLevel;
  private String itemRarity;
  private String setItemId;
  private String setItemName;
  private int reinforce;
  private String itemGradeName;
  private EnchantDto enchant;
  private String amplificationName;
  private int refine;
  private AsrahanOptionDto asrahanOption;
  private FusionOptionDto fusionOption;
  private UpgradeInfoDto upgradeInfo;
  private CustomOptionDto customOption;
}
