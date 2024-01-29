package com.iteria.Prueba.service.serviceImpl;

import com.iteria.Prueba.entity.*;
import com.iteria.Prueba.exception.TipoDocumentoInactivoException;
import com.iteria.Prueba.exception.TipoDocumentoNuloException;
import com.iteria.Prueba.repository.AfiliadoRepository;
import com.iteria.Prueba.repository.ContratoRepository;
import com.iteria.Prueba.repository.PlanRepository;
import com.iteria.Prueba.repository.TipoDocumentoRepository;
import com.iteria.Prueba.service.serviceDAO.IAfiliadoService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AfiliadoService implements IAfiliadoService {


    @Autowired
    private ContratoRepository contratoRepository;

    @Autowired
    private AfiliadoRepository afiliadoRepository;

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private TipoDocumentoService tipoDocumentoService;
    @Autowired
    private TipoDocumentoRepository tipoDocumentoRepository;

    @Override
    public Afiliado crearAfiliado(Afiliado afiliado) {
        // Guardar el TipoDocumento asociado al Afiliado
        TipoDocumento tipoDocumento = afiliado.getTipoDocumento();

        if (tipoDocumento == null) {
            // Manejar el caso en el que el TipoDocumento sea nulo
            throw new TipoDocumentoNuloException("El TipoDocumento del Afiliado es nulo");
        }

        if (tipoDocumento.getTdcEstado() != TdcEstado.ACTIVO) {
            throw new TipoDocumentoInactivoException("El tipo de documento no está activo");
        }

        // Resto de la lógica para registrar el afiliado y el tipo de documento
        tipoDocumento = tipoDocumentoService.registrarAfiliado(tipoDocumento);
        afiliado.setTipoDocumento(tipoDocumento);

        // Resto de la lógica para guardar el Afiliado
        return afiliadoRepository.save(afiliado);
    }


        public Afiliado actualizarAfiliadoConTipoDocumento(Long afiId, Afiliado afiliado,TipoDocumento tipoDocumento) {
            // Obtener el afiliado actual
            Afiliado afiliadoExistente = afiliadoRepository.findById(afiId)
                    .orElseThrow(() -> new EntityNotFoundException("Afiliado con ID " + afiId + " no encontrado"));

            // Obtener el tipo de documento actual
            TipoDocumento tipoDocumentoExistente = tipoDocumentoRepository.findById(afiliadoExistente.getTipoDocumento().getTdcId())
                    .orElseThrow(() -> new EntityNotFoundException("Tipo de documento no encontrado"));

            // Verificar que el tipo de documento esté activo
            if (tipoDocumentoExistente.getTdcEstado() != TdcEstado.ACTIVO) {
                throw new TipoDocumentoInactivoException("El tipo de documento no está activo");
            }

            // Actualizar el estado del tipo de documento
            tipoDocumentoExistente.setTdcEstado(TdcEstado.ACTIVO);

            // Actualizar el afiliado con el nuevo tipo de documento
            afiliadoExistente.setNombre(afiliado.getNombre());
            afiliadoExistente.setApellidos(afiliado.getApellidos());
            afiliadoExistente.setDocumento(afiliado.getDocumento());
            afiliadoExistente.setEstado(afiliado.getEstado());
            afiliadoExistente.setMail(afiliado.getMail());
            afiliadoExistente.setTelefono(afiliado.getTelefono());
            afiliadoExistente.setDireccion(afiliado.getDireccion());
            afiliadoExistente.setAfiliadoTipoDocumentos(afiliado.getAfiliadoTipoDocumentos());
            // ... actualizar otros campos según sea necesario
            tipoDocumentoExistente.setTdcNombre(tipoDocumento.getTdcNombre());
            tipoDocumentoExistente.setTdcEstado(tipoDocumento.getTdcEstado());


            // Guardar cambios en el tipo de documento
            tipoDocumentoRepository.save(tipoDocumentoExistente);

            // Resto de la lógica para actualizar el Afiliado
            return afiliadoRepository.save(afiliadoExistente);
        }

    @Transactional
    public void cambiarEstadoAfiliado(Long afiliadoId, Estado nuevoEstado) {
        Afiliado afiliado = afiliadoRepository.findById(afiliadoId)
                .orElseThrow(() -> new EntityNotFoundException("Afiliado no encontrado"));

        Estado estadoAnterior = afiliado.getEstado();
        afiliado.setEstado(nuevoEstado);

        afiliadoRepository.save(afiliado);

        if (nuevoEstado == Estado.INACTIVO && estadoAnterior != Estado.INACTIVO) {
            // Cambio de estado a INACTIVO, actualizar fecha de retiro en contratos asociados
            List<Contrato> contratos = contratoRepository.findByAfiliado(afiliado);

            for (Contrato contrato : contratos) {
                contrato.setFechaRetiro(LocalDate.now());
            }

            contratoRepository.saveAll(contratos);
        }
    }

}








//    @Override
//    public Afiliado actualizarAfiliado(Long id, Afiliado afiliadoActualizado) {
//        Afiliado afiliadoExistente = afiliadoRepository.findById(id)
//                .orElseThrow(() -> new NoSuchElementException("Afiliado no encontrado con ID: " + id));
//
//        // Realiza las actualizaciones necesarias en el afiliado existente
//        afiliadoExistente.setAfiNombre(afiliadoActualizado.getAfiNombre());
//        afiliadoExistente.setAfiApellidos(afiliadoActualizado.getAfiApellidos());
//        afiliadoExistente.setTipoDocumento(afiliadoActualizado.getTipoDocumento());
//        afiliadoExistente.setAfiDireccion(afiliadoActualizado.getAfiDireccion());
//        afiliadoExistente.setAfiEstado(afiliadoActualizado.getAfiEstado());
//        afiliadoExistente.setAfiId(afiliadoActualizado.getAfiId());
//        afiliadoExistente.setAfiTelefono(afiliadoActualizado.getAfiTelefono());
//
//
//        // Guarda el afiliado actualizado en la base de datos
//        return afiliadoRepository.save(afiliadoExistente);
//    }
//
//    @Override
//    public void validarTipoDocumento(TdcEstado tdcEstado) {
//        if (tdcEstado != null) {
//            // Verificar si el estado del tipo de documento es ACTIVO
//            if (tdcEstado != TdcEstado.ACTIVO) {
//                throw new IllegalArgumentException("No se puede utilizar un tipo de documento inactivo.");
//            }
//        }
//    }

//    @Override
//    public Afiliado actualizarEstadoAfiliado(Long afiliadoId, Estado nuevoEstado) {
//        Afiliado afiliadoExistente = afiliadoRepository.findById(afiliadoId)
//                .orElseThrow(() -> new NoSuchElementException("Afiliado no encontrado con ID: " + afiliadoId));
//
//        if (nuevoEstado == Estado.INACTIVO) {
//            afiliadoExistente.setAfiEstado(nuevoEstado);
//            Date fechaRetiro = new Date();
//
//            // Actualizar la fecha de retiro en los contratos asociados
//            actualizarFechaRetiroContratos(afiliadoExistente.getContratos(), fechaRetiro);
//        } else {
//            afiliadoExistente.setAfiEstado(nuevoEstado);
//        }
//
//        return afiliadoRepository.save(afiliadoExistente);
//    }
//
//    private void actualizarFechaRetiroContratos(List<Contrato> contratos, Date fechaRetiro) {
//        for (Contrato contrato : contratos) {
//            if (contrato.getEstadoContrato()== EstadoContrato.ACTIVO) {
//                contrato.setCtoFechaRetiro(fechaRetiro);
//                contrato.setEstadoContrato(EstadoContrato.INACTIVO);
//            }
//        }
//    }



