package com.example.demo.repositories;

import java.io.Serializable;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.Circuito;
import com.example.demo.models.Escuderia;
import com.example.demo.models.Piloto;

import jakarta.transaction.Transactional;

public interface PilotoRepositorio extends JpaRepository<Piloto, Serializable> {
    
    @Bean
    public abstract List<Piloto> findAll();
    public abstract Piloto findById(int id);
    public abstract Piloto findByNombre(String nombre);
    public abstract Piloto findByDorsal(int dorsal);
    public abstract List<Piloto> findByEscuderia_Nombre(String escuderia);
    public abstract Piloto findByEscuderia(Escuderia escuderia);
    public abstract Piloto findByCircuitos(Circuito circuitos);

    @Transactional
    public abstract void deleteById(int id);
    @Transactional
    public abstract void deleteByNombre(String nombre);
    @Transactional
    public abstract void deleteByDorsal(int dorsal);

    public abstract void delete(Piloto piloto);

    @SuppressWarnings("unchecked")
    public abstract Piloto save(Piloto piloto);
}
