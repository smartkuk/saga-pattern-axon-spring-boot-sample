package com.progressivecoder.paymentmanagement.paymentservice.aggregates;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import com.progressivecoder.ecommerce.commands.CreateInvoiceCommand;
import com.progressivecoder.ecommerce.events.InvoiceCreatedEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aggregate
public class InvoiceAggregate {

  @AggregateIdentifier
  private String paymentId;

  private String orderId;

  private InvoiceStatus invoiceStatus;

  public InvoiceAggregate() {}

  @CommandHandler
  public InvoiceAggregate(CreateInvoiceCommand createInvoiceCommand) {
    log.info(">>>Apply CreateInvoiceCommand");
    AggregateLifecycle.apply(
        new InvoiceCreatedEvent(createInvoiceCommand.paymentId, createInvoiceCommand.orderId));
  }

  @EventSourcingHandler
  protected void on(InvoiceCreatedEvent invoiceCreatedEvent) {
    this.paymentId = invoiceCreatedEvent.paymentId;
    this.orderId = invoiceCreatedEvent.orderId;
    this.invoiceStatus = InvoiceStatus.PAID;
    log.info(">>>InvoiceCreatedEvent (invoiceStatus: {})", invoiceStatus);
  }
}
