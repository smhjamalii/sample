package org.acme;

import io.quarkus.vertx.web.Route;
import io.quarkus.vertx.web.RoutingExchange;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

//@Path("/")    
@ApplicationScoped
public class ExampleResource {   
    
    @Inject
    CounterSingletonImmediateSum counterSingleton;        
    
    @Inject
    Vertx vertx;
    
    //@POST   
    @Route(path = "/", methods = HttpMethod.POST)
    public void add(RoutingContext rc) {
        counterSingleton.add(rc.getBodyAsString());
//        return Uni.createFrom().item(()-> Response.ok().build());
        rc.response().end();
    }

    //@GET
    //@Path("count")    
    @Route(path = "/count", methods = HttpMethod.GET)
    public void getCount(RoutingExchange ex) {        
        //return Uni.createFrom().item(()-> String.valueOf(counterSingleton.getCount()));
        ex.ok(String.valueOf(counterSingleton.getCount()));
    }
    
}