package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.config.EncoderConfig.encoderConfig;
import java.util.Date;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class ExampleResourceTest {

    @Test    
    public void add() {
        
        RestAssured.config = 
                RestAssured.config().encoderConfig(
                    encoderConfig().defaultCharsetForContentType("UTF-8", "text/plain")
                );
        
        for(int i = 0; i < 100; i++){
            given()                        
              .body("1")          
              .post("/");
            
            if(i+1 % 250 == 0) {
                given()
                  .when().get("/count")
                  .then()              
                        .statusCode(200)
                    .body(is(String.valueOf(250 * (i / 250))));
            }
        }
        
        for(int i = 0; i < 100; i++){
            given()                        
              .body("1")          
              .post("/");
            
            if(i+1 % 250 == 0) {
                given()
                  .when().get("/count")
                  .then()                                    
                        .statusCode(200)
                    .body(is(String.valueOf(2 * 250 * (i / 250))));
            }            
            
        }        
                        
            given()                        
              .body("1 0")          
              .post("/")                
              .then()
                 .statusCode(200)
                 .body(is(""));            
        
            System.out.println(new Date());
            
        given()
          .when().get("/count")
          .then()
             .statusCode(200)
             .body(is("210"));        
        
        System.out.println(new Date());
    }

    @Test    
    public void getCount() {
        given()
          .when().get("/count")
          .then()
             .statusCode(200)
             .body(is("210"));
    }    
    
}