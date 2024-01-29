package com.iteria.Prueba.Controller;

import com.iteria.Prueba.entity.Plan;
import com.iteria.Prueba.service.serviceImpl.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/planes")
public class PlanController {

    @Autowired
    private PlanService planService;

    @PostMapping("/guardarPlanYContrato")
    public ResponseEntity<Plan> guardarPlanYContrato(@RequestBody Plan plan) {
        Plan planGuardado = planService.guardarPlanYContrato(plan);
        return ResponseEntity.ok(planGuardado);
    }

    // Otros métodos del controlador según tus necesidades
}
