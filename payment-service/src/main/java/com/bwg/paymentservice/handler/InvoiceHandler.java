package com.bwg.paymentservice.handler;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bwg.domains.commands.CancelOrderCommand;
import com.bwg.domains.commands.DoOrderCommand;
import com.bwg.domains.events.InvoiceToBeValidateEvent;
import com.bwg.paymentservice.product.ItemService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class InvoiceHandler {

  @Autowired
  private CommandGateway commandGateway;

  @Autowired
  private ItemService itemService;

  @EventHandler
  protected void on(InvoiceToBeValidateEvent invoiceToBeValidateEvent) {
    log.debug("[InvoiceToBeValidateEvent] 이벤트를 받았습니다.(event: {})", invoiceToBeValidateEvent);
    Boolean isPoped = itemService.popItem(invoiceToBeValidateEvent.itemType);
    log.info("현재 배송 가능한 Item의 Pop 성공여부: [{}]", isPoped);

    if (isPoped) {
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
