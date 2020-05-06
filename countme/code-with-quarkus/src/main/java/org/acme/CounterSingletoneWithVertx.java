package org.acme;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Context;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import java.util.concurrent.atomic.AtomicLong;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 *
 * @author mohammad
 */
//@ApplicationScoped
public class CounterSingletoneWithVertx extends AbstractVerticle {

    private AtomicLong count;
    private Long number;
    
    public CounterSingletoneWithVertx(AtomicLong count, Long number) {
        this.count = count;
        this.number = number;
    }        
    
    @Override
    public void start() throws Exception {
        this.count.set(this.count.get() + number);
    }
 
}
