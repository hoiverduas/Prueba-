package com.iteria.Prueba.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "CONTRATO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contrato {



    @EmbeddedId
    private ContratoId id;
    @JsonIgnore
    @ManyToOne
    @MapsId("afiId")
    private Afiliado afiliado;
    @JsonIgnore
    @ManyToOne
    @MapsId("planId")
    private Plan plan;


    @Column(name = "CTO_CANTIDAD_USUARIOS")
    private Integer cantidadUsuarios;

    @Column(name = "CTO_FECHA_INICIO")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaInicio;

    @Column(name = "CTO_FECHA_RETIRO")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaRetiro;


    @Column(name = "CTO_EPS", length = 20)
    private String eps;


}