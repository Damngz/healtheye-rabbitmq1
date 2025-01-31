package com.healtheye_rbmq1.healtheye_rbmq1.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ProducerAlertService {
  @Autowired
  private RabbitTemplate rabbitTemplate;

  @Autowired
  private ObjectMapper objectMapper;

  @SuppressWarnings("unchecked")
  public void sendMessage(Map<String, Object> data) {
    Map<String, Object> patient = (Map<String, Object>) data.get("patient");
    Long patientId = ((Number) patient.get("patientId")).longValue();
    int heartRate = (int) data.get("heartRate");
    int systolicPressure = (int) data.get("systolicPressure");
    int diastolicPressure = (int) data.get("diastolicPressure");
    int oxygenSaturation = (int) data.get("oxygenSaturation");
    double bodyTemperature = ((Number) data.get("bodyTemperature")).doubleValue();
    String timestamp = data.get("timestamp").toString();
    Instant instant = Instant.parse(timestamp);
    LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.of("America/Santiago"));

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String formattedTimestamp = dateTime.format(formatter);

    String alertMessage = "";
    String alertType = "";

    if (heartRate < 50 || heartRate > 120) {
      alertMessage += "Frecuencia cardíaca CRÍTICA. ";
      alertType = "Critical";
    } else if (heartRate < 60 || heartRate > 100) {
      alertMessage += "Frecuencia cardíaca en los límites. ";
      alertType = "Warning";
    }

    if (oxygenSaturation < 90) {
      alertMessage += "Saturación de oxígeno CRÍTICA. ";
      alertType = "Critical";
    } else if (oxygenSaturation < 94) {
      alertMessage += "Saturación de oxígeno BAJA. ";
      alertType = "Warning";
    }

    if (systolicPressure > 130) {
      alertMessage += "Presión sistólica CRÍTICA. ";
      alertType = "Critical";
    } else if (systolicPressure > 120) {
      alertMessage += "Presión sistólica en los límites. ";
      alertType = "Warning";
    }

    if (diastolicPressure > 80) {
      alertMessage += "Presión diastólica CRÍTICA. ";
      alertType = "Critical";
    } else if (diastolicPressure < 80 && diastolicPressure > 75) {
      alertMessage += "Presión diastólica en los límites. ";
      alertType = "Warning";
    }

    if (bodyTemperature < 36.0 || bodyTemperature > 38.0) {
      alertMessage += "Temperatura corporal CRÍTICA. ";
      alertType = "Critical";
    } else if (bodyTemperature < 36.5 || bodyTemperature > 37.5) {
      alertMessage += "Temperatura corporal en los límites. ";
      alertType = "Warning";
    }

    if (!alertMessage.isEmpty()) {
      Map<String, Object> alert = Map.of(
        "patientId", patientId,
        "heartRate", heartRate,
        "systolicPressure", systolicPressure,
        "diastolicPressure", diastolicPressure,
        "oxygenSaturation", oxygenSaturation,
        "bodyTemperature", bodyTemperature,
        "alertMessage", alertMessage,
        "alertType", alertType,
        "timestamp", formattedTimestamp
      );

      try {
        String jsonMessage = objectMapper.writeValueAsString(alert);
        System.out.println("Sending alert: " + jsonMessage);
        rabbitTemplate.convertAndSend("alertQueue", jsonMessage);
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }
    }
  }
}
