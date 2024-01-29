package com.iteria.Prueba.repository;

import com.iteria.Prueba.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {

}
