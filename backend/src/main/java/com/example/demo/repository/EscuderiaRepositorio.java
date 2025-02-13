package com.example.demo.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Escuderia;

import jakarta.transaction.Transactional;

public interface EscuderiaRepositorio extends JpaRepository<Escuderia, Serializable> {

    @Bean
    public abstract List<Escuderia> findAll();
    public abstract Escuderia findById(int id);

    @SuppressWarnings("unchecked")
    public abstract Escuderia save(Escuderia escuderia);

    @Transactional
    public abstract void delete(Escuderia escuderia);

    @Transactional
    public abstract void deleteById(int id);
    
}
