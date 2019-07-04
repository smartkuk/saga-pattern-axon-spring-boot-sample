package com.bwg.orderservice.services.commands;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import com.bwg.orderservice.aggregates.OrderStatus;
import com.bwg.orderservice.commands.OrderCreateDTO;
import com.progressivecoder.ecommerce.commands.CreateOrderCommand;

@Service
public class OrderCommandServiceImpl implements OrderCommandService {

  /**
   * 명령을 전달하기 위한 명령 게이트웨이
   *
   * @see <a href=
   *      "https://docs.axoniq.io/reference-guide/configuring-infrastructure-components/command-processing/command-dispatching#creating-a-custom-command-gateway">사용자정의
   *      명령 게이트웨이 구현</a>
   */
  private final CommandGateway commandGateway;

  public OrderCommandServiceImpl(CommandGateway commandGateway) {
    this.commandGateway = commandGateway;
  }

  @Override
  public CompletableFuture<String> createOrder(OrderCreateDTO orderCreateDTO) {
    return commandGateway
        .send(new CreateOrderCommand(UUID.randomUUID().toString(), orderCreateDTO.getItemType(),
            orderCreateDTO.getPrice(), orderCreateDTO.getCurrency(),
            String.valueOf(OrderStatus.CREATED)));
  }
}
