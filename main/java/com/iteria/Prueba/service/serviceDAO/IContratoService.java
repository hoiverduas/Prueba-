package com.iteria.Prueba.service.serviceDAO;

import com.iteria.Prueba.entity.Afiliado;
import com.iteria.Prueba.entity.Contrato;
import com.iteria.Prueba.entity.Plan;

import java.util.Date;

public interface IContratoService {


    Contrato crearContrato(Long afiId, Long plnId, Contrato contrato);
    Contrato actualizarContrato(Long afiId, Long plnId, Contrato contrato);


//    Contrato crearNuevoContrato(Long afiliadoId, Long planId);
//    Contrato actualizarContrato(Long contratoId, Contrato contratoActualizado);
//
//    Contrato actualizarFechaRetiroYEstado(Long contratoId, String fechaRetiro);
}
