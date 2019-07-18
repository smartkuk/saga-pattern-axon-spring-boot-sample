package com.bwg.paymentservice.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventories")
public class InventoryRestController {

  @Autowired
  private ItemService itemService;

  @CrossOrigin(origins = "*")
  @RequestMapping(method = RequestMethod.GET)
  public HttpEntity<?> getEnabledInventories(
      @RequestParam(name = "enabled", defaultValue = "true") Boolean enabled) {
    return ResponseEntity.ok(itemService.getInventories(enabled));
  }
}
