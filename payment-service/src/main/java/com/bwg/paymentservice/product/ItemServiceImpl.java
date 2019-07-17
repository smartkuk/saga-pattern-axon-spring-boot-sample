package com.bwg.paymentservice.product;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;

import com.bwg.domains.ItemType;
import com.google.common.collect.Lists;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ItemServiceImpl implements ItemService {

  private final ItemRepository itemRepository;

  @Override
  public Integer loadItems(@Nullable ItemType itemType, @NotNull Integer numberOfItems) {

    Integer numberOfSavedItems = 0;
    List<Item> items = Lists.newArrayList();

    for (int i = 0; i < numberOfItems; i++) {
      items.add(new Item(itemType, true));
      if (i % 500 == 0) {
        numberOfSavedItems += itemRepository.saveAll(items).size();
        items.clear();
      }
    }

    if (items != null && !items.isEmpty()) {
      numberOfSavedItems += itemRepository.saveAll(items).size();
    }

    return numberOfSavedItems;
  }

  @Override
  public Boolean popItem(ItemType itemType) {

    Optional<Item> item = itemRepository.findFirstByItemTypeAndEnabledOrderByIdDesc(itemType, true);
    if (item.isPresent()) {
      Item toBeSaved = item.get();
      toBeSaved.setEnabled(false);
      itemRepository.save(toBeSaved);
      return true;
    }

    return false;
  }

  @Override
  public List<ItemStatistic> getInventories(Boolean enabled) {

    List<ItemStatistic> itemStatisticList = Lists.newArrayList();

    ItemType[] itemTypes =
        new ItemType[] { ItemType.LAPTOP, ItemType.HEADPHONE, ItemType.SMARTPHONE };

    for (ItemType itemType : itemTypes) {
      ItemStatistic statistic =
          new ItemStatistic(itemType, itemRepository.countByItemType(itemType).intValue(),
              itemRepository.countByItemTypeAndEnabled(itemType, true).intValue(),
              itemRepository.countByItemTypeAndEnabled(itemType, false).intValue());
      itemStatisticList.add(statistic);
    }

    return itemStatisticList;
  }
}
