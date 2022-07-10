package example.shared;

import am.ik.yavi.core.ConstraintViolation;
import am.ik.yavi.core.ViolationMessage;
import am.ik.yavi.message.SimpleMessageFormatter;

import java.util.Locale;

/**
 * 注文の制約を表現します
 *
 * @author kawasima
 */
public enum OrderConstraint {
    SHIPPING_JAPAN_ONLY("address", "order.shippingJapanOnly", "This order is shipping only for Japan"),
    DELIVERY_WEEKDAY("deliveryTime", "order.deliveryWeekday", "This order can deliver only on weekday");

    private final ConstraintViolation constraintViolation;

    OrderConstraint(String name, String messageKey, String violationDefaultMessage) {
        SimpleMessageFormatter messageFormatter = new SimpleMessageFormatter();
        constraintViolation = new ConstraintViolation(name, messageKey, violationDefaultMessage, new Object[]{}, messageFormatter, Locale.getDefault());
    }

    public ConstraintViolation getConstraintViolation() {
        return constraintViolation;
    }

    public ViolationMessage getViolationMessage() {
        return ViolationMessage.of(constraintViolation.messageKey(), constraintViolation.defaultMessageFormat());
    }
}
