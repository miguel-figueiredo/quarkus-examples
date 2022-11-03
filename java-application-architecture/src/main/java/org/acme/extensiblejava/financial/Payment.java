package org.acme.extensiblejava.financial;

import java.math.BigDecimal;
import org.acme.extensiblejava.bill.Bill;

public class Payment {
	public BigDecimal generateDraft(Bill bill) {
		if (bill.getAuditedAmount() == null) {
			return bill.getAmount();
		} else {
			return bill.getAuditedAmount();
		}
	}
}