package org.acme;

import java.io.Serializable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLongArray;
import java.util.stream.LongStream;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CounterSingletonImmediateSum implements Serializable {
            
    private ScheduledExecutorService ses;
    private AtomicLongArray numbers = new AtomicLongArray(5_000_000);
    private int index = 0;       
    private int exIndex = 0;
    private Long count = 0L;
    
    public CounterSingletonImmediateSum (){
        ses = Executors.newScheduledThreadPool(1);
        ses.scheduleWithFixedDelay(() -> calculate(), 500, 5, TimeUnit.MILLISECONDS);
    }
    
    private void calculate(){
        if(exIndex<=index){
            count += numbers.get(exIndex++);
        }
    }
    
    public Long getCount(){                 
        return this.count;
    }                
    
    public void add(String number){
        numbers.addAndGet(index++, parseLong(number));        
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
