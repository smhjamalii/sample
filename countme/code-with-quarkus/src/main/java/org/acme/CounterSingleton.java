package org.acme;

import java.io.Serializable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;

/**
 *
 * @author mohammad
 */
//@ApplicationScoped
public class CounterSingleton implements Serializable {
    
    private static final Long DEFAULT_RPS = 100L;
    
    private Long count;
    private LinkedBlockingQueue<Long> queue;    
    private long index = 1;
    private long rps = DEFAULT_RPS;
    
    public CounterSingleton() {
        //count = 0L;
        //queue = new LinkedBlockingQueue<>();
        //new Thread(() -> adjustRps()).start();
    }   
    
    public Long getCount(){        
        while(! queue.isEmpty()){
            try {
                count += queue.take();
            } catch (InterruptedException ex) {
                Logger.getLogger(CounterSingleton.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return count;
    }
    
    public void add(Long number){
        queue.offer(number);  
        index++;
        if (index % rps == 0){
            calculate();            
            index = 1;
        }
    }
    
    private void calculate(){
        new Thread(()-> {
            while(! queue.isEmpty()){
                try {
                    count += queue.take();
                } catch (InterruptedException ex) {
                    Logger.getLogger(CounterSingleton.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();         
    }
    
    private void adjustRps(){
        long indexAtStart = 0;
        long indexAtEnd = DEFAULT_RPS;
        while(true) {        
            indexAtStart = index;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(CounterSingleton.class.getName()).log(Level.SEVERE, null, ex);
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
            //System.out.println("New rps: " + rps + " - " + (indexAtEnd - indexAtStart));
        }
    }
}
