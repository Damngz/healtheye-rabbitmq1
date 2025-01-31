package com.healtheye_rbmq1.healtheye_rbmq1.controller;

import com.healtheye_rbmq1.healtheye_rbmq1.service.ProducerAlertService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@CrossOrigin
public class ProducerAlertController {
  @Autowired
  private ProducerAlertService producer;

  @PostMapping("/send")
  public Map<String, Object> sendMessage(@RequestBody Map<String, Object> message) {
    producer.sendMessage(message);
    Map<String, Object> response = new HashMap<>();
    response.put("status", "success");
    response.put("message", "Message sent successfully");
    return response;
  }
  
}
