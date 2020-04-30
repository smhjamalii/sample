package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.config.EncoderConfig.encoderConfig;
import io.restassured.http.ContentType;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class ExampleResourceTest {

    @Test    
    public void add() {
        
        RestAssured.config = 
                RestAssured.config().encoderConfig(
                    encoderConfig().defaultCharsetForContentType("UTF-8", "text/plain")
                );
        
            given()          
              .contentType(ContentType.TEXT)                
              .body("1")          
              .post("/")                
              .then()
                 .statusCode(204)
                 .body(is(""));
            
            given()          
              .contentType(ContentType.TEXT)                
              .body("1 0")          
              .post("/")                
              .then()
                 .statusCode(204)
                 .body(is(""));            
        
        given()
          .when().get("/count")
          .then()
             .statusCode(200)
             .body(is("11"));        
    }

    @Test    
    public void getCount() {
        given()
          .when().get("/count")
          .then()
             .statusCode(200)
             .body(is("11"));
    }    
    
}