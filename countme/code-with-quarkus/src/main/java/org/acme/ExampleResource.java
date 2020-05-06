package org.acme;

import io.smallrye.mutiny.Uni;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/")    
public class ExampleResource {   
    
    @Inject
    CounterSingletonImmediateSum counterSingleton;           
    
    @POST
    public Uni<Response> add(String number) {
        counterSingleton.add(number);
        return Uni.createFrom().item(()-> Response.ok().build());        
    }

    @GET
    @Path("count")        
    public Uni<String> getCount() {        
        return Uni.createFrom().item(()-> String.valueOf(counterSingleton.getCount()));        
    }
    
}