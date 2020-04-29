package org.acme;

import java.util.concurrent.atomic.AtomicInteger;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class ExampleResource {

    private static AtomicInteger count = new AtomicInteger(0);
    
    @POST
    @Path("/")    
    @Consumes(MediaType.TEXT_PLAIN)
    public void add(String number) {
        count.addAndGet(Integer.valueOf(number.trim()));        
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)    
    public String getCount() {        
        return String.valueOf(count.get());
    }
    
}