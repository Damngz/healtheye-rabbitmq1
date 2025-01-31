package com.healtheye_rbmq1.healtheye_rbmq1.service;

import java.util.List;
import java.util.Map;
import java.sql.Timestamp;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.healtheye_rbmq1.healtheye_rbmq1.model.Alert;
import com.healtheye_rbmq1.healtheye_rbmq1.repository.AlertRepository;

@Service
public class ConsumerAlertService {
  @Autowired
  private AlertRepository alertRepository;

  @SuppressWarnings("unchecked")
  @RabbitListener(queues = "alertQueue")
  public void receiveMessage(String message) {
    try {
      Map<String, Object> alertMap = new ObjectMapper().readValue(message, Map.class);
      
      Long patientId = Long.valueOf(alertMap.get("patientId").toString());
      int heartRate = (int) alertMap.get("heartRate");
      int systolicPressure = (int) alertMap.get("systolicPressure");
      int diastolicPressure = (int) alertMap.get("diastolicPressure");
      int oxygenSaturation = (int) alertMap.get("oxygenSaturation");
      double bodyTemperature = ((Number) alertMap.get("bodyTemperature")).doubleValue();
      String alertMessage = alertMap.get("alertMessage").toString();
      String alertType = alertMap.get("alertType").toString();
      String timestamp = alertMap.get("timestamp").toString();
      Timestamp timestampParsed = Timestamp.valueOf(timestamp);

      Alert alert = new Alert();
      alert.setPatientId(patientId);
      alert.setAlertMessage(alertMessage);
      alert.setAlertType(alertType);
      alert.setTimestamp(timestampParsed);
      alert.setHeartRate(heartRate);
      alert.setSystolicPressure(systolicPressure);
      alert.setDiastolicPressure(diastolicPressure);
      alert.setOxygenSaturation(oxygenSaturation);
      alert.setBodyTemperature(bodyTemperature);

      alertRepository.save(alert);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public List<Alert> getAlertsByPatient(Long patientId) {
    return alertRepository.findByPatientIdOrderByTimestampDesc(patientId);
  }
}
