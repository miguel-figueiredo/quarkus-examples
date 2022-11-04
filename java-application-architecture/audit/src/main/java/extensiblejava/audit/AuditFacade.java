package extensiblejava.audit;

import java.math.BigDecimal;

public interface AuditFacade {
	public BigDecimal audit(Auditable auditable);
}