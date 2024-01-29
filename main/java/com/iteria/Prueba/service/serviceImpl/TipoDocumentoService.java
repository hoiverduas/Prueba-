package com.iteria.Prueba.service.serviceImpl;

import com.iteria.Prueba.entity.TipoDocumento;
import com.iteria.Prueba.repository.TipoDocumentoRepository;
import com.iteria.Prueba.service.serviceDAO.ITipoDocumentoService;
import org.springframework.stereotype.Service;

@Service
public class TipoDocumentoService implements ITipoDocumentoService {

    private  final TipoDocumentoRepository tipoDocumentoRepository;

    public TipoDocumentoService(TipoDocumentoRepository tipoDocumentoRepository) {
        this.tipoDocumentoRepository = tipoDocumentoRepository;
    }

    @Override
    public TipoDocumento registrarAfiliado(TipoDocumento tipoDocumento) {
        return tipoDocumentoRepository.save(tipoDocumento);
    }
}
