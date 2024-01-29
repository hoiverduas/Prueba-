package com.iteria.Prueba.service.serviceImpl;

import com.iteria.Prueba.entity.Contrato;
import com.iteria.Prueba.entity.Plan;
import com.iteria.Prueba.repository.ContratoRepository;
import com.iteria.Prueba.repository.PlanRepository;
import com.iteria.Prueba.service.serviceDAO.IPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlanService implements IPlanService{



    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private ContratoRepository contratoRepository;

    public Plan guardarPlanYContrato(Plan plan) {
        return planRepository.save(plan);
    }


}
