package com.iteria.Prueba.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AfiliadoTipoDocumentoId implements Serializable {

    @Column(name = "AFI_ID")
    private Long afiId;

    @Column(name = "TDC_ID")
    private Long tdcId;

    // Métodos equals y hashCode si es necesario
}