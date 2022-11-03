package org.acme.extensiblejava.audit;

import java.math.BigDecimal;
import org.acme.extensiblejava.bill.Bill;

public class AuditFacade {
	public BigDecimal audit(Bill bill) {
		BigDecimal amount = bill.getAmount();
		BigDecimal auditedAmount = amount.multiply(new BigDecimal("0.75"));
		return auditedAmount.setScale(2);
	}
}