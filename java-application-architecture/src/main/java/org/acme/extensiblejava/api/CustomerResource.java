package org.acme.extensiblejava.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import org.acme.extensiblejava.bill.Customer;
import org.acme.extensiblejava.bill.DefaultCustomerEntityLoader;

@Path("/customers")
public class CustomerResource {

    @GET
    @Path("/{id}")
    public Customer get(@PathParam("id") Integer id) {
        return Customer.loadCustomer(new DefaultCustomerEntityLoader(id));
    }
}