package com.iteria.Prueba.repository;

import com.iteria.Prueba.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface ContratoRepository extends JpaRepository<Contrato, ContratoId> {

    Optional<Contrato> findById_AfiIdAndId_PlanId(Long afiId, Long planId);

    List<Contrato> findByAfiliado(Afiliado afiliado);

    // Resto de tus métodos del repositorio...
}

