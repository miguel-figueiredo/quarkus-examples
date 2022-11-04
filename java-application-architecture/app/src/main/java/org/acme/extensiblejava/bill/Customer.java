package org.acme.extensiblejava.bill;

import java.util.List;

public class Customer {
	private CustomerEntityLoader loader;
	private Integer customerId;
	private Name name;
	private List<Bill> bills;

	public static Customer loadCustomer(CustomerEntityLoader loader) {
		return loader.loadCustomer();
	}

	public Customer(Integer customerId, Name name, CustomerEntityLoader loader) {
		this.customerId = customerId;
		this.name = name;
		this.loader = loader;
	}

	public List<Bill> getBills() {
		if (this.bills == null) {
			this.bills = loader.loadBills();
		}
		return this.bills;
	}

	public Name getName() { return this.name; }
}