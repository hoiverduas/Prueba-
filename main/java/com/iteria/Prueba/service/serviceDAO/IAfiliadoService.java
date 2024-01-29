package com.iteria.Prueba.service.serviceDAO;

import com.iteria.Prueba.entity.*;

public interface IAfiliadoService {

//   Afiliado actualizarAfiliado(Long id, Afiliado afiliadoActualizado);
//   void validarTipoDocumento(TdcEstado tdcEstado);
       Afiliado crearAfiliado( Afiliado afiliado);
       Afiliado actualizarAfiliadoConTipoDocumento(Long afiId, Afiliado afiliado,TipoDocumento tipoDocumento);


//   Afiliado actualizarEstadoAfiliado(Long afiliadoId, Estado nuevoEstado);
}
