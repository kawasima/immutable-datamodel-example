package example.simple;

import example.shared.OrderConstraint;

import java.util.EnumSet;

public interface Order {
    String orderId();
    EnumSet<OrderConstraint> constraints();
}
