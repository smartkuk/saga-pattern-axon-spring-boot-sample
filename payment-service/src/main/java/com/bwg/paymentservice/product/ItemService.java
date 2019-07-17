package com.bwg.paymentservice.product;

import java.util.List;

import com.bwg.domains.ItemType;

public interface ItemService {

  /**
   * 아이템 유형과 갯수로 Item 생성후 생성된 갯수를 리턴
   */
  Integer loadItems(ItemType itemType, Integer numberOfItems);

  /**
   * enabled 상태의 재고 Item 1개를 disabled 변경
   */
  Boolean popItem(ItemType itemType);

  /**
   * 현재 재고 현황을 ItemType 별로 갯수 제공
   */
  List<ItemStatistic> getInventories(Boolean enabled);
}
