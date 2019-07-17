package com.bwg.orderservice.services.commands;

import java.util.concurrent.CompletableFuture;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import com.bwg.domains.commands.CreateOrderCommand;
import com.bwg.orderservice.commands.OrderCreateDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
    CreateOrderCommand command = orderCreateDTO.createOrderCommand();
    log.debug("[CreateOrderCommand] 명령을 보냅니다.(command: {})", command);
    return commandGateway.send(command);
  }
}
