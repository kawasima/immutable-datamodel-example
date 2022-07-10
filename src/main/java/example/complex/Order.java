package example.complex;

import am.ik.yavi.builder.ValidatorBuilder;
import am.ik.yavi.core.ConstraintViolationsException;
import am.ik.yavi.core.Validator;
import example.shared.Address;
import example.shared.OrderConstraint;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.EnumSet;

import static example.shared.OrderConstraint.DELIVERY_WEEKDAY;
import static example.shared.OrderConstraint.SHIPPING_JAPAN_ONLY;

/**
 * 注文を表します。
 *
 * @author kawasima
 */
public class Order {
    private static final Validator<Order> validator = ValidatorBuilder.<Order>of()
            .constraintOnCondition((order, constraintGroup) -> order.getConstraints().contains(SHIPPING_JAPAN_ONLY),
                    c -> c.constraintOnTarget(o -> o.deliveryAddress.country().equals("JP"),
                            SHIPPING_JAPAN_ONLY.getConstraintViolation().name(),
                            SHIPPING_JAPAN_ONLY.getViolationMessage()))
            .constraintOnCondition((order, constraintGroup) -> order.getConstraints().contains(DELIVERY_WEEKDAY),
                    c -> c.constraintOnTarget(o -> !EnumSet.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY)
                                    .contains(o.getDeliveryDate().getDayOfWeek()),
                            DELIVERY_WEEKDAY.getConstraintViolation().name(),
                            DELIVERY_WEEKDAY.getViolationMessage()))
            .build();

    private OrderStatus status;
    private final String orderId;
    private final EnumSet<OrderConstraint> constraints;
    private Address deliveryAddress;
    private LocalDate deliveryDate;

    public Order(String orderId, EnumSet<OrderConstraint> constraints) {
        this.orderId = orderId;
        this.constraints = constraints;
        status = OrderStatus.IN_PROGRESS;
    }

    public void deliver(Address deliveryAddress, LocalDate deliveryDate) {
        // 「手配中」ステータスのときだけ、deliverは実行できる
        if (status != OrderStatus.IN_PROGRESS) {
            throw new IllegalStateException("Order status is not in progress");
        }
        this.deliveryAddress = deliveryAddress;
        this.deliveryDate = deliveryDate;
        status = OrderStatus.DELIVERING;

        // 「配送処理中」ステータスなので、「配送先住所」と「配送希望日」が
        // NULLでないこと、および注文条件を満たしているかをチェックする
        validator.applicative().validate(this)
                .orElseThrow(ConstraintViolationsException::new);
    }

    public void close() {
        if (status != OrderStatus.DELIVERING) {
            throw new IllegalStateException("Order status is not delivering");
        }
        status = OrderStatus.CLOSED;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public String getOrderId() {
        return orderId;
    }

    public EnumSet<OrderConstraint> getConstraints() {
        return constraints;
    }

    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }
}
