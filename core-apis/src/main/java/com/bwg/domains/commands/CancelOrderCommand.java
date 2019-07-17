package com.bwg.domains.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class CancelOrderCommand {

  @TargetAggregateIdentifier
  public final String paymentId;

  public final String orderId;

  public CancelOrderCommand(String paymentId, String orderId) {
    this.paymentId = paymentId;
    this.orderId = orderId;
  }
}
