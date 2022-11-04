package org.acme.extensiblejava.bill;


import org.acme.extensiblejava.bill.data.BillDataBean;
import org.acme.extensiblejava.bill.data.BillDb;

public class DefaultBillEntityLoader implements BillEntityLoader {

	private Integer billId;

	public DefaultBillEntityLoader(Integer billId) {
		this.billId = billId;
	}

	public Bill loadBill() {
		BillDataBean billBean = BillDb.getBill(this.billId);
		return new Bill(billBean);
	}
}