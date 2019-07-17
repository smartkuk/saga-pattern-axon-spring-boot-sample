package com.bwg.orderservice.aggregates;

import java.math.BigDecimal;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import com.bwg.domains.ItemType;
import com.bwg.domains.commands.CreateOrderCommand;
import com.bwg.domains.commands.UpdateOrderStatusCommand;
import com.bwg.domains.events.OrderCreatedEvent;
import com.bwg.domains.events.OrderUpdatedEvent;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aggregate
@Getter
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
    log.debug("[CreateOrderCommand] 명령을 받았습니다.(command: {})", createOrderCommand);
    log.debug("[OrderCreatedEvent] 이벤트가 발생했습니다.");
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
    log.debug("[OrderCreatedEvent] 이벤트를 받았습니다.(event: {})", orderCreatedEvent);
  }

  @CommandHandler
  protected void on(UpdateOrderStatusCommand updateOrderStatusCommand) {
    log.debug("[UpdateOrderStatusCommand] 명령을 받았습니다.");
    log.debug("[OrderUpdatedEvent] 이벤트가 발생했습니다.");
    AggregateLifecycle.apply(new OrderUpdatedEvent(updateOrderStatusCommand.orderId,
        updateOrderStatusCommand.orderStatus));
  }

  @EventSourcingHandler
  protected void on(OrderUpdatedEvent orderUpdatedEvent) {
    this.orderId = orderId;
    this.orderStatus = OrderStatus.valueOf(orderUpdatedEvent.orderStatus);
    log.debug("[OrderUpdatedEvent] 이벤트를 받았습니다.(event: {})", orderUpdatedEvent);
  }
}
