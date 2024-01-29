package com.iteria.Prueba.Controller;

import com.iteria.Prueba.entity.Afiliado;
import com.iteria.Prueba.entity.Contrato;
import com.iteria.Prueba.exception.ContratoActivoException;
import com.iteria.Prueba.exception.ContratoInvalidoException;
import com.iteria.Prueba.exception.PlanInactivoException;
import com.iteria.Prueba.service.serviceImpl.ContratoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contratos")
public class ContratoController {

    @Autowired
    private ContratoService contratoService;

    @PostMapping("/crear")
    public ResponseEntity<String> crearContrato(
            @RequestParam Long afiId,
            @RequestParam Long plnId,
            @RequestBody Contrato contrato) {

        try {
            contratoService.crearContrato(afiId, plnId, contrato);
            return new ResponseEntity<>("Contrato creado exitosamente", HttpStatus.CREATED);
        } catch (ContratoInvalidoException e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ContratoActivoException e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (PlanInactivoException e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/actualizar/{afiId}/{plnId}")
    public ResponseEntity<?> actualizarContrato(
            @PathVariable Long afiId,
            @PathVariable Long plnId,
            @RequestBody Contrato contrato) {

        try {
            Contrato contratoActualizado = contratoService.actualizarContrato(afiId, plnId, contrato);
            return new ResponseEntity<>(contratoActualizado, HttpStatus.OK);
        } catch (PlanInactivoException e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}




//    @PostMapping("/nuevo")
//    public Contrato crearNuevoContrato(@RequestParam Long afiliadoId, @RequestParam Long planId) {
//        return contratoService.crearNuevoContrato(afiliadoId, planId);
//    }

//    @PutMapping("/actualizar/{id}")
//    public Contrato actualizarContrato(@PathVariable Long id, @RequestBody Contrato contratoActualizado) {
//        return contratoService.actualizarContrato(id, contratoActualizado);
//    }
//
//    @PutMapping("/actualizarFechaRetiro/{id}")
//    public Contrato actualizarFechaRetiroYEstado(@PathVariable Long id, @RequestParam String fechaRetiro) {
//        // Llamar al método del servicio
//        return contratoService.actualizarFechaRetiroYEstado(id, fechaRetiro);
//    }

