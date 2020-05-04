/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.acme;

import java.util.List;
import java.util.concurrent.Callable;

/**
 *
 * @author mohammad
 */
public class CallableCalculator implements Callable<Long> {

    private List<Long> numbers;        
    
    public CallableCalculator(List<Long> numbers) {
        this.numbers = numbers;        
    }
                    
    @Override
    public Long call() throws Exception {        
        if(numbers != null && ! numbers.isEmpty()){                                    
            return numbers.stream().parallel().reduce(0L, Long::sum);
            //return numbers.stream().reduce(0L, Long::sum);
//            Long count = 0L;
//            for(Long num : numbers){
//                count += num;
//            }
//            return count;
        } else {
            return 0L;
        }
    }
    
}
