package com.iteria.Prueba.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TIPO_DOCUMENTO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoDocumento {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TDC_ID")
    private Long tdcId;

    @Column(name = "TDC_NOMBRE")
    private String tdcNombre;

    @Enumerated(EnumType.STRING)
    @Column(name = "TDC_ESTADO")
    private TdcEstado tdcEstado;
}