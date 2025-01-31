package com.healtheye_rbmq1.healtheye_rbmq1.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "alerts")
public class Alert {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "alert_id")
  private Long alertId;

  @Column(name = "patient_id", nullable = false)
  private Long patientId;

  @Column(name = "heart_rate", nullable = false)
  private int heartRate;

  @Column(name = "systolic_pressure", nullable = false)
  private int systolicPressure;

  @Column(name = "diastolic_pressure", nullable = false)
  private int diastolicPressure;

  @Column(name = "oxygen_saturation", nullable = false)
  private int oxygenSaturation;

  @Column(name = "body_temperature", nullable = false)
  private double bodyTemperature;

  @Column(name = "timestamp", nullable = false)
  private Timestamp timestamp;

  @Column(name = "alert_message", nullable = false)
  private String alertMessage;

  @Column(name = "alert_type", nullable = false)
  private String alertType;
}
