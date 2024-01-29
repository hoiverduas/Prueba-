package com.iteria.Prueba.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "PLAN")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PLN_ID")
    private Long plnId;

    @JsonIgnore
    @OneToMany(mappedBy = "plan")
    private List<Contrato> contratoList = new ArrayList<>();

    @Column(name = "PLN_NOMBRE", length = 15)
    private String plnNombre;

    @Column(name = "PLN_FECHA_INICIO")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate plnFechaInicio;

    @Column(name = "PLN_FECHA_FIN")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate plnFechaFin;

    @Column(name = "PLN_ESTADO",length = 255)
    @Enumerated(EnumType.STRING)
    private  EstadoPlan estadoPlan;



}