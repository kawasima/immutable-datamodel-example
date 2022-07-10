package example.simple;

import example.shared.OrderConstraint;

import java.util.EnumSet;

public record ClosedOrder(String orderId, EnumSet<OrderConstraint> constraints) implements Order {
}
