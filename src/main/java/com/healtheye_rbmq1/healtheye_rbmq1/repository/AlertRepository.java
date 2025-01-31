package com.healtheye_rbmq1.healtheye_rbmq1.repository;

import com.healtheye_rbmq1.healtheye_rbmq1.model.Alert;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {
  List<Alert> findByPatientIdOrderByTimestampDesc(Long patientId);
}
