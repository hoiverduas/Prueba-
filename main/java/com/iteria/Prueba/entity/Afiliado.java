package com.iteria.Prueba.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "AFILIADO",uniqueConstraints = @UniqueConstraint(columnNames = {"TDC_ID", "AFI_DOCUMENTO"}))
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Afiliado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AFI_ID")
    private Long afiId;

    @Column(name = "AFI_NOMBRE", length = 20)
    private String nombre;

    @Column(name = "AFI_APELLIDOS", length = 30)
    private String apellidos;

    @Column(name = "AFI_DOCUMENTO", length = 20)
    private String documento;

    @ManyToOne
    @JoinColumn(name = "TDC_ID")
    private TipoDocumento tipoDocumento;

    @JsonIgnore
    @OneToMany(mappedBy = "afiliado")

    private List<Contrato> contratoList = new ArrayList<>();


    @Column(name = "AFI_DIRECCION", length = 30)
    private String direccion;

    @Column(name = "AFI_TELEFONO", length = 20)
    private String telefono;

    @Column(name = "AFI_MAIL", length = 30)
    private String mail;

    @Enumerated(EnumType.STRING)
    private Estado estado;


    @ElementCollection
    @CollectionTable(
            name = "AFILIADO_TIPO_DOCUMENTO",
            joinColumns = @JoinColumn(name = "AFI__ID", referencedColumnName = "AFI_ID")
    )

    private Set<AfiliadoTipoDocumentoId> afiliadoTipoDocumentos = new HashSet<>();

    // Métodos para agregar y quitar AfiliadoTipoDocumento si es necesario

    public void agregarAfiliadoTipoDocumento(Long tdcId) {
        AfiliadoTipoDocumentoId afiliadoTipoDocumentoId = new AfiliadoTipoDocumentoId(this.afiId, tdcId);
        afiliadoTipoDocumentos.add(afiliadoTipoDocumentoId);
    }

    public void quitarAfiliadoTipoDocumento(Long tdcId) {
        AfiliadoTipoDocumentoId afiliadoTipoDocumentoId = new AfiliadoTipoDocumentoId(this.afiId, tdcId);
        afiliadoTipoDocumentos.remove(afiliadoTipoDocumentoId);
    }
}
