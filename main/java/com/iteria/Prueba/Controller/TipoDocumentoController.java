package com.iteria.Prueba.Controller;

import com.iteria.Prueba.entity.TipoDocumento;
import com.iteria.Prueba.service.serviceDAO.ITipoDocumentoService;
import com.iteria.Prueba.service.serviceImpl.TipoDocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tipo-documento")
public class TipoDocumentoController {

    private final TipoDocumentoService tipoDocumentoService;

    @Autowired
    public TipoDocumentoController(TipoDocumentoService tipoDocumentoService) {
        this.tipoDocumentoService = tipoDocumentoService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<TipoDocumento> registrarTipoDocumento(@RequestBody TipoDocumento tipoDocumento) {
        TipoDocumento tipoDocumentoRegistrado = tipoDocumentoService.registrarAfiliado(tipoDocumento);
        return new ResponseEntity<>(tipoDocumentoRegistrado, HttpStatus.CREATED);
    }
}
