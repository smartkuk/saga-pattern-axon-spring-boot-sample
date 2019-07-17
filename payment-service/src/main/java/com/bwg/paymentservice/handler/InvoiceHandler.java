package com.bwg.paymentservice.handler;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bwg.domains.commands.CancelOrderCommand;
import com.bwg.domains.commands.DoOrderCommand;
import com.bwg.domains.events.InvoiceToBeValidateEvent;
import com.bwg.paymentservice.product.ItemRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class InvoiceHandler {

  @Autowired
  private CommandGateway commandGateway;

  @Autowired
  private ItemRepository itemRepository;

//  @EventHandler
  @EventSourcingHandler
  protected void on(InvoiceToBeValidateEvent invoiceToBeValidateEvent) {

    log.debug("[InvoiceToBeValidateEvent] 이벤트를 받았습니다.(event: {})", invoiceToBeValidateEvent);

    if (itemRepository.enabledItemByStatus(true)) {
      log.debug("[DoOrderCommand] 명령을 보냅니다.");
      commandGateway.send(new DoOrderCommand(invoiceToBeValidateEvent.paymentId,
          invoiceToBeValidateEvent.orderId, invoiceToBeValidateEvent.itemType));
    } else {
      log.debug("[CancelOrderCommand] 명령을 보냅니다.");
      commandGateway.send(new CancelOrderCommand(invoiceToBeValidateEvent.paymentId,
          invoiceToBeValidateEvent.orderId));
    }
  }
}
