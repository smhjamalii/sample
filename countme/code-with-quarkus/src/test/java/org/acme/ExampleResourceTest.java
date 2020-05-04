package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class ExampleResourceTest {

    //@Test    
    public void add() {       
        
        for(int i = 0; i < 10000; i++){
            given()                        
              .body("1")          
              .post("/");
        }
        
        for(int i = 0; i < 10000; i++){
            given()                        
              .body("1")          
              .post("/");                            
        }        
                        
            given()                        
              .body("1 0")          
              .post("/")                
              .then()
                 .statusCode(200)
                 .body(is(""));                   
            
        given()
          .when().get("/count")
          .then()
             .statusCode(200)
             .body(is("20010"));        
        
    }

    //@Test    
    public void getCount() {
        given()
          .when().get("/count")
          .then()
             .statusCode(200)
             .body(is("20010"));
    }    
    
}