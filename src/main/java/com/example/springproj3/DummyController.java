package com.example.springproj3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
public class DummyController {

    @Autowired
    DummyService dummyService;

    @GetMapping("/getDummy")
    public String getPatient(@RequestParam String name ) throws InterruptedException, ExecutionException{
        return dummyService.getDummyDetails(name);
    }

    @PostMapping("/insertDummy")
    public String createPatient(@RequestBody Dummy dummy ) throws InterruptedException, ExecutionException {
        return dummyService.insertDummyDetails(dummy);
    }
}