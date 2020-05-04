package org.acme;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/")    
public class ExampleResource {   
    
    @Inject
    CounterSingletonWithThreadPool counterSingleton;        
    
    @POST    
    public Response add(String number) {
        counterSingleton.add(number);
        return Response.ok().build();
    }

    @GET
    @Path("count")    
    public String getCount() {        
        return String.valueOf(counterSingleton.getCount());
    }
    
}