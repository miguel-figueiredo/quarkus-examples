package org.acme.extensiblejava.bill;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.util.Iterator;
import org.acme.extensiblejava.bill.data.BillDataBean;
import org.junit.jupiter.api.Test;

public class BillTest {

    @Test
    public void testCustomerLoader() {
        Customer cust = Customer.loadCustomer(new DefaultCustomerEntityLoader(new Integer(1)));
        assertNotNull(cust.getName());

        Iterator bills = cust.getBills().iterator();
        while (bills.hasNext()) {
            assertNotNull(bills.next());
        }
    }

    @Test
    public void testBillLoader() {
        Bill bill = Bill.loadBill(new BillEntityLoader() {
            public Bill loadBill() {
                return new Bill(new BillDataBean(new Integer(1), new Integer(1), "ONE", new BigDecimal("25.00"), null, null));
            }
        });
        assertNotNull(bill);
    }

    @Test
    public void testAudit() {
        Bill bill = Bill.loadBill(new BillEntityLoader() {
            public Bill loadBill() {
                return new Bill(new BillDataBean(new Integer(1), new Integer(1), "ONE", new BigDecimal("25.00"), null, null));
            }
        });
        bill.audit();
        BigDecimal auditedAmount = bill.getAuditedAmount();
        assertEquals(new BigDecimal("18.75"), auditedAmount);
    }

    @Test
    public void testPay() {
        Bill bill = Bill.loadBill(new BillEntityLoader() {
            public Bill loadBill() {
                return new Bill(new BillDataBean(new Integer(1), new Integer(1), "ONE", new BigDecimal("25.00"), null, null));
            }
        });
        bill.pay();
        assertEquals(bill.getPaidAmount(), bill.getAmount());
    }

    @Test
    public void testAuditAfterPay() {
        Bill bill = Bill.loadBill(new BillEntityLoader() {
            public Bill loadBill() {
                return new Bill(new BillDataBean(new Integer(1), new Integer(1), "ONE", new BigDecimal("25.00"), null, null));
            }
        });
        bill.pay();
        BigDecimal paidAmount = bill.getPaidAmount();
        bill.audit();
        BigDecimal paidAmountAfter = bill.getPaidAmount();
        assertEquals(paidAmount, paidAmountAfter);
    }
}
