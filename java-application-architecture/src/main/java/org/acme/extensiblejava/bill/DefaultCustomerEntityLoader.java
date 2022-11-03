package org.acme.extensiblejava.bill;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.acme.extensiblejava.bill.data.BillDataBean;
import org.acme.extensiblejava.bill.data.BillDb;
import org.acme.extensiblejava.bill.data.CustomerDataBean;

public class DefaultCustomerEntityLoader implements CustomerEntityLoader {
	private Integer custId;

	public DefaultCustomerEntityLoader(Integer custId) {
		this.custId = custId;
	}
	public Customer loadCustomer() {
		CustomerDataBean customer = BillDb.getCustomer(custId);
		return new Customer(this.custId, new Name(customer.getFirstName(), customer.getLastName()), this);
	}

	public List loadBills() {
		Iterator billBeans = BillDb.getBills(this.custId).iterator();

		ArrayList bills = new ArrayList();
		while (billBeans.hasNext()) {
			BillDataBean billBean = (BillDataBean) billBeans.next();
			Bill b = new Bill(billBean);
			bills.add(b);
		}
		return bills;
	}
}