package com.iteria.Prueba.Controller;


import com.iteria.Prueba.entity.*;
import com.iteria.Prueba.exception.TipoDocumentoInactivoException;
import com.iteria.Prueba.exception.TipoDocumentoNuloException;
import com.iteria.Prueba.service.serviceImpl.AfiliadoService;
import com.iteria.Prueba.service.serviceImpl.ContratoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/afiliados")
public class AfiliadoController {

    private final AfiliadoService afiliadoService;
    private final ContratoService contratoService;

    public AfiliadoController(AfiliadoService afiliadoService, ContratoService contratoService) {
        this.afiliadoService = afiliadoService;
        this.contratoService = contratoService;
    }

    @PostMapping("/crearAfiliado")
    public ResponseEntity<?> crearAfiliado(@RequestBody Afiliado afiliado) {
        try {
            Afiliado afiliadoCreado = afiliadoService.crearAfiliado(afiliado);
            return new ResponseEntity<>(afiliadoCreado, HttpStatus.CREATED);
        } catch (TipoDocumentoInactivoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (TipoDocumentoNuloException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }


    @PutMapping("/actualizar/{afiId}")
    public ResponseEntity<?> actualizarAfiliadoConTipoDocumento(
            @PathVariable Long afiId,
            @RequestBody Afiliado afiliado,
            @RequestParam Long tdcId,  // Asegúrate de incluir 'tdcId' en la URL o en la solicitud
            @RequestParam String tdcNombre,
            @RequestParam TdcEstado tdcEstado) {

        TipoDocumento tipoDocumento = new TipoDocumento();
        tipoDocumento.setTdcId(tdcId);
        tipoDocumento.setTdcNombre(tdcNombre);
        tipoDocumento.setTdcEstado(tdcEstado);

        try {
            Afiliado afiliadoActualizado = afiliadoService.actualizarAfiliadoConTipoDocumento(afiId, afiliado, tipoDocumento);
            return new ResponseEntity<>(afiliadoActualizado, HttpStatus.OK);
        } catch (TipoDocumentoInactivoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/cambiarEstado")
    public ResponseEntity<String> cambiarEstadoAfiliado(
            @RequestParam Long afiliadoId,
            @RequestParam Estado nuevoEstado) {

        try {
            afiliadoService.cambiarEstadoAfiliado(afiliadoId, nuevoEstado);
            return new ResponseEntity<>("Estado del afiliado cambiado exitosamente", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
//    @PutMapping("/actualizar/{id}")
//    public Afiliado actualizarAfiliado(@PathVariable Long id, @RequestBody Afiliado afiliadoActualizado) {
//        return afiliadoService.actualizarAfiliado(id, afiliadoActualizado);
//    }
//
//    @PutMapping("/actualizarEstado/{afiliadoId}/{nuevoEstado}")
//    public Afiliado actualizarEstadoAfiliado(@PathVariable Long afiliadoId, @PathVariable Estado nuevoEstado) {
//        return afiliadoService.actualizarEstadoAfiliado(afiliadoId, nuevoEstado);
//    }


