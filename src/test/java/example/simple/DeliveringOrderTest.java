package example.simple;

import am.ik.yavi.core.ConstraintViolationsException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class DeliveringOrderTest {
    @Test
    void validation() {
        assertThatThrownBy(() -> new DeliveringOrder(null, null, null, null))
                .isInstanceOfSatisfying(ConstraintViolationsException.class,
                        ex -> assertThat(ex.violations().size()).isEqualTo(4));
    }
}