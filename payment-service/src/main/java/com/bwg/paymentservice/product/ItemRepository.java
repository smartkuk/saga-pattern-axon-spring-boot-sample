package com.bwg.paymentservice.product;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bwg.domains.ItemType;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

  @Query("SELECT CASE WHEN COUNT(i) > 0 THEN true ELSE false END FROM Item i WHERE i.enabled = :enabled")
  boolean enabledItemByStatus(@Param("enabled") Boolean enabled);

  Optional<Item> findFirstByItemTypeAndEnabledOrderByIdDesc(ItemType itemType, Boolean enabled);

  Long countByItemType(ItemType itemType);

  Long countByItemTypeAndEnabled(ItemType itemType, Boolean enabled);
}
