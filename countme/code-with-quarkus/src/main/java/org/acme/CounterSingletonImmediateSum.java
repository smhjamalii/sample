package org.acme;

import io.vertx.core.Vertx;
import java.io.Serializable;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicLong;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class CounterSingletonImmediateSum implements Serializable {

    @Inject
    Vertx vertx;    
    
    private ConcurrentLinkedDeque<Long> deque;
    private int index = 0;           
    private AtomicLong count;

    public CounterSingletonImmediateSum() {
        this.deque = new ConcurrentLinkedDeque<>();
        this.count = new AtomicLong(0L);
    }        
    
    public Long getCount(){                 
        return this.count.get();
    }                
    
    public void add(String number){        
        this.count.addAndGet(parseLong(number));
    }
    
    private Long parseLong(String s){
        char[] chars = s.toCharArray();
        StringBuilder number = new StringBuilder();
        for(char ch : chars){
            if(Character.isDigit(ch) || ch == '-'){
                number.append(ch);
            }
        }        
        return Long.parseLong(number.toString());
    }      
    
}
