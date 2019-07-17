package com.bwg.orderservice.sagas;

import java.util.UUID;

import javax.inject.Inject;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;

import com.bwg.domains.commands.CreateInvoiceCommand;
import com.bwg.domains.commands.CreateShippingCommand;
import com.bwg.domains.commands.UpdateOrderStatusCommand;
import com.bwg.domains.events.InvoiceCreatedEvent;
import com.bwg.domains.events.OrderCreatedEvent;
import com.bwg.domains.events.OrderShippedEvent;
import com.bwg.domains.events.OrderUpdatedEvent;
import com.bwg.orderservice.aggregates.OrderStatus;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Saga
public class OrderManagementSaga {

  @Inject
  private transient CommandGateway commandGateway;

  @StartSaga
  @SagaEventHandler(associationProperty = "orderId")
  public void handle(OrderCreatedEvent orderCreatedEvent) {

    log.debug("Saga process가 [OrderCreatedEvent] 이벤트를 통해 시작 됩니다.");
    log.debug("[OrderCreatedEvent] 이벤트를 받았습니다.(event: {})", orderCreatedEvent);

    String paymentId = UUID.randomUUID().toString();

    // associate Saga
    SagaLifecycle.associateWith("paymentId", paymentId);

    log.debug("paymentId({})를 SAGA Lifecycle에 등록합니다.", paymentId);

    CreateInvoiceCommand command = new CreateInvoiceCommand(paymentId, orderCreatedEvent.orderId);

    log.debug("[CreateInvoiceCommand] 명령을 보냅니다.(command: {})", command);

    commandGateway.send(command);
  }

  @SagaEventHandler(associationProperty = "paymentId")
  public void handle(InvoiceCreatedEvent invoiceCreatedEvent) {

    log.debug("[InvoiceCreatedEvent] 이벤트를 받았습니다.(event: {})", invoiceCreatedEvent);

    String shippingId = UUID.randomUUID().toString();

    SagaLifecycle.associateWith("shipping", shippingId);

    log.debug("shippingId({})를 SAGA Lifecycle에 등록합니다.", shippingId);

    CreateShippingCommand command =
        new CreateShippingCommand(shippingId, invoiceCreatedEvent.orderId,
            invoiceCreatedEvent.paymentId);

    log.debug("[CreateShippingCommand] 명령을 보냅니다.(command: {})", command);

    commandGateway.send(command);
  }

  @SagaEventHandler(associationProperty = "orderId")
  public void handle(OrderShippedEvent orderShippedEvent) {

    log.debug("[OrderShippedEvent] 이벤트를 받았습니다.(event: {})", orderShippedEvent);

    UpdateOrderStatusCommand command = new UpdateOrderStatusCommand(orderShippedEvent.orderId,
        String.valueOf(OrderStatus.SHIPPED));

    log.debug("[CreateShippingCommand] 명령을 보냅니다.(command: {})", command);

    commandGateway.send(command);
  }

  @SagaEventHandler(associationProperty = "orderId")
  public void handle(OrderUpdatedEvent orderUpdatedEvent) {
    log.debug("Saga process가 [OrderUpdatedEvent] 이벤트를 통해 종료 됩니다.");
    SagaLifecycle.end();
  }
}
