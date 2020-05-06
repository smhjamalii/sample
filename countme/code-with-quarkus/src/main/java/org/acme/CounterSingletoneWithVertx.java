package org.acme;

import io.vertx.core.Context;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 *
 * @author mohammad
 */
//@ApplicationScoped
public class CounterSingletoneWithVertx implements Verticle {
    
    //@Inject Vertx vertx;
    
//    @PostConstruct
//    public void init(){
//        vertx.deployVerticle(MyVerticle.class.getName(), ar -> { });
//        vertx.deployVerticle(new MyVerticle(), ar -> { });        
//    }

    @Override
    public Vertx getVertx() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void init(Vertx vertx, Context cntxt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void start(Future<Void> future) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        Verticle.super.start(startPromise); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void stop(Future<Void> future) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void stop(Promise<Void> stopPromise) throws Exception {
        Verticle.super.stop(stopPromise); //To change body of generated methods, choose Tools | Templates.
    }
    
}
