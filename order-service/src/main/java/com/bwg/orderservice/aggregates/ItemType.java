package com.bwg.orderservice.aggregates;

import lombok.Getter;

@Getter
public enum ItemType {

  LAPTOP("LAPTOP"), HEADPHONE("HEADPHONE"), SMARTPHONE("SMARTPHONE");

  private String value;

  ItemType(String value) {
    this.value = value;
  }
}
