package org.acme.extensiblejava.financial;

import java.math.BigDecimal;

public interface Payable {
    BigDecimal getAuditedAmount();

    BigDecimal getAmount();
}