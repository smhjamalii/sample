package org.acme;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;

/**
 *
 * @author mohammad
 */
@ApplicationScoped
public class CounterSingletonWithThreadPool implements Serializable {
    
    private static final Long DEFAULT_RPS = 100L;
    
    private Long count;
    private LinkedBlockingQueue<List<Long>> queue;    
    private long index = 1;
    private long enqueueIndex = 1;
    private long rps = DEFAULT_RPS;
    private long idleTime = 0;
    private ExecutorService es;        
    
    public CounterSingletonWithThreadPool() {
        count = 0L;
        queue = new LinkedBlockingQueue<>();
        es = Executors.newCachedThreadPool();        
        queue.offer(new LinkedList<>());
        new Thread(() -> adjustRps()).start();
    }   
    
    public Long getCount(){        
        while(! queue.isEmpty()){
            es.submit(() -> {
                calculate(queue.poll());
            });                
        }            
        return count;
    }
    
    public void add(Long number){
        if(! queue.isEmpty()){
            queue.offer(new LinkedList<>());
        }
        queue.peek().add(number);        
        index++;        
        if ((index - enqueueIndex) % rps == 0){
            queue.offer(new LinkedList<>());            
            enqueueIndex = index;
            es.execute(() -> {
                calculate(queue.poll());          
            });            
        }
    }
    
    private void calculate(List<Long> list){
        count += list.stream().reduce(0L, Long::sum);
    }
    
    private void adjustRps(){
        long indexAtStart = 0;
        long indexAtEnd = DEFAULT_RPS;
        while(true) {        
            indexAtStart = index;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(CounterSingletonWithThreadPool.class.getName()).log(Level.SEVERE, null, ex);
            }
            indexAtEnd = index; 
            if(indexAtEnd - indexAtStart > DEFAULT_RPS){
                long rpsCandiate = indexAtEnd - indexAtStart;
                long newRps = 1;
                while (rpsCandiate >= DEFAULT_RPS){
                    newRps *= 10;
                    rpsCandiate = rpsCandiate / 10;
                }
                if(rpsCandiate > 0){
                    rps = newRps * rpsCandiate;
                }
            }
            if(indexAtEnd - indexAtStart == 0) {
                idleTime++;
            } else {
                idleTime = 0;
            }
            if(idleTime > 5) {                                
                try {
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
