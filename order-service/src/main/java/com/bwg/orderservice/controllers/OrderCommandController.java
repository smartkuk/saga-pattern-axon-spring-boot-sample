package com.bwg.orderservice.controllers;

import java.util.concurrent.CompletableFuture;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bwg.orderservice.commands.OrderCreateDTO;
import com.bwg.orderservice.services.commands.OrderCommandService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api/orders")
@Api(value = "Order Commands", description = "Order Commands Related Endpoints",
    tags = "Order Commands")
public class OrderCommandController {

  private OrderCommandService orderCommandService;

  public OrderCommandController(OrderCommandService orderCommandService) {
    this.orderCommandService = orderCommandService;
  }

  @ApiOperation(value = "The Endpoint that start saga pattern is API.")
  @PostMapping
  public CompletableFuture<String> createOrder(@RequestBody OrderCreateDTO orderCreateDTO) {
    return orderCommandService.createOrder(orderCreateDTO);
  }
}
