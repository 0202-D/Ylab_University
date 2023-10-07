package io.ylab.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
@ToString
public class Transaction {
    AtomicLong id;
    TransactionalType transactionalType;
    BigDecimal sum;
    User user;
}
