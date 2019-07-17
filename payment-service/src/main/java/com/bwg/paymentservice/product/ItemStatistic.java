package com.bwg.paymentservice.product;

import com.bwg.domains.ItemType;

import lombok.Data;

@Data
public class ItemStatistic {
  private final ItemType itemType;
  private final Integer numberOfItems;
  private final Integer numberOfEnabledItems;
  private final Integer numberOfDisabledItems;
}
