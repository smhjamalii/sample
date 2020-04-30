/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.acme;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;
import javax.enterprise.context.ApplicationScoped;

/**
 *
 * @author mohammad
 */
@ApplicationScoped
public class CounterSingleton implements Serializable {
    
    private AtomicLong count;
    
    public CounterSingleton() {
         count = new AtomicLong(0);    
    }   
    
    public Long getCount(){
        return count.get();
    }
    
    public void add(Long number){
        count.addAndGet(number);
    }
}
