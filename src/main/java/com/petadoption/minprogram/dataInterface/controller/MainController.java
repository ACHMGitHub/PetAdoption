package com.petadoption.minprogram.dataInterface.controller;

import com.petadoption.minprogram.dataInterface.service.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class MainController {

    @Autowired
    private IUsersService usersService;

    @RequestMapping("TestGetData")
    public List getUsersList(){
        return usersService.list();
    }

    @RequestMapping("/testpost")
    public String wxAppletLoginApi(@RequestBody Map<String, String> request) {
        return "success";
    }
}
