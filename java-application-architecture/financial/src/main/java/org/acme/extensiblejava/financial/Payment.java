package org.acme.extensiblejava.financial;

import java.math.BigDecimal;

public class Payment {
	public BigDecimal generateDraft(Payable payable) {
		if (payable.getAuditedAmount() == null) {
			return payable.getAmount();
		} else {
			return payable.getAuditedAmount();
		}
	}
}