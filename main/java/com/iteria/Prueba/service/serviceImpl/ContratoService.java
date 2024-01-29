package com.iteria.Prueba.service.serviceImpl;

import com.iteria.Prueba.entity.*;

import com.iteria.Prueba.exception.ContratoActivoException;
import com.iteria.Prueba.exception.ContratoInvalidoException;
import com.iteria.Prueba.exception.PlanInactivoException;
import com.iteria.Prueba.repository.AfiliadoRepository;
import com.iteria.Prueba.repository.ContratoRepository;

import com.iteria.Prueba.repository.PlanRepository;
import com.iteria.Prueba.service.serviceDAO.IContratoService;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

@Service
public class ContratoService implements IContratoService{

    @Autowired
    private ContratoRepository contratoRepository;

    @Autowired
    private AfiliadoRepository afiliadoRepository;

    @Autowired
    private PlanRepository planRepository;
    @Override
    public Contrato crearContrato(Long afiId, Long plnId, Contrato contrato) {
        // Buscar afiliado y plan
        Afiliado afiliado = afiliadoRepository.findById(afiId)
                .orElseThrow(() -> new EntityNotFoundException("Afiliado con ID " + afiId + " no encontrado"));

        Plan plan = planRepository.findById(plnId)
                .orElseThrow(() -> new EntityNotFoundException("Plan con ID " + plnId + " no encontrado"));

        if (plan.getEstadoPlan() != EstadoPlan.ACTIVO) {
            throw new PlanInactivoException("El plan no está activo y no se puede utilizar para el contrato");
        }
        LocalDate fechaFinPlan = plan.getPlnFechaFin();
        LocalDate fechaRegistroContrato = contrato.getFechaInicio();


        if (fechaFinPlan.isEqual(fechaRegistroContrato)) {
            throw new ContratoInvalidoException("La fecha fin del plan no puede ser el mismo día del registro del contrato");
        }

        // Verificar nulidad antes de asignar
        if (contrato != null) {
            // Verificar si ya tiene contrato activo con el mismo plan
            if (tieneContratoActivoConPlan(afiliado, plan)) {
                throw new ContratoActivoException("El afiliado ya tiene un contrato activo con este plan.");
            }

            ContratoId contratoId = new ContratoId(afiId, plnId);
            contrato.setId(contratoId);
            contrato.setAfiliado(afiliado);
            contrato.setPlan(plan);

            // Guardar contrato
            return contratoRepository.save(contrato);
        } else {
            throw new IllegalArgumentException("El contrato proporcionado es nulo");
        }
    }


    @Override
    public Contrato actualizarContrato(Long afiId, Long plnId, Contrato contrato) {
        // Verificar si el plan tiene estado activo
        Plan plan = planRepository.findById(plnId)
                .orElseThrow(() -> new EntityNotFoundException("Plan no encontrado"));

        if (plan.getEstadoPlan() != EstadoPlan.ACTIVO) {
            throw new PlanInactivoException("El plan no está activo");
        }

        // Obtener el contrato existente
        ContratoId contratoId = new ContratoId(afiId, plnId);
        Optional<Contrato> optionalContrato = contratoRepository.findById_AfiIdAndId_PlanId(afiId,plnId);

        if (optionalContrato.isPresent()) {
            Contrato contratoExistente = optionalContrato.get();

            // Actualizar campos específicos del contrato
            contratoExistente.setFechaInicio(contrato.getFechaInicio());
            contratoExistente.setFechaRetiro(contrato.getFechaRetiro());
            contratoExistente.setPlan(contrato.getPlan());
            contratoExistente.setAfiliado(contrato.getAfiliado());
            contratoExistente.setCantidadUsuarios(contrato.getCantidadUsuarios());
            contratoExistente.setId(contrato.getId());







            // Guardar cambios en el contrato
            return contratoRepository.save(contratoExistente);
        } else {
            throw new EntityNotFoundException("Contrato no encontrado");
        }
    }





    private boolean tieneContratoActivoConPlan(Afiliado afiliado, Plan plan) {
        return afiliado.getContratoList().stream()
                .anyMatch(contrato -> contrato.getPlan().equals(plan) && contrato.getPlan().getEstadoPlan().equals(EstadoPlan.ACTIVO));
    }




}




//    @Override
//    public Contrato crearNuevoContrato(Long afiliadoId, Long planId) {
//
//
//
//
//        // Obtener el afiliado y el plan
//        Afiliado afiliado = afiliadoRepository.findById(afiliadoId)
//                .orElseThrow(() -> new NoSuchElementException("Afiliado no encontrado con ID: " + afiliadoId));
//
//        Plan plan = planRepository.findById(planId)
//                .orElseThrow(() -> new NoSuchElementException("Plan no encontrado con ID: " + planId));
//
//        // Verificar si el afiliado ya tiene un contrato con el mismo plan activo
//        if (afiliadoYaTienePlanActivo(afiliado, plan)) {
//            throw new ContratoInvalidoException("El afiliado ya tiene este plan activo.");
//        }
//
//
//        // Validar la regla de negocio: No se puede registrar un contrato si su plan tiene como fecha fin el mismo día de registro del contrato
//        validarFechaFinPlan(plan, LocalDate.now());
//
//        // Crear el nuevo contrato
//        Contrato nuevoContrato = new Contrato();
//        nuevoContrato.setAfiliado(afiliado);
//        nuevoContrato.setPlan(plan);
//        nuevoContrato.setEstadoContrato(nuevoContrato.getEstadoContrato());
//
//        // Configurar otras propiedades del contrato si es necesario...
//
//        // Guardar el nuevo contrato en la base de datos
//        return contratoRepository.save(nuevoContrato);
//
//
//    }
//
//    @Override
//    public Contrato actualizarContrato(Long contratoId, Contrato contratoActualizado) {
//        // Verificar si el contrato existente está en la base de datos
//        Contrato contratoExistente = contratoRepository.findById(contratoId)
//                .orElseThrow(() -> new NoSuchElementException("Contrato no encontrado con ID: " + contratoId));
//
//        // Realizar actualizaciones necesarias en el contrato existente
//        contratoExistente.setPlan(contratoActualizado.getPlan());
//        contratoExistente.setCtoFechaInicio(contratoActualizado.getCtoFechaInicio());
//        Afiliado afiliadoActualizado = contratoActualizado.getAfiliado();
//        contratoExistente.setAfiliado(afiliadoActualizado);
//
//
//        // Obtener el plan actualizado (asumiendo que el plan está presente en contratoActualizado)
//        Plan planActualizado = contratoActualizado.getPlan();
//
//        // Validar el estado del plan
//        validarEstadoPlan(planActualizado);
//
//
//        // Actualizar el plan en el contrato existente
//        contratoExistente.setPlan(planActualizado);
//        // Guardar el contrato actualizado en la base de datos
//        return contratoRepository.save(contratoExistente);
//    }
//
//
//
//    private void validarFechaFinPlan(Plan plan, LocalDate ctoFechaRegistro) {
//        if (plan.getPlnFechaFin() != null && ctoFechaRegistro != null &&
//                plan.getPlnFechaFin().isEqual(ctoFechaRegistro)) {
//            throw new ContratoInvalidoException("No se puede registrar un contrato con un plan que tiene fecha fin el mismo día.");
//        }
//    }
//
//
//    private boolean afiliadoYaTienePlanActivo(Afiliado afiliado, Plan plan) {
//        // Verificar si existe un contrato activo para el afiliado y el plan proporcionados
//        return contratoRepository.existsByAfiliadoAndPlanAndEstadoContrato(afiliado, plan, EstadoContrato.ACTIVO);
//    }
//
//    private void validarEstadoPlan(Plan plan) {
//        if (plan != null) {
//            // No necesitas buscar el estado del plan en la base de datos porque son valores constantes
//            if (plan.getPlnEstado() != EstadoPlan.ACTIVO) {
//                throw new IllegalArgumentException("No se puede utilizar un plan inactivo.");
//            }
//        }
//    }
//
//
//    @Override
//    public Contrato actualizarFechaRetiroYEstado(Long contratoId, String fechaRetiro) {
//        Contrato contrato = contratoRepository.findById(contratoId)
//                .orElseThrow(() -> new NoSuchElementException("Contrato no encontrado con ID: " + contratoId));
//
//        // Convierte la cadena de fechaRetiro a un objeto Date
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//        Date fechaRetiroDate;
//        try {
//            fechaRetiroDate = dateFormat.parse(fechaRetiro);
//        } catch (ParseException e) {
//            // Manejar la excepción de análisis de fecha si es necesario
//            throw new IllegalArgumentException("Formato de fecha no válido: " + fechaRetiro, e);
//        }
//
//        // Luego, actualiza la fecha de retiro y el estado
//        contrato.setCtoFechaRetiro(fechaRetiroDate);
//        contrato.actualizarEstado();
//
//        // Guardar el contrato actualizado en la base de datos
//        return contratoRepository.save(contrato);
//    }
//
//// Otros métodos del servicio...
//
//    public LocalDate obtenerFechaActual() {
//        return LocalDate.now();
//    }
//
//    private int obtenerNumeroDiasHabilesPor(int tipoUsuario) {
//        Map<Integer, Integer> mapaDiasHabiles = new HashMap<>();
//        mapaDiasHabiles.put(1, 10);
//        mapaDiasHabiles.put(2, 8);
//        mapaDiasHabiles.put(3, 7);
//        return mapaDiasHabiles.getOrDefault(tipoUsuario, 0);
//    }
//
//    private LocalDate calcularFechaDevolucion(LocalDate fechaActual, int numeroDiasHabiles){
//        int contador = 0;
//        LocalDate fechaDevolucion = fechaActual;
//        while ( contador < numeroDiasHabiles){
//            fechaDevolucion = fechaDevolucion.plusDays(1);
//            DayOfWeek diaSemana = fechaDevolucion.getDayOfWeek();
//            if (diaSemana != DayOfWeek.SATURDAY && diaSemana != DayOfWeek.SUNDAY){
//                ++contador;
//            }
//        }
//        return fechaDevolucion;
//    }


