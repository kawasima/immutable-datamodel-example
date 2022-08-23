package example.complex;

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
        Order sut = new Order("1", EnumSet.noneOf(OrderConstraint.class));
        assertThat(sut.getStatus()).isEqualTo(OrderStatus.IN_PROGRESS);
        sut.deliver(new Address("JP", "1234567", "Tokyo", "Suginami-ku"),
                LocalDate.now());
        assertThat(sut.getStatus()).isEqualTo(OrderStatus.DELIVERING);
    }

    @Test
    void testDeliverWithJPOnly() {
        Order sut = new Order("1", EnumSet.of(OrderConstraint.SHIPPING_JAPAN_ONLY));
        assertThat(sut.getStatus()).isEqualTo(OrderStatus.IN_PROGRESS);
        assertThatThrownBy(() ->sut.deliver(new Address("US", "1234567", "Tokyo", "Suginami-ku"),
                LocalDate.now()))
                .hasMessageContaining("shipping only for Japan");
    }

}