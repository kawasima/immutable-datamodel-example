package example.simple;

import example.shared.Address;
import example.shared.OrderConstraint;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.EnumSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderTest {
    @Test
    void testDeliverWithNoConstraint() {
        InProgressOrder inProgressOrder = new InProgressOrder("1", EnumSet.noneOf(OrderConstraint.class));
        DeliveringOrder deliveringOrder = inProgressOrder.deliver(new Address("JP", "1234567", "Tokyo", "Suginami-ku"),
                LocalDate.now());
        assertThat(deliveringOrder).hasFieldOrPropertyWithValue("orderId", "1");
    }

    @Test
    void testDeliverWithJPOnly() {
        InProgressOrder inProgressOrder = new InProgressOrder("1", EnumSet.of(OrderConstraint.SHIPPING_JAPAN_ONLY));
        assertThatThrownBy(() -> inProgressOrder.deliver(new Address("US", "1234567", "Tokyo", "Suginami-ku"),
                LocalDate.now()))
                .hasMessageContaining("shipping only for Japan");
    }

    @Test
    void testOrderClose() {
        InProgressOrder inProgressOrder = new InProgressOrder("1", EnumSet.noneOf(OrderConstraint.class));
        DeliveringOrder deliveringOrder = inProgressOrder.deliver(new Address("JP", "1234567", "Tokyo", "Suginami-ku"),
                LocalDate.now());
        assertThat(deliveringOrder.close()).hasFieldOrPropertyWithValue("orderId", "1");
    }
}