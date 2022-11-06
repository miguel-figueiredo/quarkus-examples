package org.acme.extensiblejava.bill;

import extensiblejava.audit.AuditFacade;
import org.acme.extensiblejava.bill.data.BillDataBean;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BillTest {

    public static final String AUDITED_AMOUNT = "18.75";

    @Test
    public void testCustomerLoader() {
        Customer cust = Customer.loadCustomer(new DefaultCustomerEntityLoader(1));
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
                return new Bill(new BillDataBean(1, 1, "ONE", new BigDecimal("25.00"), null, null));
            }
        });
        assertNotNull(bill);
    }

    @Test
    public void testAudit() {
        Bill bill = Bill.loadBill(new BillEntityLoader() {
            public Bill loadBill() {
                return new Bill(new BillDataBean(1, 1, "ONE", new BigDecimal("25.00"), null, null));
            }
        });
        bill.audit(getAuditFacade());
        BigDecimal auditedAmount = bill.getAuditedAmount();
        assertEquals(new BigDecimal(AUDITED_AMOUNT), auditedAmount);
    }

    @Test
    public void testPay() {
        Bill bill = Bill.loadBill(new BillEntityLoader() {
            public Bill loadBill() {
                return new Bill(new BillDataBean(1, 1, "ONE", new BigDecimal("25.00"), null, null));
            }
        });
        bill.pay();
        assertEquals(bill.getPaidAmount(), bill.getAmount());
    }

    @Test
    public void testAuditAfterPay() {
        Bill bill = Bill.loadBill(new BillEntityLoader() {
            public Bill loadBill() {
                return new Bill(new BillDataBean(1, 1, "ONE", new BigDecimal("25.00"), null, null));
            }
        });
        bill.pay();
        BigDecimal paidAmount = bill.getPaidAmount();
        bill.audit(getAuditFacade());
        BigDecimal paidAmountAfter = bill.getPaidAmount();
        assertEquals(paidAmount, paidAmountAfter);
    }

    private AuditFacade getAuditFacade() {
        AuditFacade auditFacade = mock(AuditFacade.class);
        when(auditFacade.audit(Mockito.any())).thenReturn(new BigDecimal(AUDITED_AMOUNT));
        return auditFacade;
    }
}
