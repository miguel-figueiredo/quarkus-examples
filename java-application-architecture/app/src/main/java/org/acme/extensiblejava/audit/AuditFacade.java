package org.acme.extensiblejava.audit;

import java.math.BigDecimal;
import org.acme.extensiblejava.bill.Bill;

public interface AuditFacade {
	public BigDecimal audit(Bill bill);
}