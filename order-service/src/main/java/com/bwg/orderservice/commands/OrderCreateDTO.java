package com.bwg.orderservice.commands;

import java.math.BigDecimal;
import java.util.UUID;

import com.bwg.domains.commands.CreateOrderCommand;
import com.bwg.orderservice.aggregates.OrderStatus;
import com.google.common.base.Strings;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderCreateDTO {

  private String orderId;

  private String itemType;

  private BigDecimal price;

  private String currency;

  public CreateOrderCommand createOrderCommand() {
    return new CreateOrderCommand(
        Strings.isNullOrEmpty(orderId) ? UUID.randomUUID().toString() : orderId,
        itemType,
        price,
        currency,
        String.valueOf(OrderStatus.CREATED));
  }
}
