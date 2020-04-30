/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.acme;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author mohammad
 */
public class CounterSingleton {
    
    private AtomicInteger count = new AtomicInteger(0);    
    
    private CounterSingleton() {
    }
    
    public static CounterSingleton getInstance() {
        return CounterSingletonHolder.INSTANCE;
    }
    
    private static class CounterSingletonHolder {
        private static final CounterSingleton INSTANCE = new CounterSingleton();
    }
    
    public AtomicInteger getCount(){
        return count;
    }
}
