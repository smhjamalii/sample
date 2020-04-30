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
    CounterSingleton counterSingleton;        
    
    @POST
    @Path("/")    
    @Consumes(value = {MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON})
    public void add(String number) {
        number = number.replaceAll("[^0-9]", "");
        counterSingleton.add(Long.valueOf(number.trim()));        
    }

    @GET
    @Path("count")
    @Produces(value = {MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON})    
    public String getCount() {        
        return String.valueOf(counterSingleton.getCount());
    }
    
}