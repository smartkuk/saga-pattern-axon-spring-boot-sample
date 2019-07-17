package com.bwg.domains;

import lombok.Getter;

@Getter
public enum ItemType {

  LAPTOP("LAPTOP"),
  HEADPHONE("HEADPHONE"),
  SMARTPHONE("SMARTPHONE");

  private String value;

  ItemType(String value) {
    this.value = value;
  }
}
