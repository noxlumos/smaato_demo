package com.example.smaato.controller;


import com.example.smaato.service.AcceptService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SmaatoController {

    private AcceptService acceptService;

    public SmaatoController(AcceptService acceptService) {
        this.acceptService = acceptService;
    }

    @GetMapping(path = "/api/smaato/accept")
    public String getId(@RequestParam(name = "id") Integer id, @RequestParam(name = "endpoint", required = false) String endpoint){
        try {
            acceptService.accept(id, endpoint);
            return "ok";
        } catch (Exception e) {
            return "failed";
        }

    }

}
