package com.bwg.domains.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class UpdateOrderStatusCommand {

    @TargetAggregateIdentifier
    public final String orderId;

    public final String orderStatus;

    public UpdateOrderStatusCommand(String orderId, String orderStatus) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
    }
}
