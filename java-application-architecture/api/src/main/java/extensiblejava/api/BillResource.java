package extensiblejava.api;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import org.acme.extensiblejava.bill.Bill;
import org.acme.extensiblejava.bill.DefaultBillEntityLoader;

@Path("/bills")
public class BillResource {

    @GET
    @Path("/{id}")
    public Bill get(@PathParam("id") Integer id) {
        return Bill.loadBill(new DefaultBillEntityLoader(id));
    }

    @POST
    @Path("/{id}/audit")
    public Bill audit(@PathParam("id") Integer id) {
        Bill bill = get(id);
        bill.audit();
        return bill;
    }

    @POST
    @Path("/{id}/pay")
    public Bill pay(@PathParam("id") Integer id) {
        Bill bill = get(id);
        bill.pay();
        return bill;
    }
}