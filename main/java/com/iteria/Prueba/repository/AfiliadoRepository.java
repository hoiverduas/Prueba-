package com.iteria.Prueba.repository;


import com.iteria.Prueba.entity.Afiliado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AfiliadoRepository extends JpaRepository<Afiliado,Long> {

}

