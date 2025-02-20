package com.example.demo.repositories;

import java.io.Serializable;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.Circuito;

import jakarta.transaction.Transactional;

public interface CircuitoRepositorio extends JpaRepository<Circuito, Serializable> {
    
    @Bean
    public abstract List<Circuito> findAll();
    public abstract Circuito findById(int id);
    public abstract Circuito findByNombre(String nombre);
    public abstract Circuito findByPais(String pais);

    @Transactional
    public abstract void deleteById(int id);
    
    @Transactional
    public abstract void deleteByNombre(String nombre);

    @Transactional
    public abstract void deleteByPais(String pais);

    @Transactional
    public abstract void delete(Circuito circuito);

    @SuppressWarnings("unchecked")
    public abstract Circuito save(Circuito circuito);
    
}
