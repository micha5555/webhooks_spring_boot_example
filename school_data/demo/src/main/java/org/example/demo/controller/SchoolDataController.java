package org.example.demo.controller;

import org.example.demo.model.SchoolData;
import org.example.demo.model.Student;
import org.example.demo.model.WebhookDetails;
import org.example.demo.repository.SchoolDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/demo")
public class SchoolDataController {

    @Autowired
    private SchoolDataRepository schoolDataRepository;

    @PostMapping("/addNewSchool")
    public @ResponseBody SchoolData addNewSchool(@RequestParam String schoolName) {
        SchoolData schoolData = new SchoolData();
        schoolData.setSchoolName(schoolName);
        schoolDataRepository.save(schoolData);
        return schoolData;
    }

    @PostMapping("/addWebhookEvent/{schoolId}")
    public @ResponseBody String addWebhookEvent(@PathVariable Integer schoolId, @RequestBody WebhookDetails webhookDetails) {
        Optional<SchoolData> schoolDataOptional = schoolDataRepository.findById(schoolId);

        WebhookDetails webhook = new WebhookDetails();
        webhook.setEventName(webhookDetails.getEventName());
        webhook.setEndpointUrl(webhookDetails.getEndpointUrl());
        webhook.setSchoolData(schoolDataOptional.get());

        schoolDataOptional.get().getWebhookDetails().add(webhook);
        schoolDataRepository.save(schoolDataOptional.get());

        return "Webhook added";
    }

    @PostMapping("/addStudent/{schoolId}")
    public @ResponseBody String addStudent(@PathVariable Integer schoolId, @RequestBody Student reqData) {
        Optional<SchoolData> schoolDataOptional = schoolDataRepository.findById(schoolId);

        List<Student> students = schoolDataOptional.get().getStudents();
        Student student = new Student();
        student.setName(reqData.getName());
        student.setAge(reqData.getAge());
        student.setSchoolData(schoolDataOptional.get());
        students.add(student);
        schoolDataOptional.get().setStudents(students);

        schoolDataRepository.save(schoolDataOptional.get());
        WebhookDetails webhookDetails = schoolDataOptional.get().getWebhookDetails()
                .stream().filter(webhook -> webhook.getEventName().equals("add"))
                .findFirst()
                .orElse(null);

        if (webhookDetails != null && webhookDetails.getEndpointUrl() != null) {
            // Call the webhook
            System.out.println("Calling webhook: " + webhookDetails.getEndpointUrl());
            String url = webhookDetails.getEndpointUrl() + "/" + student.getName();
            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForObject(url, String.class);
            System.out.println("Webhook response: " + result);
        }
        return "Student added";
    }
}
