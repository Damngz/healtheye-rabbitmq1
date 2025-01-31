package com.healtheye_rbmq1.healtheye_rbmq1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healtheye_rbmq1.healtheye_rbmq1.model.Alert;
import com.healtheye_rbmq1.healtheye_rbmq1.service.ConsumerAlertService;

@RestController
@CrossOrigin
@RequestMapping("/alerts")
public class ConsumerAlertController {
  @Autowired
  private ConsumerAlertService consumer;

  @GetMapping("/{patientId}")
  public List<Alert> getAlertsByPatient(@PathVariable Long patientId) {
    return consumer.getAlertsByPatient(patientId);
  }

}
