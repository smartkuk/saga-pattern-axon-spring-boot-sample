package com.bwg.paymentservice.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bwg.domains.ItemType;

@RestController
@RequestMapping("/items")
public class ItemRestController {

  @Autowired
  private ItemService itemService;

  /**
   * 재고 자동 생성
   *
   * @param itemType
   * @param numberOfItems
   * @return
   */
  @CrossOrigin(origins = "*")
  @RequestMapping(method = RequestMethod.POST, value = "/{type}/{number}")
  public HttpEntity<?> createItems(
      @PathVariable("type") ItemType itemType,
      @PathVariable("number") Integer numberOfItems) {
    return ResponseEntity.ok(itemService.loadItems(itemType, numberOfItems));
  }
}
