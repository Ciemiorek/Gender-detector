package com.Ciemiorek.Genderdetector.controller;

import com.Ciemiorek.Genderdetector.common.ResponseDictionary;
import com.Ciemiorek.Genderdetector.common.TypeOfChecking;
import com.Ciemiorek.Genderdetector.service.GenderDetectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBodyReturnValueHandler;

@Controller
@RequestMapping("gender")
public class GenderDetectorController {

    @Autowired
    private GenderDetectorService genderDetectorService;

    private StreamingResponseBodyReturnValueHandler handler = new StreamingResponseBodyReturnValueHandler();


    @GetMapping(value = "/{fullName}/{firstOrAll}",produces = "application/json")
    public ResponseEntity<ResponseDictionary> getGender(
            @PathVariable String fullName,
            @PathVariable TypeOfChecking firstOrAll
    ){
        return genderDetectorService.checkGender(fullName,firstOrAll);
    }



    @GetMapping(value = "/tokens",produces = "application/json")
    public StreamingResponseBody getAllTokens(){
        return genderDetectorService.returnAllNamesTokens();
    }

}
