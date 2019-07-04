package com.bwg.shippingservice.aggregates;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import com.bwg.domains.commands.CreateShippingCommand;
import com.bwg.domains.events.OrderShippedEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aggregate
public class ShippingAggregate {

  @AggregateIdentifier
  private String shippingId;

  private String orderId;

  private String paymentId;

  public ShippingAggregate() {}

  @CommandHandler
  public ShippingAggregate(CreateShippingCommand createShippingCommand) {
    log.debug(">>>Apply CreateShippingCommand");
    AggregateLifecycle.apply(new OrderShippedEvent(createShippingCommand.shippingId,
        createShippingCommand.orderId, createShippingCommand.paymentId));
  }

  @EventSourcingHandler
  protected void on(OrderShippedEvent orderShippedEvent) {
    log.debug(">>>OrderShippedEvent");
    this.shippingId = orderShippedEvent.shippingId;
    this.orderId = orderShippedEvent.orderId;
  }
}
