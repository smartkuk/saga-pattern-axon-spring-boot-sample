package com.bwg.orderservice.aggregates;

import lombok.Getter;

@Getter
public enum OrderStatus {
  CREATED("CREATED"), SHIPPED("SHIPPED"), REJECTED("REJECTED");
  private String value;

  OrderStatus(String value) {
    this.value = value;
  }
}
