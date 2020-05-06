/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.acme;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
//import java.util.regex.Pattern;

/**
 *
 * @author mohammad
 */
public class CallableCalculator implements Callable<Long> {

    private List<String> numbers;                    
    
    public CallableCalculator(List<String> numbers) {
        this.numbers = numbers;          
    }
                    
    @Override
    public Long call() throws Exception {        
        if(numbers != null && ! numbers.isEmpty()){                                                
            return numbers.stream().collect(Collectors.summingLong(number -> parseLong(number)));            
        } else {
            return 0L;
        }
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
