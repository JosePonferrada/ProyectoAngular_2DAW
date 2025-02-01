package com.example.demo.repositories;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.Escuderia;
import com.example.demo.models.Piloto;

import jakarta.transaction.Transactional;

public interface PilotoRepositorio extends JpaRepository<Piloto, Serializable> {

    @Bean
    public abstract List<Piloto> findAll();
    public abstract Piloto findById(int id);
    public abstract Piloto findByNombre(String nombre);
    public abstract Piloto findByDorsal(int dorsal);
    public abstract Piloto findByEquipo(Escuderia equipo);
    public abstract Piloto findByPuntos(int puntos);

    @Transactional
    public abstract void deleteById(int id);
    @Transactional
    public abstract void delete(Piloto piloto);

    @Transactional
    public abstract Piloto save(Piloto piloto);
    
}
