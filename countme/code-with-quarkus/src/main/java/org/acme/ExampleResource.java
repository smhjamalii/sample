package org.acme;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/")    
public class ExampleResource {   
    
    @Inject
    CounterSingleton counterSingleton;        
    
    @POST    
    public void add(String number) {
        number = number.replaceAll("[^0-9]", "");
        counterSingleton.add(Long.valueOf(number.trim()));        
    }

    @GET
    @Path("count")    
    public String getCount() {        
        return String.valueOf(counterSingleton.getCount());
    }
    
}