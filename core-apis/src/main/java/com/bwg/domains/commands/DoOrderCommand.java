package com.bwg.domains.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.bwg.domains.ItemType;

public class DoOrderCommand {

  @TargetAggregateIdentifier
  public final String paymentId;

  public final String orderId;

  public final ItemType itemType;

  public DoOrderCommand(String paymentId, String orderId, ItemType itemType) {
    this.paymentId = paymentId;
    this.orderId = orderId;
    this.itemType = itemType;
  }
}
