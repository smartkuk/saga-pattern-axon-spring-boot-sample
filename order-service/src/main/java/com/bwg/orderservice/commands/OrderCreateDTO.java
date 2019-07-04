package com.bwg.orderservice.commands;

import java.math.BigDecimal;
import java.util.UUID;

import com.bwg.domains.commands.CreateOrderCommand;
import com.bwg.orderservice.aggregates.OrderStatus;

public class OrderCreateDTO {

  private String itemType;

  private BigDecimal price;

  private String currency;

  public String getItemType() {
    return itemType;
  }

  public void setItemType(String itemType) {
    this.itemType = itemType;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public CreateOrderCommand createOrderCommand() {
    return new CreateOrderCommand(
        UUID.randomUUID().toString(),
        itemType,
        price,
        currency,
        String.valueOf(OrderStatus.CREATED));
  }
}
