package com.example.demo.controller;

import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestSchoolController {
    @GetMapping("/webhook/studentAdded/{name}")
    public @ResponseBody String studentAdded(@PathVariable String name) {
        System.out.println("Student Added: " + name);
        return "Webhook Received";
    }
}
