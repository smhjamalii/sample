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
//@ApplicationScoped
public class CounterSingletonWithThreadPool implements Serializable {
        
    private AtomicLong count;    
    private int checkpoint = 0;
    private ConcurrentLinkedQueue<String> queue;
    private LinkedBlockingDeque<Future<Long>> futureDeque;    
    private long idleTime = 0;
    private ExecutorService es;
    private ScheduledExecutorService ses;
    private volatile int index;
    private List<String> chunk;
    
    public CounterSingletonWithThreadPool() {
        
        es = Executors.newFixedThreadPool(1);        
        ses = Executors.newScheduledThreadPool(2);                
        
        count = new AtomicLong(0L);                      
        futureDeque = new LinkedBlockingDeque<>();
        queue = new ConcurrentLinkedQueue<>();        
        ses.scheduleWithFixedDelay(() -> finalCalculation(), 2000, 200, TimeUnit.MILLISECONDS);
        ses.scheduleWithFixedDelay(() -> sumUp(), 100, 10, TimeUnit.MILLISECONDS);        
        ses.scheduleWithFixedDelay(() -> adjustRps(), 1050, 1000, TimeUnit.MILLISECONDS);                
        
        chunk = new LinkedList<>();    
    }   
    
    public Long getCount(){         
        return count.get();        
    }                
    
    public void add(String number){
        chunk.add(number);        
        index++;        
        if(chunk.size()>=10){
            calculate(new ArrayList<>(chunk));
            chunk = new LinkedList<>();
        }
        System.out.println("Index: " + index + " Count: " + count.get() + " Chunk Size: " + chunk.size());
    }
        
    private void calculate(List<String> list) {                                
        futureDeque.offer((Future<Long>) es.<Long>submit(new CallableCalculator(list)));
    }    
    
    int chunkIndex;    
    private void finalCalculation(){                                        
        if(chunkIndex == index && chunk.size() > 0) {
            //System.out.println("Final Calculation is done.");
            calculate(new ArrayList<>(chunk));
            chunk = new LinkedList<>();
        }                
        chunkIndex = index;                
    }
    
    int sumRunCount = 0;
    private void sumUp() {                
        sumRunCount++;
        if(! futureDeque.isEmpty()){
            try {
                count.set(count.get() + futureDeque.poll().get());
            } catch (InterruptedException ex) {
                Logger.getLogger(CounterSingletonWithThreadPool.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExecutionException ex) {
                Logger.getLogger(CounterSingletonWithThreadPool.class.getName()).log(Level.SEVERE, null, ex);
            }                
        }
    }   
    
    int lastIndex = 0;
    private void adjustRps(){
        if(index == lastIndex && queue.isEmpty() && futureDeque.isEmpty()){
            ses.shutdown();
            es.shutdown();                    
            try {
                es.awaitTermination(1, TimeUnit.HOURS);
            } catch (InterruptedException ex) {
                Logger.getLogger(CounterSingletonWithThreadPool.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }
        lastIndex = index;
    }
}
