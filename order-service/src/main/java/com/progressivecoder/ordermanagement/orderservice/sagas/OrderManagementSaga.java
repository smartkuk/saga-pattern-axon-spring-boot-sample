package com.progressivecoder.ordermanagement.orderservice.sagas;

import java.util.UUID;

import javax.inject.Inject;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;

import com.progressivecoder.ecommerce.commands.CreateInvoiceCommand;
import com.progressivecoder.ecommerce.commands.CreateShippingCommand;
import com.progressivecoder.ecommerce.commands.UpdateOrderStatusCommand;
import com.progressivecoder.ecommerce.events.InvoiceCreatedEvent;
import com.progressivecoder.ecommerce.events.OrderCreatedEvent;
import com.progressivecoder.ecommerce.events.OrderShippedEvent;
import com.progressivecoder.ecommerce.events.OrderUpdatedEvent;
import com.progressivecoder.ordermanagement.orderservice.aggregates.OrderStatus;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Saga
public class OrderManagementSaga {

  @Inject
  private transient CommandGateway commandGateway;

  @StartSaga
  @SagaEventHandler(associationProperty = "orderId")
  public void handle(OrderCreatedEvent orderCreatedEvent) {

    log.info(">>>Handle OrderCreatedEvent");

    String paymentId = UUID.randomUUID().toString();

    // associate Saga
    SagaLifecycle.associateWith("paymentId", paymentId);

    log.info(">>>Register paymentId({})", paymentId);

    System.out.println("order id" + orderCreatedEvent.orderId);

    // send the commands
    commandGateway.send(new CreateInvoiceCommand(paymentId, orderCreatedEvent.orderId));
  }

  @SagaEventHandler(associationProperty = "paymentId")
  public void handle(InvoiceCreatedEvent invoiceCreatedEvent) {
    String shippingId = UUID.randomUUID().toString();

    System.out.println("Saga continued");

    // associate Saga with shipping
    SagaLifecycle.associateWith("shipping", shippingId);

    // send the create shipping command
    commandGateway.send(new CreateShippingCommand(shippingId, invoiceCreatedEvent.orderId,
        invoiceCreatedEvent.paymentId));
  }

  @SagaEventHandler(associationProperty = "orderId")
  public void handle(OrderShippedEvent orderShippedEvent) {
    log.info(">>>Handle OrderShippedEvent");
    commandGateway.send(new UpdateOrderStatusCommand(orderShippedEvent.orderId,
        String.valueOf(OrderStatus.SHIPPED)));
  }

  @SagaEventHandler(associationProperty = "orderId")
  public void handle(OrderUpdatedEvent orderUpdatedEvent) {
    SagaLifecycle.end();
    log.info(">>>Handle OrderUpdatedEvent (Saga Lifecycle End)");
  }
}
