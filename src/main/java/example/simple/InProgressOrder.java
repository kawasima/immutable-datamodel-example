package example.simple;

import example.shared.Address;
import example.shared.OrderConstraint;

import java.time.LocalDate;
import java.util.EnumSet;

public record InProgressOrder(
        String orderId,
        EnumSet<OrderConstraint> constraints) implements Order, Deliverable {
    @Override
    public DeliveringOrder deliver(Address deliverAddress, LocalDate deliverDate) {
        return new DeliveringOrder(orderId, constraints, deliverAddress, deliverDate);
    }
}
