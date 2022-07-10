package example.simple.composability;

import example.shared.Address;
import example.shared.OrderConstraint;
import example.simple.DeliveringOrder;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.EnumSet;

import static org.assertj.core.api.Assertions.assertThat;

class LotteryOrderTest {
    @Test
    void test() {
        LotteryOrder lotteryOrder = new LotteryOrder("1", EnumSet.noneOf(OrderConstraint.class));
        // 抽選注文が当選したら…
        WinningOrder winningOrder = lotteryOrder.win("1234");
        DeliveringOrder deliveringOrder = winningOrder.deliver(new Address("JP", "1234567", "Tokyo", "Suginami-ku"),
                LocalDate.now());
        assertThat(deliveringOrder).hasFieldOrPropertyWithValue("orderId", "1");
    }

}