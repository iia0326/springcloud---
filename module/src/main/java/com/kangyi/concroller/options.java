package com.kangyi.concroller;




import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//@Controller
//@CrossOrigin(origins = "*",allowCredentials = "true",allowedHeaders = {"X-Custom-Header"},
//        maxAge = 3600L)
public class options {
    @RequestMapping(value = "/*",method = RequestMethod.OPTIONS)
    public ResponseEntity handleOptions(){
        System.out.println("          options            ");
        return (ResponseEntity) ResponseEntity.noContent();
    }

}
