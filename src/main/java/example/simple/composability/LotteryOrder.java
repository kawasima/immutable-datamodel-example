package example.simple.composability;

import example.shared.OrderConstraint;
import example.simple.Order;

import java.util.EnumSet;

/**
 * 抽選販売商品の注文
 *
 * @author kawasima
 */
public record LotteryOrder(
        String orderId,
        EnumSet<OrderConstraint> constraints
) implements Order, Winnable {
    @Override
    public WinningOrder win(String productSerialNo) {
        return new WinningOrder(orderId, constraints, productSerialNo);
    }
}
