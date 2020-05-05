package org.acme;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;

/**
 *
 * @author mohammad
 */
@ApplicationScoped
public class CounterSingletonImmediateSum implements Serializable {
        
    private AtomicLong count;    
    
    public CounterSingletonImmediateSum() {                     
        count = new AtomicLong(0L);                                    
    }   
    
    public Long getCount(){         
        return count.get();        
    }                
    
    public void add(String number){
        count.set(count.get() + Long.valueOf(parseLong(number)));
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
