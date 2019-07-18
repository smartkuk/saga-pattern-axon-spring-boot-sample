package com.bwg.orderservice.handler;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bwg.domains.events.OrderCreatedEvent;
import com.bwg.domains.events.OrderUpdatedEvent;
import com.bwg.orderservice.aggregates.OrderStatus;
import com.bwg.orderservice.order.CustomerOrder;
import com.bwg.orderservice.order.CustomerOrderRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class OrderEventHandler {

  @Autowired
  private CustomerOrderRepository repository;

  @EventHandler
  protected void on(OrderCreatedEvent orderCreatedEvent) {
    log.debug("[OrderCreatedEvent] 이벤트를 받았습니다.(event: {})", orderCreatedEvent);
    CustomerOrder order = repository.save(new CustomerOrder(orderCreatedEvent));
    log.debug("새로운 [Order] Entity를 저장했습니다.", order);
  }

  @EventHandler
  protected void on(OrderUpdatedEvent orderUpdatedEvent) {
    log.debug("[OrderUpdatedEvent] 이벤트를 받았습니다.(event: {})", orderUpdatedEvent);
    OrderStatus status = OrderStatus.valueOf(orderUpdatedEvent.orderStatus);
    repository.findById(orderUpdatedEvent.orderId).ifPresent((order) -> {
      order.setOrderStatus(status);
      repository.save(order);
      log.debug("[Order] Entity(ID: {})를 저장했습니다.", orderUpdatedEvent.orderId);
    });
  }
}
