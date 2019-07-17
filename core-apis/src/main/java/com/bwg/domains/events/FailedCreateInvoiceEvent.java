package com.bwg.domains.events;

public class FailedCreateInvoiceEvent {

  public final String orderId;

  public FailedCreateInvoiceEvent(String orderId) {
    this.orderId = orderId;
  }
}
