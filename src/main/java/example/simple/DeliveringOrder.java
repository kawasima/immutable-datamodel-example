package example.simple;

import am.ik.yavi.arguments.Arguments4;
import am.ik.yavi.arguments.Arguments4Validator;
import am.ik.yavi.builder.ArgumentsValidatorBuilder;
import example.shared.Address;
import example.shared.OrderConstraint;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Objects;

import static example.shared.OrderConstraint.DELIVERY_WEEKDAY;
import static example.shared.OrderConstraint.SHIPPING_JAPAN_ONLY;

public record DeliveringOrder(
        String orderId,
        EnumSet<OrderConstraint> constraints,
        Address deliveryAddress,
        LocalDate deliveryDate
) implements Order, OrderClosable {
    private static final Arguments4Validator<String, EnumSet<OrderConstraint>, Address, LocalDate, DeliveringOrder> validator = ArgumentsValidatorBuilder.of(DeliveringOrder::new)
            .builder(b -> b
                    ._string(Arguments4::arg1, "orderId", c -> c.notBlank())
                    ._collection(Arguments4::arg2, "orderConstraint", c -> c.notNull())
                    ._object(Arguments4::arg3, "address", c -> c.notNull())
                    ._localDate(Arguments4::arg4, "deliveryDate", c -> c.notNull())
                    .constraintOnCondition((args, constraintGroup) -> Objects.requireNonNullElse(args.arg2(), Collections.emptySet()).contains(SHIPPING_JAPAN_ONLY),
                            c -> c.constraintOnTarget(args -> args.arg3() != null && args.arg3().country().equals("JP"),
                                    SHIPPING_JAPAN_ONLY.getConstraintViolation().name(),
                                    SHIPPING_JAPAN_ONLY.getViolationMessage()))
                    .constraintOnCondition((args, constraintGroup) -> Objects.requireNonNullElse(args.arg2(), Collections.emptySet()).contains(DELIVERY_WEEKDAY),
                            c -> c.constraintOnTarget(args -> args.arg4() != null && !EnumSet.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY).contains(args.arg4().getDayOfWeek()),
                                    DELIVERY_WEEKDAY.getConstraintViolation().name(),
                                    DELIVERY_WEEKDAY.getViolationMessage())))
            .build();

    public DeliveringOrder {
        validator.lazy().validated(orderId, constraints, deliveryAddress, deliveryDate);
    }

    @Override
    public ClosedOrder close() {
        return new ClosedOrder(orderId(), constraints());
    }
}
