package org.acme;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class ExampleResource {   
    
    @Inject
    private CounterSingleton counterSingleton;
    
    @POST
    @Path("/")    
    @Consumes(MediaType.TEXT_PLAIN)
    public void add(String number) {
        counterSingleton.add(Long.valueOf(number.trim()));        
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)    
    public String getCount() {        
        return String.valueOf(counterSingleton.getCount());
    }
    
}