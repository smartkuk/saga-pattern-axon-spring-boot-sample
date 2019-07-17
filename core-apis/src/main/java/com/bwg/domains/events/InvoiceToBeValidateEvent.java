package com.bwg.domains.events;

import com.bwg.domains.ItemType;

public class InvoiceToBeValidateEvent {

  public final String paymentId;
  public final String orderId;
  public final ItemType itemType;

  public InvoiceToBeValidateEvent(String paymentId, String orderId, ItemType itemType) {
    this.paymentId = paymentId;
    this.orderId = orderId;
    this.itemType = itemType;
  }
}
