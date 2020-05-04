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
public class CounterSingletonWithThreadPool implements Serializable {
    
    private static final int DEFAULT_RPS = 100;         
    private AtomicLong count;    
    private int checkpoint = 0;
    private ConcurrentLinkedQueue<LinkedList<Long>> queue;
    private LinkedBlockingDeque<Future<Long>> futureDeque;    
    private long idleTime = 0;
    private ExecutorService es;
    private ScheduledExecutorService ses;      
    private volatile int index;
    
    public CounterSingletonWithThreadPool() {
        
        es = Executors.newFixedThreadPool(3);        
        ses = Executors.newScheduledThreadPool(3);                
        
        count = new AtomicLong(0L);                      
        futureDeque = new LinkedBlockingDeque<>();
        queue = new ConcurrentLinkedQueue<>();
        queue.offer(new LinkedList<>());
        ses.scheduleWithFixedDelay(() -> createChunk(), 1000, 1000, TimeUnit.MILLISECONDS);
        ses.scheduleWithFixedDelay(() -> sumUp(), 1050, 1050, TimeUnit.MILLISECONDS);        
        
        new Thread(() -> adjustRps()).start();        
    }   
    
    public Long getCount(){         
        return count.get();        
    }        
    
    public void add(Long number){
        queue.peek().add(number);        
        index++;
    }
    int i = 0;
    private void createChunk(){        
        while(index > checkpoint){               
            queue.offer(new LinkedList<>());
            List<Long> chunck = new ArrayList<>(queue.poll());            
            i+=chunck.size();
//            System.out.println("total: " + i + " chunk size: " + chunck.size() + " index: " + index + " checkpoint: " + checkpoint);
            calculate(chunck);
            checkpoint = index + 1;            
        }
    }
    
    private void sumUp() {        
        while(! futureDeque.isEmpty() || ! es.isTerminated()){
            try {
                if(! futureDeque.isEmpty()){
                    count.set(count.get() + futureDeque.poll().get());
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(CounterSingletonWithThreadPool.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExecutionException ex) {
                Logger.getLogger(CounterSingletonWithThreadPool.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void calculate(List<Long> list) {                
        futureDeque.offer((Future<Long>) es.<Long>submit(new CallableCalculator(list)));
    }
    
    private void adjustRps(){
        int indexAtStart = 0;
        int indexAtEnd = DEFAULT_RPS;
        while(true) {        
            indexAtStart = index;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(CounterSingletonWithThreadPool.class.getName()).log(Level.SEVERE, null, ex);
            }
            indexAtEnd = index; 
            if(indexAtEnd - indexAtStart == 0) {
                idleTime++;
            } else {
                idleTime = 0;
            }
            if(idleTime > 5) {                                
                try {                    
                    ses.shutdown();
                    es.shutdown();                    
                    es.awaitTermination(1, TimeUnit.HOURS);
                } catch (InterruptedException ex) {
                    Logger.getLogger(CounterSingletonWithThreadPool.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }            
        }
    }
}
