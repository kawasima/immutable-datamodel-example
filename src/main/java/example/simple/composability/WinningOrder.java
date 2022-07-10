package example.simple.composability;

import example.shared.Address;
import example.shared.OrderConstraint;
import example.simple.Deliverable;
import example.simple.DeliveringOrder;
import example.simple.Order;

import java.time.LocalDate;
import java.util.EnumSet;

/**
 * 抽選販売商品の注文
 */
public record WinningOrder(
        String orderId,
        EnumSet<OrderConstraint> constraints,
        String productSerialNo) implements Order, Deliverable {

    @Override
    public DeliveringOrder deliver(Address deliverAddress, LocalDate deliverDate) {
        return new DeliveringOrder(orderId,
                constraints,
                deliverAddress,
                deliverDate);
    }
}
