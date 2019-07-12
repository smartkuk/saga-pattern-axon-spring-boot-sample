package com.bwg.orderservice.services.commands;

import java.util.concurrent.CompletableFuture;

import com.bwg.orderservice.commands.OrderCreateDTO;

public interface OrderCommandService {

  /**
   * 주문 처리
   * 
   * @param orderCreateDTO
   * @return
   */
  public CompletableFuture<String> createOrder(OrderCreateDTO orderCreateDTO);

}
