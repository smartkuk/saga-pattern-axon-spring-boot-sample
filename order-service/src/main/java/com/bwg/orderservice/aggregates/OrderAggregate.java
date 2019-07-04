package com.bwg.orderservice.aggregates;

import java.math.BigDecimal;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import com.progressivecoder.ecommerce.commands.CreateOrderCommand;
import com.progressivecoder.ecommerce.commands.UpdateOrderStatusCommand;
import com.progressivecoder.ecommerce.events.OrderCreatedEvent;
import com.progressivecoder.ecommerce.events.OrderUpdatedEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aggregate
public class OrderAggregate {

  @AggregateIdentifier
  private String orderId;

  private ItemType itemType;

  private BigDecimal price;

  private String currency;

  private OrderStatus orderStatus;

  public OrderAggregate() {}

  @CommandHandler
  public OrderAggregate(CreateOrderCommand createOrderCommand) {
    log.debug(">>>Apply CreateOrderCommand");
    AggregateLifecycle
        .apply(new OrderCreatedEvent(createOrderCommand.orderId, createOrderCommand.itemType,
            createOrderCommand.price, createOrderCommand.currency, createOrderCommand.orderStatus));
  }

  @EventSourcingHandler
  protected void on(OrderCreatedEvent orderCreatedEvent) {
    this.orderId = orderCreatedEvent.orderId;
    this.itemType = ItemType.valueOf(orderCreatedEvent.itemType);
    this.price = orderCreatedEvent.price;
    this.currency = orderCreatedEvent.currency;
    this.orderStatus = OrderStatus.valueOf(orderCreatedEvent.orderStatus);
    log.debug(">>>OrderCreatedEvent (orderStatus: {})", orderStatus);
  }

  @CommandHandler
  protected void on(UpdateOrderStatusCommand updateOrderStatusCommand) {
    log.debug(">>>Apply UpdateOrderStatusCommand");
    AggregateLifecycle.apply(new OrderUpdatedEvent(updateOrderStatusCommand.orderId,
        updateOrderStatusCommand.orderStatus));
  }

  @EventSourcingHandler
  protected void on(OrderUpdatedEvent orderUpdatedEvent) {
    this.orderId = orderId;
    this.orderStatus = OrderStatus.valueOf(orderUpdatedEvent.orderStatus);
    log.debug(">>>OrderUpdatedEvent (orderStatus: {})", orderStatus);
  }
}
