package org.acme.extensiblejava.bill.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Iterator;
import org.acme.extensiblejava.bill.data.BillDataBean;
import org.acme.extensiblejava.bill.data.BillDb;
import org.acme.extensiblejava.bill.data.CustomerDataBean;
import org.junit.jupiter.api.Test;

public class BillDbTest {

    @Test
    public void testCustomerLoad() {
        CustomerDataBean cust = BillDb.getCustomer(new Integer(1));
        assertEquals(cust.getId(), new Integer(1));
    }

    @Test
    public void testBillsLoad() {
        Iterator billBeans = BillDb.getBills(new Integer(1)).iterator();

        int i = 1;
        while (billBeans.hasNext()) {
            BillDataBean billBean = (BillDataBean) billBeans.next();
            assertEquals(billBean.getBillId(), new Integer(i));
            i++;
        }
    }
}
