package com.bwg.orderservice.order;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import com.bwg.domains.events.OrderCreatedEvent;
import com.bwg.orderservice.aggregates.ItemType;
import com.bwg.orderservice.aggregates.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "`order`")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Order {

  @Id
  private String id;

  @Enumerated(EnumType.STRING)
  private ItemType itemType;

  private BigDecimal price;

  private String currency;

  @Enumerated(EnumType.STRING)
  private OrderStatus orderStatus;

  public Order(OrderCreatedEvent orderCreatedEvent) {
    id = orderCreatedEvent.orderId;
    itemType = ItemType.valueOf(orderCreatedEvent.itemType);
    price = orderCreatedEvent.price;
    currency = orderCreatedEvent.currency;
    orderStatus = OrderStatus.valueOf(orderCreatedEvent.orderStatus);
  }
}
