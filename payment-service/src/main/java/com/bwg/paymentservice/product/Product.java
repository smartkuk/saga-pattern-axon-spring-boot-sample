package com.bwg.paymentservice.product;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.bwg.domains.events.OrderCreatedEvent;

import lombok.NoArgsConstructor;

@Entity(name = "product")
//@Getter
//@Setter
@NoArgsConstructor
//@AllArgsConstructor
//@EqualsAndHashCode
public class Product {

  @Id
  private String id;

//  @Enumerated(EnumType.STRING)
//  private ItemType itemType;

  private BigDecimal price;

  private String currency;

//  @Enumerated(EnumType.STRING)
//  private OrderStatus orderStatus;

  public Product(OrderCreatedEvent orderCreatedEvent) {
    id = orderCreatedEvent.orderId;
//    itemType = ItemType.valueOf(orderCreatedEvent.itemType);
    price = orderCreatedEvent.price;
    currency = orderCreatedEvent.currency;
//    orderStatus = OrderStatus.valueOf(orderCreatedEvent.orderStatus);
  }
}
