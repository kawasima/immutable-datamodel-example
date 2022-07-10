package example.simple;

import example.shared.Address;

import java.time.LocalDate;

public interface Deliverable {
    DeliveringOrder deliver(Address deliveryAddress, LocalDate deliveryDate);
}
