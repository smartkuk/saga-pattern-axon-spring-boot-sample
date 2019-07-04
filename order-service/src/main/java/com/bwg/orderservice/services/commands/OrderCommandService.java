package com.bwg.orderservice.services.commands;

import java.util.concurrent.CompletableFuture;

import com.bwg.orderservice.commands.OrderCreateDTO;

public interface OrderCommandService {

    public CompletableFuture<String> createOrder(OrderCreateDTO orderCreateDTO);

}
