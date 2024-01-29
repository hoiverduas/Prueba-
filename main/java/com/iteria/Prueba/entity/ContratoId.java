package com.iteria.Prueba.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Setter
@Getter

public class ContratoId  implements Serializable {

    @Column(name = "afi_id")
    private Long afiId;
    @Column(name = "plan_id")
    private Long planId;
}
