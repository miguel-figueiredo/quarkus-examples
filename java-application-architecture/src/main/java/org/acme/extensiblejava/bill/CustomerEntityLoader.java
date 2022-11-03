package org.acme.extensiblejava.bill;

import java.util.List;

public interface CustomerEntityLoader {
	public Customer loadCustomer();
	public List<Bill> loadBills();
}