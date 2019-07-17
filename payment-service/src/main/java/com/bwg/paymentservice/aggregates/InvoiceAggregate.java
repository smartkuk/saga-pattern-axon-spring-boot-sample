package com.bwg.paymentservice.aggregates;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import com.bwg.domains.commands.CreateInvoiceCommand;
import com.bwg.domains.events.InvoiceCreatedEvent;

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
    log.debug("[CreateInvoiceCommand] 명령을 받았습니다.(command: {})", createInvoiceCommand);
    log.debug("[InvoiceCreatedEvent] 이벤트가 발생했습니다.");
    AggregateLifecycle.apply(
        new InvoiceCreatedEvent(createInvoiceCommand.paymentId, createInvoiceCommand.orderId));
  }

  @EventSourcingHandler
  protected void on(InvoiceCreatedEvent invoiceCreatedEvent) {
    this.paymentId = invoiceCreatedEvent.paymentId;
    this.orderId = invoiceCreatedEvent.orderId;
    this.invoiceStatus = InvoiceStatus.PAID;
    log.debug("[InvoiceCreatedEvent] 이벤트를 받았습니다.(event: {})", invoiceCreatedEvent);
  }
}
